package com.example.steven.wheateritems;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.ResponseHandler;
import org.apache.http.impl.client.BasicResponseHandler;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;
import java.util.Scanner;

/**
 * Created by Steven on 30/12/14.
 */
public class HandleJson implements ResponseHandler<Weather>{

    private static final String TAG = "HandleJson";
    @Override
    public Weather handleResponse(HttpResponse httpResponse)
            throws ClientProtocolException, IOException {

            Weather weather1 = null;
            String JSONResp = new BasicResponseHandler().handleResponse(httpResponse);


        try {
            JSONObject responseObject = (JSONObject)new JSONTokener(JSONResp).nextValue();
            JSONArray weatherJsonArray = responseObject.getJSONArray("weather");
            JSONObject weatherJson = (JSONObject)weatherJsonArray.get(0);
            String imageUrl = weatherJson.getString("icon");
            Log.d(TAG,"img: "+imageUrl);
            //Bitmap bitmap = BitmapFactory.decodeStream("http://openweathermap.org/img/w/"+imageUrl+".png");
            String main = weatherJson.getString("main");
            Log.d(TAG,"main: "+main);
            String description = weatherJson.getString("description");
            Log.d(TAG,"descr: "+description);
            JSONObject mainJson = responseObject.getJSONObject("main");
            long minTemp = mainJson.getLong("temp_min");
            Log.d(TAG,"min: "+minTemp);
            long maxTemp = mainJson.getLong("temp_max");
            Log.d(TAG,"max: "+maxTemp);
            String title = responseObject.getString("name");
            weather1 = new Weather(imageUrl,title,minTemp,maxTemp,main,description);



        } catch (JSONException e) {
            e.printStackTrace();
        }


        return weather1;
    }
}
