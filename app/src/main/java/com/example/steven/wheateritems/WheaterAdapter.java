package com.example.steven.wheateritems;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

import java.util.ArrayList;

/**
 * Created by Steven on 30/10/14.
 */
public class WheaterAdapter extends BaseAdapter {

    Context context;
    int layoutResourceId;
    ArrayList<Weather> data = null;
    private static final String TAG = "WheaterAdapter";

    public WheaterAdapter(Context context, int resource,ArrayList<Weather>data) {


        this.context = context;
        this.layoutResourceId =resource;
        this.data = data;

    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public Object getItem(int i) {
        return data.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
       View row  = convertView;

        WeatherHolder holder = null;
        if (row == null)
        {
            LayoutInflater inflater = ((Activity)context).getLayoutInflater();
            row = inflater.inflate(layoutResourceId,parent,false);
            holder = new WeatherHolder();
            holder.iconView = (ImageView)row.findViewById(R.id.List_imageView);
            holder.titleView = (TextView)row.findViewById(R.id.cityTextView);
            holder.minView = (TextView)row.findViewById(R.id.minTextView);
            holder.maxView = (TextView)row.findViewById(R.id.maxTextView);
            holder.mainView = (TextView)row.findViewById(R.id.mainTextView);
            holder.descriptionView = (TextView)row.findViewById(R.id.descriptionTextView);

            row.setTag(holder);

        }
        else
        {
            holder = (WeatherHolder)row.getTag();
        }
        Weather weather = data.get(position);
        holder.titleView.setText(weather.getTitle());
        Log.d(TAG,"icon : "+weather.getIcon());
        Picasso.with(context).load(weather.getIcon()).resize(100,100).centerCrop().into(holder.iconView);
        holder.mainView.setText(weather.getMain());
        holder.descriptionView.setText(weather.getDescription());
        holder.minView.setText("min : "+weather.getMinTemp()+"°C");
        holder.maxView.setText("max : "+weather.getMaxTemp()+"°C");



        //nog afwerken
        return row;
    }

    static class WeatherHolder
    {
        ImageView iconView;
        TextView titleView;
        TextView minView;
        TextView maxView;
        TextView mainView;
        TextView descriptionView;
    }
}
