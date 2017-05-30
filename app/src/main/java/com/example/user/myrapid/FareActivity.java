package com.example.user.myrapid;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

public class FareActivity extends AppCompatActivity implements Button.OnClickListener{

    Button btn1, btn2, btn3, btn4, btn5, btn6;

    Intent intent_web;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fare);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("RapidKL Fares");

        btn1 = (Button) findViewById(R.id.lrt_cash);
        btn2 = (Button) findViewById(R.id.lrt_tg);
        btn3 = (Button) findViewById(R.id.lrt_weekly);
        btn4 = (Button) findViewById(R.id.lrt_monthly);

        btn5 = (Button) findViewById(R.id.mrt_cash);
        btn6 = (Button) findViewById(R.id.mrt_tg);

        btn1.setOnClickListener(this);
        btn2.setOnClickListener(this);
        btn3.setOnClickListener(this);
        btn4.setOnClickListener(this);
        btn5.setOnClickListener(this);
        btn6.setOnClickListener(this);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        finish();

        return true;
    }

    @Override
    public void onClick(View v) {

        int id_btn = v.getId();

        if(id_btn == R.id.lrt_cash){

            intent_web = new Intent(Intent.ACTION_VIEW,
                    Uri.parse("https://www.myrapid.com.my/clients/Myrapid_Prasarana_37CB56E7-2301-" +
                            "4302-9B98-DFC127DD17E9/contentms/doc/20161115_cash_fare_table.pdf"));

        }else if(id_btn == R.id.lrt_tg){

            intent_web = new Intent(Intent.ACTION_VIEW,
                    Uri.parse("https://www.myrapid.com.my/clients/Myrapid_Prasarana_37CB56E7-2301-" +
                            "4302-9B98-DFC127DD17E9/contentms/doc/20161115_cashless_fare_table.pdf"));

        }else if(id_btn == R.id.lrt_weekly){

            intent_web = new Intent(Intent.ACTION_VIEW,
                    Uri.parse("https://www.myrapid.com.my/clients/Myrapid_Prasarana_37CB56E7-2301-" +
                            "4302-9B98-DFC127DD17E9/contentms/img/20160616_faretable_smart7.pdf"));

        }else if(id_btn == R.id.lrt_monthly){

            intent_web = new Intent(Intent.ACTION_VIEW,
                    Uri.parse("https://www.myrapid.com.my/clients/Myrapid_Prasarana_37CB56E7-2301-" +
                            "4302-9B98-DFC127DD17E9/contentms/img/20160616_faretable_smart30.pdf"));

        }else if(id_btn == R.id.mrt_cash){

            intent_web = new Intent(Intent.ACTION_VIEW,
                    Uri.parse("https://www.myrapid.com.my/clients/Myrapid_Prasarana_37CB56E7-2301-" +
                            "4302-9B98-DFC127DD17E9/contentms/img/20161117_mrt_sbk_cash_fare_table_3.jpg"));


        }else if(id_btn == R.id.mrt_tg){

            intent_web = new Intent(Intent.ACTION_VIEW,
                    Uri.parse("https://www.myrapid.com.my/clients/Myrapid_Prasarana_37CB56E7-2301-" +
                            "4302-9B98-DFC127DD17E9/contentms/img/20161117_mrt_sbk_cashless_fare_table_3.jpg"));

        }

        startActivity(intent_web);

    }
}
