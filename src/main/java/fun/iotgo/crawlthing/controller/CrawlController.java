
package fun.iotgo.crawlthing.controller;

import fun.iotgo.crawlthing.crawl.ComicCrawl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/crawl")
public class CrawlController {

  @Autowired
  private ComicCrawl comicCrawl;

  @RequestMapping("/singleChapter")
  public String singleChapter(@RequestParam("chapterUrl") String chapterUrl){
    comicCrawl.getComicSingleChapter(chapterUrl);
    return "success";
  }
}