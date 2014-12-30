package com.example.steven.wheateritems;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.net.http.AndroidHttpClient;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;

import java.io.IOException;
import java.util.ArrayList;


public class MyWheaterActivity extends Activity implements ActivityInterface {

    private ListView list;
    private Button button;
    private ArrayList<Weather>weathers;
    private WheaterAdapter adapter;
    private EditText searchText;
    private static final String TAG = "MyWheaterActivity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_wheater);
        list = (ListView)findViewById(R.id.wheaterList);
        View header = (View)getLayoutInflater().inflate(R.layout.list_view_header_row,null);
        list.addHeaderView(header);
        button = (Button)findViewById(R.id.search_button);
        searchText = (EditText)findViewById(R.id.wheaterText);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!searchText.getText().toString().isEmpty())
                {
                    Log.d(TAG,"SearchText = "+searchText.getText().toString());
                   new HTTPGetTask(searchText.getText().toString(),MyWheaterActivity.this).execute();
                    searchText.setText("");
                }
                else
                {
                    Toast.makeText(MyWheaterActivity.this,"Text cannot be empty",Toast.LENGTH_LONG).show();
                }
            }
        });


    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.my_wheater, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void startProces() {


    }

    @Override
    public void endProces(Weather weather) {
        if(weathers == null)
        {
            weathers = new ArrayList<Weather>();

        }

        weathers.add(weather);
        if(adapter == null)
        {
            adapter = new WheaterAdapter(this,R.layout.list_view_item_row,weathers);
            list.setAdapter(adapter);
        }
        else
        {
            adapter.notifyDataSetChanged();
        }


    }

    private class HTTPGetTask extends AsyncTask<Void,Void,Weather>
    {   private static final String TAG = "HTTPGetTask";
        private static final String URL = "http://api.openweathermap.org/data/2.5/weather?q=";
        private String searchUrl;
        AndroidHttpClient mClient = AndroidHttpClient.newInstance("");
        private Context context;
        public HTTPGetTask(String search,Context context)
        {
            searchUrl = URL+search;
            Log.d(TAG,"searchUrl : "+searchUrl);
            this.context = context;
        }

        private ProgressDialog dialog;


        @Override
        protected void onPreExecute() {
           dialog = new ProgressDialog(context);
            dialog.setTitle("Loading ... ");
            dialog.show();
        }

        @Override
        protected Weather doInBackground(Void... voids) {
            HttpGet request = new HttpGet(searchUrl);

            HandleJson handleJson = new HandleJson();
            try {
                return mClient.execute(request,handleJson);
            } catch (IOException e) {
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(Weather weather) {
            if(mClient != null)
            {
                mClient.close();
            }
            dialog.dismiss();
            if (weather != null)
            {
                Log.d(TAG,"weather: "+weather.getTitle());
                endProces(weather);
            }
        }
    }
}
