package com.example.user.myrapid;

import android.content.Intent;
import android.media.MediaCodec;
import android.os.PatternMatcher;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import java.util.regex.*;

import it.sephiroth.android.library.picasso.Picasso;

public class SearchActivity extends AppCompatActivity{

    AutoCompleteTextView from, to;
    Button button_search, button_route;
    Intent intent;
    ImageView background_image;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("RapidKL Trip Planner");

        String[] station = getResources().getStringArray(R.array.lrt_stop);

        from = (AutoCompleteTextView) findViewById(R.id.from);
        to = (AutoCompleteTextView) findViewById(R.id.to);
        background_image = (ImageView) findViewById(R.id.bg);

        Picasso.with(this).load(R.drawable.bg).into(background_image);

        ArrayAdapter<String> adapter = new ArrayAdapter<String>
                (this,android.R.layout.simple_list_item_1,station);

        from.setAdapter(adapter);
        from.setThreshold(2);

        to.setAdapter(adapter);
        to.setThreshold(2);

        button_search = (Button) findViewById(R.id.search_trip);
        button_route = (Button) findViewById(R.id.view_route_map);

        button_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String from_location = from.getText().toString();
                String to_location = to.getText().toString();

                String pattern1 = ".*KJ .*";
                String pattern2 = ".*AG .*";

                boolean isMatch1 = Pattern.matches(pattern1, from_location);
                boolean isMatch2 = Pattern.matches(pattern2,from_location);
                boolean isMatch3 = Pattern.matches(pattern1, to_location);
                boolean isMatch4 = Pattern.matches(pattern2, to_location);

                intent = new Intent(SearchActivity.this, ResultActivity.class);

                intent.putExtra("FROM",from_location);
                intent.putExtra("TO",to_location);

                if(isMatch1 == true || isMatch2 == true){

                    if(isMatch3 == true || isMatch4 == true){
                        if(from_location.equals(to_location)){

                            Toast.makeText(SearchActivity.this, "Cannot go to the same place", Toast.LENGTH_SHORT).show();

                        }else{

                            startActivity(intent);

                        }
                    }else{

                        Toast.makeText(SearchActivity.this, "Cannot look for your destination LRT Station", Toast.LENGTH_SHORT).show();

                    }

                }else{

                    Toast.makeText(SearchActivity.this, "Cannot look for your LRT Station", Toast.LENGTH_SHORT).show();
                }

            }
        });

        button_route.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                intent = new Intent(SearchActivity.this, RouteActivity.class);
                startActivity(intent);

            }
        });

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        finish();

        return true;
    }
}