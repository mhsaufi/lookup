package com.example.user.lookup;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import it.sephiroth.android.library.picasso.Picasso;

public class AboutActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("About LookUp!");

        ImageView company_logo = (ImageView) findViewById(R.id.company_logo);
        TextView apps_name = (TextView) findViewById(R.id.apps_full_name);
        TextView apps_desc = (TextView) findViewById(R.id.apps_description);

        Picasso.with(this).load(R.drawable.lookup_logo).into(company_logo);
        apps_name.setText(R.string.apps_full_name);
        apps_desc.setText(R.string.apps_description);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        finish();

        return true;
    }
}
