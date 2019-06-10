
package fun.iotgo.crawlthing.crawl;

import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.gargoylesoftware.htmlunit.NicelyResynchronizingAjaxController;
import com.gargoylesoftware.htmlunit.WebClient;
import java.util.List;

public class ComicCrawl {

  public static void getComic(){
    String chapterUrl = "http://www.chuixue.net/manhua/19736/535303.html?page=14";
    List<String> imgList = GetChapterImg.getImg(GetWebClient.getClient(),chapterUrl);
    imgList.forEach(System.out::println);
  }

  public static void main(String[] args) {
    ComicCrawl.getComic();
  }
}