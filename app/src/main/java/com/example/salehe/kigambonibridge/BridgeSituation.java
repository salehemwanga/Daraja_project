package com.example.salehe.kigambonibridge;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;



public class BridgeSituation extends AppCompatActivity {

    Config conf = new Config();
    public  String value;
    public  String value2;

    public class Wrapper {
        public String JSON_STRING;
        public String JSON_STRING1;
    }

    Wrapper w = new Wrapper();

    private ListView listView, listView1;
    public TextView name, cost, status;

//    public static String id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bridge_situation);

        listView = (ListView) findViewById(R.id.listView);
        listView1 = (ListView) findViewById(R.id.listView1);
        Intent intent = getIntent();
    value = intent.getStringExtra("key");


//        Toast.makeText(getApplication(),value, Toast.LENGTH_SHORT).show();
        getJSON();
    }




    private void showBridgeSituation() {

//        Wrapper w = new Wrapper();
        JSONObject jsonObject = null;
        ArrayList<HashMap<String, String>> list = new ArrayList<HashMap<String, String>>();
//        ArrayList<HashMap<String,String>> list1 = new ArrayList<HashMap<String, String>>();
        try {
            jsonObject = new JSONObject(w.JSON_STRING);
            JSONArray result = jsonObject.getJSONArray(Config.TAG_JSON_ARRAY);

            for (int i = 0; i < result.length(); i++) {
                JSONObject jo = result.getJSONObject(i);
                String time = jo.getString(Config.TAG_TIME);
                String situation = jo.getString(Config.TAG_STATUS);
                String id = jo.getString(Config.STATUS_ID);

//                setSomeVariable(id);

//                showCost(id);
//                Toast.makeText(getApplicationContext(),id,Toast.LENGTH_SHORT).show();

                HashMap<String, String> employees = new HashMap<>();
                employees.put(Config.TAG_TIME, time);
                employees.put(Config.STATUS_ID, id);
                employees.put(Config.TAG_STATUS, situation);
                list.add(employees);

//                id = Config.STATUS_ID;
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

        ListAdapter adapter = new SimpleAdapter(
                BridgeSituation.this, list, R.layout.list_view,
//                new String[]{Config.TAG_TIME, Config.TAG_STATUS, Config.STATUS_ID},
                new String[]{Config.TAG_TIME, Config.TAG_STATUS},
                new int[]{R.id.tvTime, R.id.tvStatus});
        listView.setAdapter(adapter);
    }




    private void showCost(){
        Toast.makeText(getApplication(),value, Toast.LENGTH_SHORT).show();
        JSONObject jsonObject = null;
        ArrayList<HashMap<String,String>> list = new ArrayList<HashMap<String, String>>();
        try {
            jsonObject = new JSONObject(w.JSON_STRING1);
            JSONArray result = jsonObject.getJSONArray(Config.TAG_JSON_ARRAY);

            for(int i = 0; i<result.length(); i++){
                JSONObject jo = result.getJSONObject(i);
                String name = jo.getString(Config.TAG_VEHICLE_TYPE);
                String price = jo.getString(Config.TAG_COST);
                String status = jo.getString(Config.TAG_STATUS_COST);

                HashMap<String,String> employees = new HashMap<>();
                employees.put(Config.TAG_VEHICLE_TYPE,name);
                employees.put(Config.TAG_STATUS_COST,status);
                employees.put(Config.TAG_COST,price);
                list.add(employees);
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

        ListAdapter adapter = new SimpleAdapter(
                BridgeSituation.this, list, R.layout.list_view_cost,
                new String[]{Config.TAG_VEHICLE_TYPE,Config.TAG_STATUS_COST,Config.TAG_COST},
                new int[]{R.id.tvVehicleType,R.id.tvStatusCost, R.id.tvCost});
        listView1.setAdapter(adapter);
    }


    private void getJSON() {
        class GetJSON extends AsyncTask<Void, Void, Wrapper> {

            ProgressDialog loading;

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(BridgeSituation.this, "Fetching Data", "Wait...", false, false);
            }

            @Override
            protected void onPostExecute(Wrapper s) {

                super.onPostExecute(s);
                loading.dismiss();
                w.JSON_STRING = s.JSON_STRING;
//               w.JSON_STRING1 = s.JSON_STRING1;
                showBridgeSituation();
                showCost();
            }

            @Override
            protected Wrapper doInBackground(Void... params) {
                RequestHandler rh = new RequestHandler();
                w.JSON_STRING = rh.sendGetRequest(Config.URL_GET_SITUATION);
                w.JSON_STRING1  = rh.sendGetRequestParam(Config.URL_GET_COST,value);
                return w;
            }
        }
        GetJSON gj = new GetJSON();
        gj.execute();
    }

}
