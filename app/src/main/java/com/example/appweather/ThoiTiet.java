package com.example.appweather;

public class ThoiTiet {
    private String day;
    private String status;
    private String img;
    private String nhietdomax;
    private String nhietdomin;


    public ThoiTiet(String day, String status, String img, String nhietdomax, String nhietdomin) {
        this.day = day;
        this.status = status;
        this.img = img;
        this.nhietdomax = nhietdomax;
        this.nhietdomin = nhietdomin;
    }

    public ThoiTiet() {
    }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getNhietdomax() {
        return nhietdomax;
    }

    public void setNhietdomax(String nhietdomax) {
        this.nhietdomax = nhietdomax;
    }

    public String getNhietdomin() {
        return nhietdomin;
    }

    public void setNhietdomin(String nhietdomin) {
        this.nhietdomin = nhietdomin;
    }
}
