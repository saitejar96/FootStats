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

public class GridActivity2 extends ArrayAdapter {
    private Context context;
    private int layoutResourceId;
    private ArrayList data = new ArrayList();

    public GridActivity2(Context context, int layoutResourceId, ArrayList data) {
        super(context, layoutResourceId, data);
        this.layoutResourceId = layoutResourceId;
        this.context = context;
        this.data = data;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View row = convertView;
        ViewHolder2 holder = null;

        if (row == null) {
            LayoutInflater inflater = ((Activity) context).getLayoutInflater();
            row = inflater.inflate(layoutResourceId, parent, false);
            holder = new ViewHolder2();
            holder.p_form = (TextView) row.findViewById(R.id.t2);
            holder.p_name = (TextView) row.findViewById(R.id.t1);
            holder.p_ppg = (TextView) row.findViewById(R.id.t3);
            holder.p_img = (ImageView) row.findViewById(R.id.i0);
            holder.c_img = (ImageView) row.findViewById(R.id.i1);
            row.setTag(holder);
        } else {
            holder = (ViewHolder2) row.getTag();
        }
        BestPlayer item = (BestPlayer) data.get(position);
        holder.p_name.setText("Name: "+item.getP_name());
        holder.p_form.setText("Form: "+item.getP_form());
        holder.p_ppg.setText("Avg Pts: "+item.getP_ppg());
        Picasso.with(context).load(item.getP_img()).into(holder.p_img);
        Picasso.with(context).load(item.getC_img()).into(holder.c_img);
        return row;
    }

    static class ViewHolder2 {
        TextView p_ppg;
        TextView p_form;
        TextView p_name;
        ImageView p_img;
        ImageView c_img;
    }
}
