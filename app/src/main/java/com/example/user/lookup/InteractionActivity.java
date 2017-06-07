package com.example.user.lookup;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.ListView;

import com.example.user.lookup.Adapter.InteractionListAdapter;
import com.example.user.lookup.Builder.InteractionList;
import com.example.user.lookup.Builder.PlacesBuilder;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class InteractionActivity extends AppCompatActivity {

    ListView interactionView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_interaction);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        interactionView = (ListView) findViewById(R.id.place_list);

        Intent intent = getIntent();

        String main_point = intent.getStringExtra("INTERACTIONPOINT");
        String main_point_name = intent.getStringExtra("INTERACTIONCENTER");


        getSupportActionBar().setTitle("What's up " + main_point_name);

        final ArrayList<InteractionList> interactionlists = new ArrayList<>();

        PlacesBuilder placesBuilder = new PlacesBuilder(main_point,this);

        placesBuilder.lookPlace(new PlacesBuilder.Callback() {
            @Override
            public void onSuccess(String result) {

                try{

                    JSONObject object = new JSONObject(result);
                    String results = object.getString("results");

                    JSONArray object1 = new JSONArray(results);

                    int limit = object1.length() - 1;

                    for(int i = 0;i < limit;i++){

                        String test = object1.get(i).toString();

                        JSONObject object2 = new JSONObject(test);

                        String name;
                        try{ name = object2.getString("name");}catch(Exception e){ name = "Unknownn";}

                        String icon;
                        try{

                            icon = object2.getString("icon");

                        }catch(Exception e){

                            icon = "https://www.iconfinder.com/data/icons/gray-toolbar-4/512/question-512.png";

                        }

                        String rating;
                        try{ rating = object2.getString("rating");}catch(Exception e){ rating = "Not rated";}

                        String address;
                        try{ address = object2.getString("vicinity");}catch(Exception e){ address = name;}

                        String geometry = object2.getString("geometry");
                        JSONObject object3 = new JSONObject(geometry);

                        String location = object3.getString("location");
                        JSONObject object4 = new JSONObject(location);

                        String lat = object4.getString("lat");
                        String lng = object4.getString("lng");

                        //get photo
                        String photos = "";
                        String index = "";
                        String html_attributions = "";
                        String photo_url = "";
                        String real_photo_url = "";

                        try{

                            photos = object2.getString("photos");
                            JSONArray jsonArray = new JSONArray(photos);
                            index = jsonArray.get(0).toString();

                            JSONObject jsonObject = new JSONObject(index);
                            html_attributions = jsonObject.getString("html_attributions");
                            JSONArray jsonArray1 = new JSONArray(html_attributions);
                            photo_url = jsonArray1.get(0).toString();

                            real_photo_url = trimURL(photo_url);

                        }catch(Exception e){

                        }

                        Log.d("JSON",real_photo_url);
                        Log.d("JSON",name);
                        Log.d("JSON",address);
                        Log.d("JSON",icon);
                        Log.d("JSON",rating);
                        Log.d("JSON",lat + " , " + lng);

                        interactionlists.add(new InteractionList(name, icon, rating, address, lat, lng, real_photo_url));

                    }

                    Log.d("KOTARAYA","KITAAA");

                    InteractionListAdapter adapter = new InteractionListAdapter(InteractionActivity.this, interactionlists);

                    interactionView.setAdapter(adapter);

                }catch(Exception e){

                    Log.d("KOTARAYA","KITAAA : " + e.toString());

                }

            }
        });

    }

    public String trimURL(String text){

        String links = "";

        String regex = "\\(?\\b(https://|www[.])[-A-Za-z0-9+&amp;@#/%?=~_()|!:,.;]*[-A-Za-z0-9+&amp;@#/%=~_()|]";

        Pattern p = Pattern.compile(regex);
        Matcher m = p.matcher(text);

        if(m.find()){

            String urlStr = m.group();

            if (urlStr.startsWith("(") && urlStr.endsWith(")"))
            {
                urlStr = urlStr.substring(1, urlStr.length() - 1);
            }
            links = urlStr;

        }
        return links;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        finish();

        return true;
    }
}
