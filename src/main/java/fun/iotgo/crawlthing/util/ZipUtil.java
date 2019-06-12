
package fun.iotgo.crawlthing.util;


import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class ZipUtil {

  /** 一次性压缩多个文件，文件存放至一个文件夹中*/
  public static void ZipMultiFile(String filepath ,String zippath) {
    try {
      File file = new File(filepath);// 要被压缩的文件夹
      File zipFile = new File(zippath);
      InputStream input = null;
      ZipOutputStream zipOut = new ZipOutputStream(new FileOutputStream(zipFile));
      if(file.isDirectory()){
        File[] files = file.listFiles();
        for(int i = 0; i < files.length; ++i){
          input = new FileInputStream(files[i]);
          zipOut.putNextEntry(new ZipEntry(file.getName() + File.separator + files[i].getName()));
          int temp = 0;
          while((temp = input.read()) != -1){
            zipOut.write(temp);
          }
          input.close();
        }
      }
      zipOut.close();
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  public static void main(String[] args) {
    ZipMultiFile("/Users/luck/code/crawlthing/src/main/resources/img/原目/295新武器","/Users/luck/code/crawlthing/src/main/resources/img/原目/295新武器.cbz");
  }
}