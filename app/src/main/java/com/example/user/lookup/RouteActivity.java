package com.example.user.lookup;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;

import com.davemorrissey.labs.subscaleview.ImageSource;
import com.davemorrissey.labs.subscaleview.SubsamplingScaleImageView;

public class RouteActivity extends AppCompatActivity {

    SubsamplingScaleImageView route_img;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_route);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Public Transit");

        route_img = (SubsamplingScaleImageView) findViewById(R.id.route_map);

        route_img.setImage(ImageSource.resource(R.drawable.maps));

        route_img.getCenter();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        finish();

        return true;
    }
}
