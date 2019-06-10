
package fun.iotgo.crawlthing.crawl;


import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.gargoylesoftware.htmlunit.NicelyResynchronizingAjaxController;
import com.gargoylesoftware.htmlunit.WebClient;

public class GetWebClient {

  public static WebClient getClient(){
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
}