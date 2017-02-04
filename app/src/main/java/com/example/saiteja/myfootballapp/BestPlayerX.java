package com.example.saiteja.myfootballapp;

/**
 * Created by sai teja on 22-12-2016.
 */
public class BestPlayerX implements Comparable<BestPlayerX>{
    public String p_ppg;
    public String p_form;
    public String p_name;
    public String p_teamW;

    public String getP_teamW() {
        return p_teamW;
    }

    public void setP_teamW(String p_teamW) {
        this.p_teamW = p_teamW;
    }

    public String getP_oppW() {
        return p_oppW;
    }

    public void setP_oppW(String p_oppW) {
        this.p_oppW = p_oppW;
    }

    public String p_oppW;


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
    public int compareTo(BestPlayerX bestPlayer) {
        int form1 = Math.round(Float.parseFloat(bestPlayer.getP_form())*10);
        int ppg1 = Math.round(Float.parseFloat(bestPlayer.getP_ppg())*10);
        int tW1 = Integer.parseInt(bestPlayer.getP_teamW());
        int oW1 = Integer.parseInt(bestPlayer.getP_oppW());
        int form2 = Math.round(Float.parseFloat(this.getP_form())*10);
        int ppg2 = Math.round(Float.parseFloat(this.getP_ppg())*10);
        int tW2 = Integer.parseInt(this.getP_teamW());
        int oW2 = Integer.parseInt(this.getP_oppW());
        float x1 = form1*ppg1*tW1/oW1;
        float x2 = form2*ppg2*tW2/oW2;
        return Math.round(x1-x2);
    }
}
