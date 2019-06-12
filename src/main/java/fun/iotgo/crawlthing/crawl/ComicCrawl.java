
package fun.iotgo.crawlthing.crawl;

import com.alibaba.fastjson.JSON;
import com.gargoylesoftware.htmlunit.WebClient;
import fun.iotgo.crawlthing.dao.ComicInfoMapper;
import fun.iotgo.crawlthing.entity.ComicInfo;
import fun.iotgo.crawlthing.util.ComicUtil;

import fun.iotgo.crawlthing.util.CompactAlgorithm;
import fun.iotgo.crawlthing.util.DownloadUtil;
import fun.iotgo.crawlthing.util.MyStringUtil;
import fun.iotgo.crawlthing.util.ZipUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
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
  public void getComic(String comicUrl, String host) {
    String filePath = "/Users/luck/code/crawlthing/src/main/resources/img/原目/";
    String targetPath = "/Users/luck/code/crawlthing/src/main/resources/img/";
    String targetFile = "原目.cbz";


//    List<String> chapterList = ComicUtil.getComicChapterUrlList(webClient, comicUrl, host);
//    chapterList.forEach(chapterUrl -> comicInfoMapper.insertSelective(ComicUtil.getComic(webClient,chapterUrl)));
//    chapterList.forEach(chapterUrl -> System.out.println(JSON.toJSON(ComicUtil.getComic(webClient,chapterUrl))));
//    System.out.println(JSON.toJSON(ComicUtil.getComic(webClient,"http://www.chuixue.net/manhua/19736/549002.html")));


    ComicUtil.getComic("http://www.chuixue.net/manhua/19736/535303.html").forEach(c ->
        DownloadUtil.downloadFile(c.getComicimg(),
            "/Users/luck/code/crawlthing/src/main/resources/img/"+c.getComicname()+"/"+c.getComicchapter()+"/",
            c.getComicpage()+".jpg"));
    //压缩下载好的漫画，格式为cbz
    CompactAlgorithm.ZipFiles(filePath,targetPath,targetFile);


//TODO
//    ComicUtil.getComicChapterUrlList(comicUrl, host).forEach(chapterUrl -> {
//      ComicUtil.getComic(chapterUrl).forEach(c ->{
//        if (Integer.parseInt(MyStringUtil.getStringNumber(c.getComicchapter())) > startFrom){
//          DownloadUtil.downloadFile(c.getComicimg(),
//              "/Users/luck/code/crawlthing/src/main/resources/img/"+c.getComicname()+"/"+c.getComicchapter()+"/",
//              c.getComicpage()+".jpg");
//        }
//      });
//    });

  }
}