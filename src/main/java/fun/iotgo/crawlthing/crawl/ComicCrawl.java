
package fun.iotgo.crawlthing.crawl;

import fun.iotgo.crawlthing.dao.ComicInfoMapper;
import fun.iotgo.crawlthing.util.*;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class ComicCrawl {

  @Autowired
  private ComicInfoMapper comicInfoMapper;

  /**
   * 1.通过漫画URL，获取该漫画每个章节的链接列表
   * 2.通过每个章节的链接，获取该章节总共页数
   * 3.拼接 章节URL+page=N 组成每一话链接
   * 4.下载每一话图片以 漫画名称/章节 结构存储图片
   * 5.压缩下载后的漫画，并改后缀名为cbz
   */
  public void getComicStartFrom(String comicUrl, String host,int startFrom,String comicName) {

    String winFilePath = "D:\\code\\crawlthing\\src\\main\\resources\\img\\";
    String linuxFilePath = "/Users/luck/code/crawlthing/src/main/resources/img/";
    String filePath = OSUtil.isWin() ? winFilePath + comicName+"\\" : linuxFilePath +comicName +"/";

    String targetPath = OSUtil.isWin() ? winFilePath : linuxFilePath;

    String targetFile = comicName+ "startFrom" +startFrom+".cbz";

    ComicUtil.getComicChapterUrlList(comicUrl, host).forEach(chapterUrl -> {
      int comicPageSum = ComicUtil.getChapterCount(chapterUrl);
      ComicUtil.getComic(chapterUrl,startFrom,comicPageSum).forEach(c -> DownloadUtil.downloadFile(c.getComicimg(),targetPath+c.getComicname()+"/"+c.getComicchapter()+"/",c.getComicpage()+".jpg"));
    });

    //压缩下载好的漫画，格式为cbz
//    CompactAlgorithm.ZipFiles(filePath,targetPath,targetFile);

  }

  public void getComicSingleChapter(String chapterUrl) {

    String winFilePath = "D:\\code\\crawlthing\\src\\main\\resources\\img\\";
    String linuxFilePath = "/Users/luck/code/crawlthing/src/main/resources/img/";

    String targetPath = OSUtil.isWin() ? winFilePath : linuxFilePath;

    int comicPageSum = ComicUtil.getChapterCount(chapterUrl);
    log.info("getComic comicPageSum : " + comicPageSum);
    ComicUtil.getComic(chapterUrl,0,comicPageSum).forEach(c ->
        {
          String comicImgPath = targetPath+c.getComicname()+"/"+c.getComicchapter()+"/";
          DownloadUtil.downloadFile(c.getComicimg(),comicImgPath, c.getComicpage()+".jpg");
          //压缩下载好的漫画，格式为cbz
          CompactAlgorithm.ZipFiles(comicImgPath,targetPath+c.getComicname()+"/",c.getComicchapter()+".cbz");
        }
    );

  }
}