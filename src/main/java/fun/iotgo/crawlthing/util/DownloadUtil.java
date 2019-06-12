
package fun.iotgo.crawlthing.util;

import java.io.File;
import java.net.URL;
import org.apache.commons.io.FileUtils;

public class DownloadUtil {

  /**
   * httpurl: 图片的url
   * dirfile: 图片的储存目录
   */
  public static void downloadFile(String url, String dir, String filename) {
    try {

      URL httpURL = new URL(url);
      File dirFile = new File(dir);

      // 如果图片储存的目录不存在，则新建该目录
      if (!dirFile.exists()) {
        dirFile.mkdirs();
      }

      // 利用FileUtils.copyURLToFile()实现文件下载
      FileUtils.copyURLToFile(httpURL, new File(dir+filename));
    }
    catch(Exception e) {
      e.printStackTrace();
    }
  }

  public static void main(String[] args) {
    DownloadUtil.downloadFile("http://chuixue1.handsl.com/2019/05/26/00/48203993b1.jpg","/Users/luck/code/crawlthing/src/main/resources/img/","3.jpg");
  }
}