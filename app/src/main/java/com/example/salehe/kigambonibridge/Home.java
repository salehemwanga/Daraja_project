package com.example.salehe.kigambonibridge;

import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.HashMap;

public class Home extends AppCompatActivity implements View.OnClickListener {
    public class Wrapper {
        public String JSON_STRING;
    }
    Wrapper w = new Wrapper();

    TextView tvHome;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
       /* Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);*/
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

//        Toast.makeText(getApplicationContext(),Config.STATUS_ID,Toast.LENGTH_SHORT).show();

        tvHome = (TextView) findViewById(R.id.tvHome);
        LinearLayout btnsituation = (LinearLayout) findViewById(R.id.btnSituation);
        LinearLayout btnnews = (LinearLayout) findViewById(R.id.btnNews);
        Button btnpayment = (Button) findViewById(R.id.btnPayment);
        btnsituation.setOnClickListener(this);
        btnnews.setOnClickListener(this);
        btnpayment.setOnClickListener(this);

        getJSON();

    }
    /*end of oncreate function*/

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_home, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_notification) {
            LayoutInflater layout =LayoutInflater.from(this);
            View promptView = layout.inflate(R.layout.notification_layout,null);




//            Toast.makeText(getApplicationContext(), selection, Toast.LENGTH_SHORT).show();


            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
            alertDialogBuilder.setView(promptView);
            alertDialogBuilder.setCancelable(false)
                    .setPositiveButton("save", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Long alertTime = new GregorianCalendar().getTimeInMillis()+10*1000;
                            Intent alertIntent =  new Intent(Home.this,AlertReceiver.class);

                            AlarmManager alarmNanager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
                            alarmNanager.set(AlarmManager.RTC_WAKEUP,alertTime, PendingIntent.getBroadcast(Home.this,1,alertIntent, PendingIntent.FLAG_UPDATE_CURRENT));
                            Toast.makeText(Home.this, "Notification set", Toast.LENGTH_SHORT).show();
                }
            })
            .setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    Toast.makeText(getApplicationContext(),"canceled", Toast.LENGTH_SHORT).show();
                }
            });

            alertDialogBuilder.create().show();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnSituation:
                Intent i = new Intent(getApplicationContext(), BridgeSituation.class);
                i.putExtra("key", tvHome.getText().toString());
                startActivity(i);
                break;
            case R.id.btnNews:
                startActivity(new Intent(this, TrafficNews.class));
                break;
            case R.id.btnPayment:
                startActivity(new Intent(this, PackagePayment.class));
                break;

           /* case R.id.btnSetNotification:
                Toast.makeText(this,"hello",Toast.LENGTH_SHORT).show();
                break;*/
        }
    }

    private void showBridgeSituation() {

//        Wrapper w = new Wrapper();
        JSONObject jsonObject = null;
        ArrayList<HashMap<String, String>> list = new ArrayList<HashMap<String, String>>();
        try {
            jsonObject = new JSONObject(w.JSON_STRING);
            JSONArray result = jsonObject.getJSONArray(Config.TAG_JSON_ARRAY);

            for (int i = 0; i < result.length(); i++) {
                JSONObject jo = result.getJSONObject(i);
//                String time = jo.getString(Config.TAG_TIME);
                String situation = jo.getString(Config.TAG_STATUS_ID);

                HashMap<String, String> employees = new HashMap<String,String>();
                employees.put(Config.TAG_STATUS_ID, situation);
                employees.put("1",Config.TAG_STATUS_ID);
                list.add(employees);
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
        tvHome.setText(list.get(0).get(Config.TAG_STATUS_ID));
    }

    private void getJSON() {
        class GetJSON extends AsyncTask<Void, Void, Wrapper> {

            ProgressDialog loading;

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(Home.this, "Fetching Data", "Wait...", false, false);
            }

            @Override
            protected void onPostExecute(Wrapper s) {

                super.onPostExecute(s);
                loading.dismiss();
                w.JSON_STRING = s.JSON_STRING;
//               w.JSON_STRING1 = s.JSON_STRING1;
                showBridgeSituation();
//                showCost();
            }

            @Override
            protected Wrapper doInBackground(Void... params) {
                RequestHandler rh = new RequestHandler();
                w.JSON_STRING = rh.sendGetRequest(Config.URL_GET_SITUATION);
//                w.JSON_STRING1  = rh.sendGetRequestParam(Config.URL_GET_COST, "1");
                return w;
            }
        }
        GetJSON gj = new GetJSON();
        gj.execute();
    }



}/*end of class*/
