package nms.az.entertainguide.concert;

/**
 * Created by Azad on 5/5/2015.
 */
public class ConcertData {

    private String name, imgURL, desc, link, type, loca, price, time;

    public ConcertData(String link, String name, String desc, String type, String loca, String time, String price, String imgURL) {
        this.name = name;
        this.desc = desc;
        this.link = link;
        this.imgURL = imgURL;
        this.time = time;
        this.price = price;
        this.loca = loca;
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public String getLink() {
        return link;
    }


    public String getLoca() {
        return loca;
    }

    public void setLoca(String loca) {
        this.loca = loca;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
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
