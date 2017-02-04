package com.example.saiteja.myfootballapp;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by sai teja on 06-01-2017.
 */
public class GridActivity4 extends ArrayAdapter {
    private Context context;
    private int layoutResourceId;
    private ArrayList data = new ArrayList();

    public GridActivity4(Context context, int layoutResourceId, ArrayList data) {
        super(context, layoutResourceId, data);
        this.layoutResourceId = layoutResourceId;
        this.context = context;
        this.data = data;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View row = convertView;
        ViewHolderX holder = null;

        if (row == null) {
            LayoutInflater inflater = ((Activity) context).getLayoutInflater();
            row = inflater.inflate(layoutResourceId, parent, false);
            holder = new ViewHolderX();
            holder.t1 = (TextView) row.findViewById(R.id.t1);
            holder.t2 = (TextView) row.findViewById(R.id.t2);
            holder.t3 = (TextView) row.findViewById(R.id.t3);
            holder.t4 = (TextView) row.findViewById(R.id.t4);
            row.setTag(holder);
        } else {
            holder = (ViewHolderX) row.getTag();
        }
        GoldenPlayer item = (GoldenPlayer) data.get(position);
        int posX = position;
        if(posX==0){
            holder.t1.setText("Rank");
            holder.t4.setText("Points");
        }
        else {
            holder.t1.setText("" + posX);
            holder.t4.setText(item.getPoints());
        }
        holder.t2.setText(item.getEmail());
        holder.t3.setText(item.getPlayer());
        return row;
    }

    static class ViewHolderX {
        TextView t1;
        TextView t2;
        TextView t3;
        TextView t4;
    }
}

