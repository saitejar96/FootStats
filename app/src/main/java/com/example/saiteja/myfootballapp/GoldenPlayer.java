package com.example.saiteja.myfootballapp;

import android.text.format.DateUtils;

import java.util.Date;

/**
 * Created by sai teja on 05-01-2017.
 */

public class GoldenPlayer implements Comparable<GoldenPlayer> {
    public String uid;
    public String player;
    public String img;
    public String user;
    public String email;
    public String points;

    public Date getTstamp() {
        return tstamp;
    }

    public void setTstamp(Date tstamp) {
        this.tstamp = tstamp;
    }

    public Date tstamp;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPoints() {
        return points;
    }

    public void setPoints(String points) {
        this.points = points;
    }

    @Override
    public String toString() {
        return this.getUser();
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getPlayer() {
        return player;
    }

    public void setPlayer(String player) {
        this.player = player;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    @Override
    public int compareTo(GoldenPlayer goldenPlayer) {
        int p1 = Integer.parseInt(goldenPlayer.getPoints());
        int p2 = Integer.parseInt(this.getPoints());
        Date d1 = goldenPlayer.getTstamp();
        Date d2 = this.getTstamp();
        long l1 = d1.getTime();
        long l2 = d2.getTime();
        if(p2-p1!=0){
            return p2-p1;
        }
        else if(l1-l2>0){
            return +1;
        }
        else if(l1-l2<0){
            return -1;
        }
        else{
            return 0;
        }
    }
}
