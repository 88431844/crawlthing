
package fun.iotgo.crawlthing.crawl;


import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import java.util.Arrays;
import java.util.List;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

public class GetChapterImg {

  public static List<String> getImg(WebClient webClient,String chapterUrl){
    HtmlPage page = null;
    try {
      page = webClient
          .getPage(chapterUrl);//尝试加载上面图片例子给出的网页
    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      webClient.close();
    }

    webClient.waitForBackgroundJavaScript(5000);//异步JS执行需要耗时,所以这里线程要阻塞5秒,等待异步JS执行结束

    String pageXml = page.asXml();//直接将加载完成的页面转换成xml格式的字符串

    Document document = Jsoup.parse(pageXml);//获取html文档

    Elements viewimg = document.getElementById("viewimg")
        .select("img[src$=.jpg]");                   // 获取所有图片元素集
    Elements nextimg = document.getElementById("nextimg").select("img[src$=.jpg]");

    String viewimgPath = viewimg.attr("src");
    String nextimgPath = nextimg.attr("src");
    System.out.println("viewimg ------------- : " + viewimgPath);
    System.out.println("nextimg ------------- : " + nextimgPath);
    return Arrays.asList(viewimgPath,nextimgPath);
  }
}