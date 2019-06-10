
package fun.iotgo.crawlthing;
import java.io.BufferedInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

public class Test {

  public void saveToFile(String destUrl) {
    FileOutputStream fos = null;
    BufferedInputStream bis = null;
    HttpURLConnection httpUrl = null;
    URL url = null;
    int BUFFER_SIZE = 1024;
    byte[] buf = new byte[BUFFER_SIZE];
    int size = 0;
    try {
      url = new URL(destUrl);
      httpUrl = (HttpURLConnection) url.openConnection();
      httpUrl.connect();
      bis = new BufferedInputStream(httpUrl.getInputStream());
      fos = new FileOutputStream("/Users/luck/code/crawlthing/src/main/resources/img/viewimg.jpg");
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
  public static void main(String[] args) {
    // TODO Auto-generated method stub

    Test dw=new Test();
    dw.saveToFile("https://www.baidu.com/img/bd_logo1.png");
  }
}