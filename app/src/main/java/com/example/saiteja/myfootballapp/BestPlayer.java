package com.example.saiteja.myfootballapp;

/**
 * Created by sai teja on 22-12-2016.
 */
public class BestPlayer implements Comparable<BestPlayer>{
    public String p_ppg;
    public String p_form;
    public String p_name;

    public String getP_ppg() {
        return p_ppg;
    }

    public void setP_ppg(String p_ppg) {
        this.p_ppg = p_ppg;
    }

    public String getP_form() {
        return p_form;
    }

    public void setP_form(String p_form) {
        this.p_form = p_form;
    }

    public String getP_name() {
        return p_name;
    }

    public void setP_name(String p_name) {
        this.p_name = p_name;
    }

    public String getP_img() {
        return p_img;
    }

    public void setP_img(String p_img) {
        this.p_img = p_img;
    }

    public String getC_img() {
        return c_img;
    }

    public void setC_img(String c_img) {
        this.c_img = c_img;
    }

    public String p_img;
    public String c_img;

    @Override
    public int compareTo(BestPlayer bestPlayer) {
        return Math.round(Float.parseFloat(bestPlayer.getP_form())*10)*Math.round(Float.parseFloat(bestPlayer.getP_ppg())*10)-Math.round(Float.parseFloat(this.getP_form())*10)*Math.round(Float.parseFloat(this.getP_ppg())*10);
    }
}
