package com.example.user.lookup;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.webkit.WebView;

public class TestActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        WebView map_web = (WebView) findViewById(R.id.web_map);

        String start_address = "Platinum Walk";

        String end_address = "Jusco Wangsa Maju";

        String google_url = "http://maps.google.com/maps?saddr="+ start_address +"&daddr="+  end_address;

        Log.d("URL",google_url);

        map_web.getSettings().setJavaScriptEnabled(true);
        map_web.loadUrl(google_url);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        finish();

        return true;

    }
}
