
package fun.iotgo.crawlthing.crawl;

import fun.iotgo.crawlthing.dao.ComicInfoMapper;
import fun.iotgo.crawlthing.entity.ComicInfo;
import fun.iotgo.crawlthing.util.ComicUtil;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ComicCrawl {

  @Autowired
  private ComicInfoMapper comicInfoMapper;

  /**
   * 1.通过漫画URL，获取该漫画每个章节的链接列表 2.通过每个章节的链接，获取该章节总共页数 3.拼接 章节URL+page=N 组成每一话链接 4.下载每一话图片以 漫画名称/章节
   * 结构存储图片 5.压缩下载后的漫画，并改后缀名为cbz
   */
  public void getComic() {
    String comicUrl = "http://www.chuixue.net/manhua/19736/";
    String host = "http://www.chuixue.net";
    List<String> chapterList = ComicUtil.getList(ComicUtil.getClient(), comicUrl, host);

    for (int i = 0; i < chapterList.size(); i++) {
      String chapterUrl = chapterList.get(i);
      String chapter = String.valueOf((i + 1));
      int chapterCount = ComicUtil.getChapterCount(ComicUtil.getClient(), chapterUrl);
      for (int j = 0; j < chapterCount; j++) {
        String page = String.valueOf((j + 1));
        String newChapterUrl = chapterUrl + "?page=" + (j + 1);
        List<String> imgList = ComicUtil.getImg(ComicUtil.getClient(), newChapterUrl);

        ComicInfo comicInfo = new ComicInfo();
        comicInfo.setComicname("原目");
        comicInfo.setComicchapter(chapter);
        comicInfo.setComicpage(page);
        comicInfo.setComicimg(imgList.get(0));
        comicInfoMapper.insertSelective(comicInfo);
      }
    }

//    chapterList.forEach(chapter -> {
//      int chapterCount = ComicUtil.getChapterCount(ComicUtil.getClient(),chapter);
//      for (int i = 0; i < chapterCount; i++) {
//        String newChapterUrl = chapter + "?page=" + (i+1);
//        ComicUtil.getImg(ComicUtil.getClient(),newChapterUrl);
//      }
//    });
//    String chapterUrl = "http://www.chuixue.net/manhua/19736/535303.html?page=14";
//    List<String> imgList = ComicUtil.getImg(ComicUtil.getClient(),chapterUrl);
//    for (int i = 0; i < imgList.size(); i++) {
//      DownloadUtil.downloadFile(imgList.get(i),"d://" + i + ".jpg");
//    }
  }
}