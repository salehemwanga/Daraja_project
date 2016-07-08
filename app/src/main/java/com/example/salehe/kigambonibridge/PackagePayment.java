package com.example.salehe.kigambonibridge;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.app.Activity;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

public class PackagePayment extends Activity implements View.OnClickListener,Animation.AnimationListener {

    LinearLayout mpesaLayout,tigopesaLayout;
    TextView gotoweb;
    Animation animFadein;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_package_payment);
        TextView mpesa = (TextView) findViewById(R.id.m_pesa);
        TextView tigo = (TextView) findViewById(R.id.tigoPesa);
        mpesaLayout = (LinearLayout)findViewById(R.id.m_pesa_layout);
        tigopesaLayout = (LinearLayout)findViewById(R.id.tigo_pesa_layout);
        gotoweb = (TextView)findViewById(R.id.goToWeb);

        animFadein = AnimationUtils.loadAnimation(getApplicationContext(),
                R.anim.fade_in);
        animFadein.setAnimationListener(this);
        gotoweb.startAnimation(animFadein);


        mpesa.setOnClickListener(this);
        tigo.setOnClickListener(this);
        gotoweb.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.m_pesa:
                mpesaLayout.setVisibility(View.GONE);
                if(mpesaLayout.getVisibility()==View.GONE){
                    mpesaLayout.setVisibility(View.VISIBLE);
                    tigopesaLayout.setVisibility(View.GONE);
                }

                break;

            case R.id.tigoPesa:
                Toast.makeText(getApplicationContext(),"tigo",Toast.LENGTH_SHORT).show();
                if(tigopesaLayout.getVisibility()==View.GONE){
                    mpesaLayout.setVisibility(View.GONE);
                    tigopesaLayout.setVisibility(View.VISIBLE);
                }
                break;
            case R.id.goToWeb:
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.google.com"));
                startActivity(browserIntent);
                break;
        }
    }

    @Override
    public void onAnimationStart(Animation animation) {
        Toast.makeText(getApplicationContext(), "Animation start",
                Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onAnimationEnd(Animation animation) {
        if (animation == animFadein) {
            Toast.makeText(getApplicationContext(), "Animation Stopped",
                    Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onAnimationRepeat(Animation animation) {
        Toast.makeText(getApplicationContext(), "Animation repeat",
                Toast.LENGTH_SHORT).show();

    }
}
