package com.example.steven.wheateritems;

import android.graphics.Bitmap;

/**
 * Created by Steven on 30/10/14.
 */
public class Weather {

    private String icon;
    private String title;
    private long minTemp;
    private long maxTemp;
    private String main;
    private String description;

    public Weather(String icon, String title, long minTemp, long maxTemp, String main, String description) {
        this.icon = "http://openweathermap.org/img/w/"+icon+".png";
        this.title = title;
        this.minTemp = convertToCelcius(minTemp);
        this.maxTemp = convertToCelcius(maxTemp);
        this.main = main;
        this.description = description;
    }

    public String getIcon() {
        return icon;
    }


    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public long getMinTemp() {
        return minTemp;
    }

    public void setMinTemp(long minTemp) {
        this.minTemp = minTemp;
    }

    public long getMaxTemp() {
        return maxTemp;
    }

    public void setMaxTemp(long maxTemp) {
        this.maxTemp = maxTemp;
    }

    public String getMain() {
        return main;
    }

    public void setMain(String main) {
        this.main = main;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    private long convertToCelcius(long kelvin)
    {
        return kelvin - 273;
    }
}
