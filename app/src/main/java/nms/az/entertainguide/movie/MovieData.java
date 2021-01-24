package nms.az.entertainguide.movie;

/**
 * Created by Azad on 5/5/2015.
 */
public class MovieData {

    private String name, imgURL , desc, link;

    public MovieData(String name, String desc, String link, String imgURL){
        this.name = name;
        this.desc = desc;
        this.link = link;
        this.imgURL = imgURL;
    }

    public String getName() {
        return name;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getImgURL() {
        return imgURL;
    }

    public void setImgURL(String imgURL) {
        this.imgURL = imgURL;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public void setName(String name) {
        this.name = name;
    }
}
