
package fun.iotgo.crawlthing.util;


import java.io.*;
import java.math.BigDecimal;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLDecoder;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class MultiThreadDownload {

  private String userAgent = "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_13_4) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/66.0.3359.139 Safari/537.36";
  private String cookie = null;
  private String url = "http://chuixue1.handsl.com/2019/05/26/00/48203993b1.jpg";
  // 文件总长度
  private long contentLength;
  // 当前下载长度
  private long currentLength;
  private long preLength;
  private Map<String, List<String>> headers;
  private String localPath = "/Users/luck/code/crawlthing/src/main/resources/img";
  private int threadSize = 10;
  private int completeThread;

  public long getContentLength() {
    return contentLength;
  }

  public void setContentLength(long contentLength) {
    this.contentLength = contentLength;
  }

  public long getCurrentLength() {
    return currentLength;
  }

  public void setCurrentLength(long currentLength) {
    this.currentLength = currentLength;
  }

  public long getPreLength() {
    return preLength;
  }

  public void setPreLength(long preLength) {
    this.preLength = preLength;
  }

  public int getCompleteThread() {
    return completeThread;
  }

  public void setCompleteThread(int completeThread) {
    this.completeThread = completeThread;
  }

  public synchronized void countDownloadLength(int len) {
    setCurrentLength(getCurrentLength() + len);
  }

  private void download() {
    try {
      HttpURLConnection connection = (HttpURLConnection) new URL(url).openConnection();
      connection.setRequestProperty("User-Agent", userAgent);
      if (cookie != null) connection.setRequestProperty("Cookie", cookie);
      if (connection.getResponseCode() == 302) {
        url = connection.getHeaderField("Location");
        connection = (HttpURLConnection) new URL(url).openConnection();
        connection.setRequestProperty("User-Agent", userAgent);
        if (cookie != null) connection.setRequestProperty("Cookie", cookie);
      }
      if (connection.getResponseCode() == 200) {
        setContentLength(connection.getContentLength());
        headers = connection.getHeaderFields();
        File file = new File(localPath);
        if (!file.exists()) file.mkdirs();
        long singleThreadDownloadLength = getContentLength() / threadSize;
        // 统计下载数据
        new Thread(this::countDownload).start();
        for (int i = 0; i < threadSize; i++) {
          long startLength = i * singleThreadDownloadLength;
          long endLength = (i + 1) * singleThreadDownloadLength - 1;
          if (i == threadSize - 1) {
            endLength = getContentLength() - 1;
          }
          new ThreadDownload(this, localPath + File.separator + getFileName(url), startLength, endLength, (i + 1)).start();
        }
      } else {
        System.out.println("responseCode: " + connection.getResponseCode() + " responseMessage: " + connection.getResponseMessage());
      }
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  // 下载线程
  class ThreadDownload extends Thread {

    private MultiThreadDownload main;
    private String filePath;
    private long startLength, endLength;
    private int threadId;

    public ThreadDownload(MultiThreadDownload main, String filePath, long startLength, long endLength, int threadId) {
      this.main = main;
      this.filePath = filePath;
      this.startLength = startLength;
      this.endLength = endLength;
      this.threadId = threadId;
    }

    @Override
    public void run() {
      try {
        HttpURLConnection connection = (HttpURLConnection) new URL(main.url).openConnection();
        connection.setRequestProperty("User-Agent", userAgent);
        if (cookie != null) connection.setRequestProperty("Cookie", cookie);
        connection.setRequestProperty("Range", "bytes=" + startLength + "-" + endLength);
        if (connection.getResponseCode() == 206) {
          File file = new File(filePath);
          // 创建随机存储文件
          RandomAccessFile raf = new RandomAccessFile(file, "rw");
          // 设置从startLength位置开始写入
          raf.seek(startLength);
          BufferedInputStream bis = new BufferedInputStream(connection.getInputStream());
          int len;
          byte[] b = new byte[1024];
          while ((len = bis.read(b)) != -1) {
            main.countDownloadLength(len);
            raf.write(b);
          }
          raf.close();
          bis.close();
          connection.disconnect();
          main.setCompleteThread(main.getCompleteThread() + 1);
          //                    System.out.println("线程" + threadId + "下载完成!");
        }
      } catch (IOException e) {
        e.printStackTrace();
      }
    }
  }

  private void countDownload() {
    while (getCurrentLength() != getContentLength()) {
      try {
        Thread.sleep(1000);
        BigDecimal bigDecimal = new BigDecimal((double) (getCurrentLength() * 100 / getContentLength()));
        bigDecimal = bigDecimal.setScale(2, BigDecimal.ROUND_HALF_UP);
        System.out.println("下载完成：" + bigDecimal.doubleValue() +
            "% 当前下载速度：" + formatLength(getCurrentLength() - getPreLength()) +
            "/s 当前下载: " + formatLength(getCurrentLength()) +
            " 文件大小: " + formatLength(getContentLength()));
        setPreLength(getCurrentLength());
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
    }
  }

  private String formatLength(long length) {
    if (length < 1024) {
      return length + "b";
    } else if (length > 1024 && length < 1024 * 1024) {
      BigDecimal bigDecimal = new BigDecimal((double) length / 1024);
      bigDecimal = bigDecimal.setScale(2, BigDecimal.ROUND_HALF_UP);
      return bigDecimal.floatValue() + "kb";
    } else if (length > 1024 * 1024 && length < 1024 * 1024 * 1024) {
      BigDecimal bigDecimal = new BigDecimal((double) length / 1024 / 1024);
      bigDecimal = bigDecimal.setScale(2, BigDecimal.ROUND_HALF_UP);
      return bigDecimal.floatValue() + "mb";
    } else {
      BigDecimal bigDecimal = new BigDecimal((double) length / 1024 / 1024 / 1024);
      bigDecimal = bigDecimal.setScale(2, BigDecimal.ROUND_HALF_UP);
      return bigDecimal.floatValue() + "gb";
    }
  }

  private String getFileName(String url) throws UnsupportedEncodingException {
    String fileName = url.substring(url.lastIndexOf("/") + 1);
    if (fileName.contains(".")) {
      String suffix = fileName.substring(fileName.lastIndexOf(".") + 1);
      if (suffix.length() > 4 || suffix.contains("?")) {
        fileName = headers.get("Content-Disposition").get(0);
        if (fileName == null || !fileName.contains("filename")) {
          fileName = UUID.randomUUID().toString();
        } else {
          fileName = fileName.substring(fileName.lastIndexOf("filename") + 9);
        }
      }
    } else {
      fileName = headers.get("Content-Disposition").get(0);
      if (fileName == null || !fileName.contains("filename")) {
        fileName = UUID.randomUUID().toString();
      } else {
        fileName = fileName.substring(fileName.lastIndexOf("filename") + 9);
      }
    }
    fileName = URLDecoder.decode(fileName, "UTF-8");
    return fileName;
  }

  public static void main(String[] args) {
    MultiThreadDownload download = new MultiThreadDownload();
    new Thread(download::download).start();
  }
}