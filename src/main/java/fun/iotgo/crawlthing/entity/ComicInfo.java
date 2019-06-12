package fun.iotgo.crawlthing.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ComicInfo {
    private Integer id;

    private String comicname;

    private String comicchapter;

    private String comicpage;

    private String comicimg;
}