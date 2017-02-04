package com.example.saiteja.myfootballapp;

import android.app.Activity;
import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by sai teja on 21-12-2016.
 */

public class GridActivity extends ArrayAdapter {
    private Context context;
    private int layoutResourceId;
    private ArrayList data = new ArrayList();

    public GridActivity(Context context, int layoutResourceId, ArrayList data) {
        super(context, layoutResourceId, data);
        this.layoutResourceId = layoutResourceId;
        this.context = context;
        this.data = data;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View row = convertView;
        ViewHolder holder = null;

        if (row == null) {
            LayoutInflater inflater = ((Activity) context).getLayoutInflater();
            row = inflater.inflate(layoutResourceId, parent, false);
            holder = new ViewHolder();
            holder.homet = (TextView) row.findViewById(R.id.homet);
            holder.awayt = (TextView) row.findViewById(R.id.awayt);
            holder.homei = (ImageView) row.findViewById(R.id.homei);
            holder.awayi = (ImageView) row.findViewById(R.id.awayi);
            row.setTag(holder);
        } else {
            holder = (ViewHolder) row.getTag();
        }
        Fixture item = (Fixture) data.get(position);
        holder.homet.setText(item.getHomeT());
        holder.awayt.setText(item.getAwayT());
        Picasso.with(context).load(item.getHomeI()).into(holder.homei);
        Picasso.with(context).load(item.getAwayI()).into(holder.awayi);
        return row;
    }

    static class ViewHolder {
        TextView homet;
        TextView awayt;
        ImageView homei;
        ImageView awayi;
    }
}
