package com.example.salehe.kigambonibridge;

import android.app.Activity;
import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class TrafficNews extends Activity {
    public static String[] imageURLs;
    public static Bitmap[] bitmaps;

    public String JSON_STRING;
    private ListView listView;
    private ImageView image;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_traffic_news);

        listView = (ListView) findViewById(R.id.listViewTraffic);
//        image = (ImageView) findViewById(R.id.imageTraffic);
//        image.setImageResource(R.drawable.accident);

        getJSON();
    }


    private void showTrafficNews(){
        JSONObject jsonObject = null;
        ArrayList<HashMap<String,String>> list = new ArrayList<HashMap<String, String>>();
//        ArrayList<HashMap<String,String>> list1 = new ArrayList<HashMap<String, String>>();
        try {
            jsonObject = new JSONObject(JSON_STRING);
            JSONArray result = jsonObject.getJSONArray(Config.TAG_JSON_ARRAY);

            for(int i = 0; i<result.length(); i++){
                JSONObject jo = result.getJSONObject(i);
                String desc = jo.getString(Config.TAG_DESC);
                String inctype = jo.getString(Config.TAG_INCTYPE);
                String updatedTime = jo.getString(Config.TAG_UPDATEDTIME);
                String image1 = jo.getString(Config.TAG_IMAGE); //no problem/correct

                HashMap<String,String> employees = new HashMap<>();
                employees.put(Config.TAG_DESC,desc);
                employees.put(Config.TAG_INCTYPE,inctype);
                employees.put(Config.TAG_UPDATEDTIME,updatedTime);
                employees.put(Config.TAG_IMAGE,image1);
                list.add(employees);
            }

        }catch (JSONException e) {
            e.printStackTrace();
        }

        ListAdapter adapter = new SimpleAdapter(
                TrafficNews.this, list, R.layout.list_view_trafficnews,
                new String[]{Config.TAG_DESC,Config.TAG_IMAGE,Config.TAG_UPDATEDTIME},
                new int[]{R.id.tvDesc, R.id.imageTraffic,R.id.tvUpdatedtime});
        listView.setAdapter(adapter);

//        Toast.makeText(getApplicationContext(),Config.TAG_IMAGE,Toast.LENGTH_SHORT).show();
    }

    private void getJSON(){
        class GetJSON extends AsyncTask<Void,Void,String> {
            ProgressDialog loading;

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(TrafficNews.this,"Fetching Data","Wait...",false,false);
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                loading.dismiss();
                JSON_STRING = s;
                showTrafficNews();
            }

            @Override
            protected String doInBackground(Void... params) {
                RequestHandler rh = new RequestHandler();

                String s  = rh.sendGetRequest(Config.URL_GET_NEWS);
                return s ;
            }
        }
        GetJSON gj = new GetJSON();
        gj.execute();
    }
}
