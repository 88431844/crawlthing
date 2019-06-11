
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
    /**
     * 1.通过漫画URL，获取该漫画每个章节的链接列表
     * 2.通过每个章节的链接，获取该章节总共页数
     * 3.拼接 章节URL+page=N 组成每一话链接
     * 4.下载每一话图片以 漫画名称/章节 结构存储图片
     * 5.压缩下载后的漫画，并改后缀名为cbz
     */
    ComicCrawl.getComic();
  }
}