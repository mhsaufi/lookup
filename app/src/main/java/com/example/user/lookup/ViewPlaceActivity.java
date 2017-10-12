package com.example.user.lookup;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import it.sephiroth.android.library.picasso.Picasso;

public class ViewPlaceActivity extends AppCompatActivity {

    String lat, lng, open, status, name, address, rating, phone, comment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_place);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        Intent intent = getIntent();
        String url = intent.getStringExtra("WEBURL");
        lat = intent.getStringExtra("LAT");
        lng = intent.getStringExtra("LNG");
        name = intent.getStringExtra("NAME");
        address = intent.getStringExtra("ADDRESS");
        rating = intent.getStringExtra("RATING");
        open = intent.getStringExtra("OPEN");
        status = intent.getStringExtra("STATUS");
        phone = intent.getStringExtra("PHONE");
        comment = intent.getStringExtra("COMMENT");

        getSupportActionBar().setTitle(name);

        Button button_go = (Button) findViewById(R.id.go_here);
        ImageView photo = (ImageView) findViewById(R.id.interaction_photo);
//        TextView i_name = (TextView) findViewById(R.id.interaction_name);
        TextView i_isOpen = (TextView) findViewById(R.id.interaction_isOpen);
        TextView i_rating = (TextView) findViewById(R.id.interaction_rating);
        TextView i_phone = (TextView) findViewById(R.id.interaction_phone);
        TextView i_address = (TextView) findViewById(R.id.interaction_address);
        TextView i_comment = (TextView) findViewById(R.id.interaction_comment);

        Log.d("TESTER",open);
        Log.d("TESTER",status);


        if(open.equals("open")){
            i_isOpen.setBackgroundColor(getResources().getColor(android.R.color.holo_green_dark));
            i_isOpen.setText("Open - " + status);
        }else if(open.equals("close")){
            i_isOpen.setBackgroundColor(getResources().getColor(android.R.color.holo_red_dark));
            i_isOpen.setText("Closed - " + status);
        }else{
            i_isOpen.setBackgroundColor(getResources().getColor(android.R.color.black));
            i_isOpen.setText("Maybe closed");
        }

//        i_name.setText(name);
        i_rating.setText(rating + "/10");
        i_phone.setText(phone);
        i_address.setText(address);
        i_comment.setText("\"" + comment + "\"");

        Picasso.with(this).load(url).into(photo);

        Animation anim = AnimationUtils.loadAnimation(this,R.anim.bounce);
        button_go.setVisibility(View.INVISIBLE);
        button_go.startAnimation(anim);

        button_go.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(android.content.Intent.ACTION_VIEW,
                Uri.parse("http://maps.google.com/maps?daddr="+lat+","+lng));

                startActivity(intent);

            }
        });

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        finish();

        return true;
    }

    public class MyWebViewClient extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            view.loadUrl(url);
            return true;
        }
    }

}
