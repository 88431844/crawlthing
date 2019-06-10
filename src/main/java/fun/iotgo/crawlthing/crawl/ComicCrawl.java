
package fun.iotgo.crawlthing.crawl;

import fun.iotgo.crawlthing.util.ComicUtil;
import fun.iotgo.crawlthing.util.DownloadUtil;

import java.util.List;

public class ComicCrawl {

  public static void getComic(){
    String chapterUrl = "http://www.chuixue.net/manhua/19736/535303.html?page=14";
    List<String> imgList = ComicUtil.getImg(ComicUtil.getClient(),chapterUrl);
    for (int i = 0; i < imgList.size(); i++) {
      DownloadUtil.downloadFile(imgList.get(i),"d://" + i + ".jpg");
    }
  }

  public static void main(String[] args) {
    ComicCrawl.getComic();
  }
}