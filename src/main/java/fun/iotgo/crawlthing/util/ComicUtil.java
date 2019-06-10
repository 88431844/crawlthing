package fun.iotgo.crawlthing.util;

import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.gargoylesoftware.htmlunit.NicelyResynchronizingAjaxController;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlListItem;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

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

  public static List<String> getList(WebClient webClient, String url) {
    List<String> chapterList = new ArrayList<>();
    try {
      HtmlPage page=webClient.getPage(url);

      List<HtmlListItem> list = page.getByXPath("//div[@id='play_0']/ul/li");
      for (int i = 0; i < list.size(); i++) {
        HtmlListItem htmlListItem = list.get(i);
        htmlListItem.getChildElements().forEach(l -> chapterList.add(l.getAttribute("href")));
      }
    }catch (Exception e){
      e.printStackTrace();
    }
    return chapterList;
  }

  public static List<String> getImg(WebClient webClient, String chapterUrl) {
    Document document = Jsoup.parse(getPageXml(webClient, chapterUrl));//获取html文档

    Elements viewimg = document.getElementById("viewimg")
        .select("img[src$=.jpg]");                   // 获取所有图片元素集
    Elements nextimg = document.getElementById("nextimg").select("img[src$=.jpg]");

    String viewimgPath = viewimg.attr("src");
    String nextimgPath = nextimg.attr("src");
    return Arrays.asList(viewimgPath, nextimgPath);
  }

  private static String getPageXml(WebClient webClient, String url) {
    HtmlPage page = null;
    try {
      page = webClient
          .getPage(url);//尝试加载上面图片例子给出的网页
    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      webClient.close();
    }

    webClient.waitForBackgroundJavaScript(5000);//异步JS执行需要耗时,所以这里线程要阻塞5秒,等待异步JS执行结束

    return page.asXml();//直接将加载完成的页面转换成xml格式的字符串
  }

  public static void main(String[] args) {
    ComicUtil.getList(ComicUtil.getClient(),"http://www.chuixue.net/manhua/19736/").forEach(System.out::println);
  }
}