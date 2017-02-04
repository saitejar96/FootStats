package com.example.saiteja.myfootballapp;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import java.lang.Math;

import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by sai teja on 06-01-2017.
 */
public class GridActivity5 extends ArrayAdapter {
    private Context context;
    private int layoutResourceId;
    private ArrayList data = new ArrayList();
    private int nogw;

    public GridActivity5(Context context, int layoutResourceId, ArrayList data,int nogw) {
        super(context, layoutResourceId, data);
        this.layoutResourceId = layoutResourceId;
        this.context = context;
        this.data = data;
        this.nogw = nogw;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View row = convertView;
        ViewHolderY holder = null;

        if (row == null) {
            LayoutInflater inflater = ((Activity) context).getLayoutInflater();
            row = inflater.inflate(layoutResourceId, parent, false);
            holder = new ViewHolderY();
            holder.i = (ImageView)  row.findViewById(R.id.cPlayer);
            holder.t1 = (TextView) row.findViewById(R.id.cForm);
            holder.t2 = (TextView) row.findViewById(R.id.cPrice);
            holder.t3 = (TextView) row.findViewById(R.id.cPPG);
            holder.t4 = (TextView) row.findViewById(R.id.cInjury);
            holder.t5 = (TextView) row.findViewById(R.id.cCreativity);
            holder.t6 = (TextView) row.findViewById(R.id.cInfluence);
            holder.t7 = (TextView) row.findViewById(R.id.cThreat);
            holder.t8 = (TextView) row.findViewById(R.id.cStart);
            row.setTag(holder);
        } else {
            holder = (ViewHolderY) row.getTag();
        }
        JSONObject item = (JSONObject) data.get(position);
        int posX = position;
        if(posX==1){
            holder.t1.setText("Form");
            holder.t2.setText("Price");
            holder.t3.setText("Points per Game");
            holder.t4.setText("Injury Status");
            holder.t5.setText("Creativity");
            holder.t6.setText("Influence");
            holder.t7.setText("Threat");
            holder.t8.setText("Playing Chance");
        }
        else if(posX==0){
            try {
                Picasso.with(getContext()).load("https://platform-static-files.s3.amazonaws.com/premierleague/photos/players/110x140/p"+item.getString("photo").substring(0,item.getString("photo").length()-4)+".png").into(holder.i);
                holder.t1.setText(item.getString("form"));
                if(Float.parseFloat(item.getString("form"))>Float.parseFloat(((JSONObject)data.get(2)).getString("form")))
                    holder.t1.setBackgroundColor(Color.parseColor("#69F0AE"));
                else if(Float.parseFloat(item.getString("form"))<Float.parseFloat(((JSONObject)data.get(2)).getString("form")))
                    holder.t1.setBackgroundColor(Color.parseColor("#F44336"));
                else
                    holder.t1.setBackgroundColor(Color.parseColor("#FFEE58"));
                holder.t2.setText(Float.parseFloat(item.getString("now_cost"))/10+"");
                if(Float.parseFloat(item.getString("now_cost"))<Float.parseFloat(((JSONObject)data.get(2)).getString("now_cost")))
                    holder.t2.setBackgroundColor(Color.parseColor("#69F0AE"));
                else if(Float.parseFloat(item.getString("now_cost"))>Float.parseFloat(((JSONObject)data.get(2)).getString("now_cost")))
                    holder.t2.setBackgroundColor(Color.parseColor("#F44336"));
                else
                    holder.t2.setBackgroundColor(Color.parseColor("#FFEE58"));
                holder.t3.setText(item.getString("points_per_game"));
                if(Float.parseFloat(item.getString("points_per_game"))>Float.parseFloat(((JSONObject)data.get(2)).getString("points_per_game")))
                    holder.t3.setBackgroundColor(Color.parseColor("#69F0AE"));
                else if(Float.parseFloat(item.getString("points_per_game"))<Float.parseFloat(((JSONObject)data.get(2)).getString("points_per_game")))
                    holder.t3.setBackgroundColor(Color.parseColor("#F44336"));
                else
                    holder.t3.setBackgroundColor(Color.parseColor("#FFEE58"));
                if(item.getString("news").length()>13)
                    holder.t4.setText(item.getString("news").substring(0,13));
                else
                    holder.t4.setText(item.getString("news"));
                if(item.getString("news").length()==0) {
                    holder.t4.setText("No News !");
                    holder.t4.setBackgroundColor(Color.parseColor("#69F0AE"));
                }
                else if(item.getString("news").length()>0)
                    holder.t4.setBackgroundColor(Color.parseColor("#F44336"));
                else {
                    holder.t4.setBackgroundColor(Color.parseColor("#FFEE58"));
                }
                holder.t5.setText(item.getString("creativity"));
                if(Float.parseFloat(item.getString("creativity"))>Float.parseFloat(((JSONObject)data.get(2)).getString("creativity")))
                    holder.t5.setBackgroundColor(Color.parseColor("#69F0AE"));
                else if(Float.parseFloat(item.getString("creativity"))<Float.parseFloat(((JSONObject)data.get(2)).getString("creativity")))
                    holder.t5.setBackgroundColor(Color.parseColor("#F44336"));
                else
                    holder.t5.setBackgroundColor(Color.parseColor("#FFEE58"));
                holder.t6.setText(item.getString("influence"));
                if(Float.parseFloat(item.getString("influence"))>Float.parseFloat(((JSONObject)data.get(2)).getString("influence")))
                    holder.t6.setBackgroundColor(Color.parseColor("#69F0AE"));
                else if(Float.parseFloat(item.getString("influence"))<Float.parseFloat(((JSONObject)data.get(2)).getString("influence")))
                    holder.t6.setBackgroundColor(Color.parseColor("#F44336"));
                else
                    holder.t6.setBackgroundColor(Color.parseColor("#FFEE58"));
                holder.t7.setText(item.getString("threat"));
                if(Float.parseFloat(item.getString("threat"))>Float.parseFloat(((JSONObject)data.get(2)).getString("threat")))
                    holder.t7.setBackgroundColor(Color.parseColor("#69F0AE"));
                else if(Float.parseFloat(item.getString("threat"))<Float.parseFloat(((JSONObject)data.get(2)).getString("threat")))
                    holder.t7.setBackgroundColor(Color.parseColor("#F44336"));
                else
                    holder.t7.setBackgroundColor(Color.parseColor("#FFEE58"));

                holder.t8.setText(""+Math.round(item.getInt("total_points")/Float.parseFloat(item.getString("points_per_game"))/nogw));

                System.out.println(Math.round(item.getInt("total_points")/Float.parseFloat(item.getString("points_per_game")))+"");
                System.out.println(Math.round(((JSONObject)data.get(2)).getInt("total_points")/Float.parseFloat(((JSONObject)data.get(2)).getString("points_per_game")))+"");
                if(Math.round(item.getInt("total_points")/Float.parseFloat(item.getString("points_per_game")))>Math.round(((JSONObject)data.get(2)).getInt("total_points")/Float.parseFloat(((JSONObject)data.get(2)).getString("points_per_game"))))
                    holder.t8.setBackgroundColor(Color.parseColor("#69F0AE"));
                else if(Math.round(item.getInt("total_points")/Float.parseFloat(item.getString("points_per_game")))<Math.round(((JSONObject)data.get(2)).getInt("total_points")/Float.parseFloat(((JSONObject)data.get(2)).getString("points_per_game"))))
                    holder.t8.setBackgroundColor(Color.parseColor("#F44336"));
                else
                    holder.t8.setBackgroundColor(Color.parseColor("#FFEE58"));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        else{
            try {
                Picasso.with(getContext()).load("https://platform-static-files.s3.amazonaws.com/premierleague/photos/players/110x140/p"+item.getString("photo").substring(0,item.getString("photo").length()-4)+".png").into(holder.i);
                holder.t1.setText(item.getString("form"));
                if(Float.parseFloat(item.getString("form"))>Float.parseFloat(((JSONObject)data.get(0)).getString("form")))
                    holder.t1.setBackgroundColor(Color.parseColor("#69F0AE"));
                else if(Float.parseFloat(item.getString("form"))<Float.parseFloat(((JSONObject)data.get(0)).getString("form")))
                    holder.t1.setBackgroundColor(Color.parseColor("#F44336"));
                else
                    holder.t1.setBackgroundColor(Color.parseColor("#FFEE58"));
                holder.t2.setText(Float.parseFloat(item.getString("now_cost"))/10+"");
                if(Float.parseFloat(item.getString("now_cost"))<Float.parseFloat(((JSONObject)data.get(0)).getString("now_cost")))
                    holder.t2.setBackgroundColor(Color.parseColor("#69F0AE"));
                else if(Float.parseFloat(item.getString("now_cost"))>Float.parseFloat(((JSONObject)data.get(0)).getString("now_cost")))
                    holder.t2.setBackgroundColor(Color.parseColor("#F44336"));
                else
                    holder.t2.setBackgroundColor(Color.parseColor("#FFEE58"));
                holder.t3.setText(item.getString("points_per_game"));
                if(Float.parseFloat(item.getString("points_per_game"))>Float.parseFloat(((JSONObject)data.get(0)).getString("points_per_game")))
                    holder.t3.setBackgroundColor(Color.parseColor("#69F0AE"));
                else if(Float.parseFloat(item.getString("points_per_game"))<Float.parseFloat(((JSONObject)data.get(0)).getString("points_per_game")))
                    holder.t3.setBackgroundColor(Color.parseColor("#F44336"));
                else
                    holder.t3.setBackgroundColor(Color.parseColor("#FFEE58"));
                if(item.getString("news").length()>13)
                    holder.t4.setText(item.getString("news").substring(0,13));
                else
                    holder.t4.setText(item.getString("news"));
                if(item.getString("news").length()==0) {
                    holder.t4.setText("No News !");
                    holder.t4.setBackgroundColor(Color.parseColor("#69F0AE"));
                }
                else if(item.getString("news").length()>0)
                    holder.t4.setBackgroundColor(Color.parseColor("#F44336"));
                else {

                    holder.t4.setBackgroundColor(Color.parseColor("#FFEE58"));
                }
                holder.t5.setText(item.getString("creativity"));
                if(Float.parseFloat(item.getString("creativity"))>Float.parseFloat(((JSONObject)data.get(0)).getString("creativity")))
                    holder.t5.setBackgroundColor(Color.parseColor("#69F0AE"));
                else if(Float.parseFloat(item.getString("creativity"))<Float.parseFloat(((JSONObject)data.get(0)).getString("creativity")))
                    holder.t5.setBackgroundColor(Color.parseColor("#F44336"));
                else
                    holder.t5.setBackgroundColor(Color.parseColor("#FFEE58"));
                holder.t6.setText(item.getString("influence"));
                if(Float.parseFloat(item.getString("influence"))>Float.parseFloat(((JSONObject)data.get(0)).getString("influence")))
                    holder.t6.setBackgroundColor(Color.parseColor("#69F0AE"));
                else if(Float.parseFloat(item.getString("influence"))<Float.parseFloat(((JSONObject)data.get(0)).getString("influence")))
                    holder.t6.setBackgroundColor(Color.parseColor("#F44336"));
                else
                    holder.t6.setBackgroundColor(Color.parseColor("#FFEE58"));
                holder.t7.setText(item.getString("threat"));
                if(Float.parseFloat(item.getString("threat"))>Float.parseFloat(((JSONObject)data.get(0)).getString("threat")))
                    holder.t7.setBackgroundColor(Color.parseColor("#69F0AE"));
                else if(Float.parseFloat(item.getString("threat"))<Float.parseFloat(((JSONObject)data.get(0)).getString("threat")))
                    holder.t7.setBackgroundColor(Color.parseColor("#F44336"));
                else
                    holder.t7.setBackgroundColor(Color.parseColor("#FFEE58"));

                holder.t8.setText(""+Math.round(item.getInt("total_points")/Float.parseFloat(item.getString("points_per_game"))/nogw));
                System.out.println(Math.round(item.getInt("total_points")/Float.parseFloat(item.getString("points_per_game")))+"");
                System.out.println(Math.round(((JSONObject)data.get(0)).getInt("total_points")/Float.parseFloat(((JSONObject)data.get(0)).getString("points_per_game")))+"");
                if(Math.round(item.getInt("total_points")/Float.parseFloat(item.getString("points_per_game")))>Math.round(((JSONObject)data.get(0)).getInt("total_points")/Float.parseFloat(((JSONObject)data.get(0)).getString("points_per_game"))))
                    holder.t8.setBackgroundColor(Color.parseColor("#69F0AE"));
                else if(Math.round(item.getInt("total_points")/Float.parseFloat(item.getString("points_per_game")))<Math.round(((JSONObject)data.get(0)).getInt("total_points")/Float.parseFloat(((JSONObject)data.get(0)).getString("points_per_game"))))
                    holder.t8.setBackgroundColor(Color.parseColor("#F44336"));
                else
                    holder.t8.setBackgroundColor(Color.parseColor("#FFEE58"));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return row;
    }

    static class ViewHolderY {
        ImageView i;
        TextView t1;
        TextView t2;
        TextView t3;
        TextView t4;
        TextView t5;
        TextView t6;
        TextView t7;
        TextView t8;
    }
}

