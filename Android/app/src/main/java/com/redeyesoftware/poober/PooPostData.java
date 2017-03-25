package com.redeyesoftware.poober;

/**
 * Created by georgeeisa on 2017-03-25.
 */

public class PooPostData {
    private String time;
    private double lat;
    private double lon;
    private String desc;
    private String money;
    private String pic;


    public PooPostData(String time, double lat, double lon, String desc, String money, String pic) {

        this.time = time;
        this.lat = lat;
        this.lon = lon;
        this.desc = desc;
        this.money = money;
        this.pic = pic;
    }

    //getters and setters
    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public double getLon() {
        return lon;
    }

    public void setLon(double lon) {
        this.lon = lon;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getMoney() {
        return money;
    }

    public void setMoney(String money) {
        this.money = money;
    }

    public String getPic() {
        return pic;
    }

    public void setPic(String pic) {
        this.pic = pic;
    }


}
