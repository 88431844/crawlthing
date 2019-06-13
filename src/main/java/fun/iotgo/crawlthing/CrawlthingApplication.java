package fun.iotgo.crawlthing;

import fun.iotgo.crawlthing.crawl.ComicCrawl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class CrawlthingApplication {

  @Autowired
  private ComicCrawl comicCrawl;

  public static void main(String[] args) {
    SpringApplication.run(CrawlthingApplication.class, args);

  }
  @Bean
  public void crawlComic(){
    String comicUrl = "http://www.chuixue.net/manhua/29807/";
    String host = "http://www.chuixue.net";
    String comicName = "戒魔人";
    comicCrawl.getComic(comicUrl,host,476,comicName);
  }

}
