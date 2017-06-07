package com.example.user.lookup;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.Toast;

import com.example.user.lookup.Adapter.InteractionListAdapter;
import com.example.user.lookup.Builder.InteractionList;
import com.example.user.lookup.Builder.PlacesBuilder;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.places.Places;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.regex.Pattern;
import java.util.regex.Matcher;

public class LookAroundActivity extends AppCompatActivity implements
        GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener{

    GoogleApiClient googleApiClient;
    String lat,lng;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_look_around);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Intent intent = getIntent();
        String main_point_name = intent.getStringExtra("INTERACTIONCENTER");
        getSupportActionBar().setTitle("What's up " + main_point_name);

        //connecting to GoogleApiClient
        googleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this /* FragmentActivity */,
                        this /* OnConnectionFailedListener */)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .addApi(Places.GEO_DATA_API)
                .addApi(Places.PLACE_DETECTION_API)
                .build();

        googleApiClient.connect();

    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {

        Location current_location;

        if (ActivityCompat.checkSelfPermission(this,
                android.Manifest.permission.ACCESS_FINE_LOCATION) !=
                PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(this,
                        android.Manifest.permission.ACCESS_COARSE_LOCATION) !=
                        PackageManager.PERMISSION_GRANTED)
        {
            Toast.
                    makeText(this, "Location permission denied", Toast.
                            LENGTH_SHORT).show();
        } else {

            try{

                //fetch current location using FusedLocationApi
                current_location = LocationServices.FusedLocationApi.getLastLocation(googleApiClient);

                lat = Double.toString(current_location.getLatitude());
                lng = Double.toString(current_location.getLongitude());

                getFareBuilder();

            }catch(Exception e){

                Toast
                        .makeText(this,"Failed getting your current location, sorry. Check your " +
                                "location setting and try again.", Toast.LENGTH_SHORT).show();

            }
        }

    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    public void getFareBuilder(){

        final ListView interactionView = (ListView) findViewById(R.id.place_list);

        final ArrayList<InteractionList> interactionlists = new ArrayList<>();
        PlacesBuilder placesBuilder = new PlacesBuilder(lat + "," + lng,this);

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

                        interactionlists.add(new InteractionList(name, icon, rating, address, lat, lng,real_photo_url));

                    }

                    InteractionListAdapter adapter = new InteractionListAdapter(LookAroundActivity.this, interactionlists);

                    interactionView.setAdapter(adapter);

                }catch(Exception e){

                    Toast.makeText(LookAroundActivity.this, "Failed looking around you for places.", Toast.LENGTH_SHORT).show();

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
