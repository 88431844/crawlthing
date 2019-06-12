package fun.iotgo.crawlthing.util;

import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.gargoylesoftware.htmlunit.NicelyResynchronizingAjaxController;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlListItem;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import fun.iotgo.crawlthing.entity.ComicInfo;
import java.util.ArrayList;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

@Slf4j
public class ComicUtil {

  public static WebClient getClient() {
    //新建一个模拟谷歌Chrome浏览器的浏览器客户端对象
    final WebClient webClient = new WebClient(BrowserVersion.CHROME);

    webClient.getOptions().setThrowExceptionOnScriptError(false);//当JS执行出错的时候是否抛出异常, 这里选择不需要
    webClient.getOptions()
        .setThrowExceptionOnFailingStatusCode(false);//当HTTP的状态非200时是否抛出异常, 这里选择不需要
    webClient.getOptions().setActiveXNative(false);
    webClient.getOptions().setCssEnabled(false);//是否启用CSS, 因为不需要展现页面, 所以不需要启用
    webClient.getOptions().setJavaScriptEnabled(true); //很重要，启用JS
    webClient.setAjaxController(new NicelyResynchronizingAjaxController());//很重要，设置支持AJAX
    return webClient;
  }

  public static List<String> getComicChapterUrlList(String url, String host) {
    WebClient webClient = ComicUtil.getClient();
    List<String> chapterList = new ArrayList<>();
    try {
      HtmlPage page = webClient.getPage(url);

      List<HtmlListItem> list = page.getByXPath("//div[@id='play_0']/ul/li");
      for (int i = 0; i < list.size(); i++) {
        HtmlListItem htmlListItem = list.get(i);
        htmlListItem.getChildElements()
            .forEach(l -> chapterList.add(host + l.getAttribute("href")));
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
    return chapterList;
  }

  private static String getPageXml(String url) {
    log.info("getPageXml url : " + url);
    WebClient webClient = ComicUtil.getClient();
    HtmlPage page = null;
    try {
      page = webClient.getPage(url);
      if (null == page)return "";
      int statusCode = page.getWebResponse().getStatusCode();
      if (200 != statusCode){
        return "";
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
    finally {
      webClient.close();
    }

    webClient.waitForBackgroundJavaScript(5000);//异步JS执行需要耗时,所以这里线程要阻塞5秒,等待异步JS执行结束

    return page.asXml() == null ? "" : page.asXml();//直接将加载完成的页面转换成xml格式的字符串
  }

  public static List<ComicInfo> getComic(String chapterUrl) {
    log.info("getComic chapterUrl : " + chapterUrl);
    List<ComicInfo> comicInfoList = new ArrayList<>();
    int comicPageSum = getChapterCount(chapterUrl);
    log.info("getComic comicPageSum : " + comicPageSum);
    for (int i = 0; i < comicPageSum; i++) {
      Document document = Jsoup.parse(getPageXml(chapterUrl + "?page=" +(i + 1)));
      if (null == document) {
        return new ArrayList<>();
      }
      Elements viewImg = document.getElementById("viewimg").select("img[src$=.jpg]");

//    String comicName = document.getElementById("position").select("a").get(2).childNodes.get(0);
      String comicName = document.getElementById("viewpagename").parent().childNodes().get(0)
          .attr("text").split(" ")[6];
      String comicChapter = document.getElementById("viewpagename").parent().childNodes().get(0)
          .attr("text").split(" ")[7];
      String comicPage = MyStringUtil.getStringNumber(document.getElementById("viewpagename").text());
      String comicImg = viewImg.attr("src");

      comicInfoList.add(
          ComicInfo.builder().comicname(comicName).comicchapter(comicChapter).comicpage(comicPage)
              .comicimg(comicImg).build());

    }
    return comicInfoList;
  }

  private static int getChapterCount(String chapterUrl) {
    WebClient webClient = ComicUtil.getClient();
    int chapterPages = 0;
    try {
      HtmlPage page = webClient.getPage(chapterUrl);
      List<HtmlListItem> list = page.getByXPath("//span[@id='selectpage2']/select/option");
      chapterPages = list.size();
    } catch (Exception e) {
      e.printStackTrace();
    }
    return chapterPages;
  }

  public static void main(String[] args) {
//    ComicUtil.getList(ComicUtil.getClient(),"http://www.chuixue.net/manhua/19736/","http://www.chuixue.net").forEach(System.out::println);
//    System.out.println("count : "+ComicUtil.getChapterCount(ComicUtil.getClient(),"http://www.chuixue.net/manhua/19736/550557.html"));
  }
}