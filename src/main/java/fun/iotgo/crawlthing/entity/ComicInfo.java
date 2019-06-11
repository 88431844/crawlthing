package fun.iotgo.crawlthing.entity;


public class ComicInfo {
    private Integer id;

    private String comicname;

    private String comicchapter;

    private String comicpage;

    private String comicimg;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getComicname() {
        return comicname;
    }

    public void setComicname(String comicname) {
        this.comicname = comicname == null ? null : comicname.trim();
    }

    public String getComicchapter() {
        return comicchapter;
    }

    public void setComicchapter(String comicchapter) {
        this.comicchapter = comicchapter == null ? null : comicchapter.trim();
    }

    public String getComicpage() {
        return comicpage;
    }

    public void setComicpage(String comicpage) {
        this.comicpage = comicpage == null ? null : comicpage.trim();
    }

    public String getComicimg() {
        return comicimg;
    }

    public void setComicimg(String comicimg) {
        this.comicimg = comicimg == null ? null : comicimg.trim();
    }
}