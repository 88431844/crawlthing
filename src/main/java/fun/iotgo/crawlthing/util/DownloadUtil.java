package fun.iotgo.crawlthing.util;

import java.io.BufferedInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class DownloadUtil {

  public static void downloadFile(String fileUrl, String filePath) {
    FileOutputStream fos = null;
    BufferedInputStream bis = null;
    HttpURLConnection httpUrl = null;
    URL url = null;
    int BUFFER_SIZE = 1024;
    byte[] buf = new byte[BUFFER_SIZE];
    int size = 0;
    try {
      url = new URL(fileUrl);
      httpUrl = (HttpURLConnection) url.openConnection();
      httpUrl.connect();
      bis = new BufferedInputStream(httpUrl.getInputStream());
      fos = new FileOutputStream(filePath);
      while ((size = bis.read(buf)) != -1) {
        fos.write(buf, 0, size);
      }
      fos.flush();
    } catch (IOException e) {
    } catch (ClassCastException e) {
    } finally {
      try {
        fos.close();
        bis.close();
        httpUrl.disconnect();
      } catch (IOException e) {
      } catch (NullPointerException e) {
      }
    }
  }

  public static long getUrlFileSize(String fileUrl) {
    try {
      URL url = new URL(fileUrl);
      HttpURLConnection conn = null;
      conn = (HttpURLConnection) url.openConnection();
      conn.setRequestMethod("HEAD");
      conn.setRequestProperty("User-Agent",
          "Mozilla/5.0 (Windows 7; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/47.0.2526.73 Safari/537.36 YNoteCef/5.8.0.1 (Windows)");
      return (long) conn.getContentLength();
    } catch (IOException e) {
      e.printStackTrace();
    }
    return 0;
  }

  public static void main(String[] args) {
        DownloadUtil.downloadFile("http://chuixue1.handsl.com/2019/05/26/00/48203993b1.jpg","/Users/luck/code/crawlthing/src/main/resources/img/1.jpg");
//    System.out.println("file size : " + DownloadUtil.getUrlFileSize("http://chuixue1.handsl.com/2019/05/26/00/48203993b1.jpg"));
  }

//    public String urlToString(String imgUrl){
//        byte[] result=null;
//        InputStream inStream=null;
//        String photo = "";
//        try {
//            //创建URL
//            URL url=new URL(imgUrl);
//            //创建连接
//            HttpURLConnection conn=(HttpURLConnection) url.openConnection();
//            conn.setRequestMethod("GET");
//            conn.setConnectTimeout(5*1000);
//            conn.setRequestProperty("Accept-Encoding", "identity");
//            conn.connect();
//
//            inStream=conn.getInputStream();
//            int count=conn.getContentLength();//获取远程资源长度
//            result=new byte[count];
//            int readCount=0;
//            while(readCount<count){//循环读取数据
//                readCount+=inStream.read(result,readCount,count-readCount);
//            }
//            photo = toBase64(result);
//        } catch (Exception e) {
//            e.printStackTrace();
//        } finally {
//            try {
//                if (inStream != null) {
//                    inStream.close();
//                }
//                if (conn!= null) {
//                    conn.disconnect();
//                }
//            } catch (IOException e) {
//            }
//        }
//        return photo;
//
//    }


}
