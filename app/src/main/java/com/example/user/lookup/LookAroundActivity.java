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
//        getSupportActionBar().setTitle("What's up " + main_point_name);

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

//        if (ActivityCompat.checkSelfPermission(this,
//                android.Manifest.permission.ACCESS_FINE_LOCATION) !=
//                PackageManager.PERMISSION_GRANTED &&
//                ActivityCompat.checkSelfPermission(this,
//                        android.Manifest.permission.ACCESS_COARSE_LOCATION) !=
//                        PackageManager.PERMISSION_GRANTED)
        if (ActivityCompat.checkSelfPermission(this,
                    android.Manifest.permission.ACCESS_FINE_LOCATION) !=
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
                    String response = object.getString("response");

                    JSONObject object1 = new JSONObject(response);

                    String headerLocation = object1.getString("headerLocation");

                    getSupportActionBar().setTitle(headerLocation);

                    String groups = object1.getJSONArray("groups").get(0).toString();

                    JSONObject object2 = new JSONObject(groups);

                    String items = object2.getString("items");

                    JSONArray jsonArray = new JSONArray(items);

                    int limit = jsonArray.length() - 1;

                    for(int i = 0;i < limit;i++){

                        String summary = jsonArray.get(i).toString();

                        JSONObject jsonObject = new JSONObject(summary);

                        String venue = jsonObject.getString("venue");

                        JSONObject jsonObject3 = new JSONObject(venue);

                        String name = jsonObject3.getString("name");

                        String rating = jsonObject3.getString("rating");

                        String location = jsonObject3.getString("location");

                        //get another info

                        String hours = "";
                        Boolean hours_value = false;
                        String status = "No status";
                        String contact = "";
                        String phone = "Not given";
                        String test = "";
                        String comment = "";

                        try{

                            hours = jsonObject3.getString("hours");
                            JSONObject jsonObject1 = new JSONObject(hours);
                            hours_value = jsonObject1.getBoolean("isOpen");

                            if(hours_value == true){
                                test = "open";
                            }else{
                                test = "close";
                            }

                            Log.d("TESTER",name + ", " + test);
                            status = jsonObject1.getString("status");

                        }catch(Exception e){

                            Log.d("TESTER",name);

                        }

                        try{
                            contact = jsonObject3.getString("contact");
                            JSONObject jsonObject11 = new JSONObject(contact);
                            phone = jsonObject11.getString("phone");

                        }catch(Exception e){

                        }

                        try{

                            String text_comment = jsonObject.getString("tips");

                            JSONArray jsonArray1 = new JSONArray(text_comment);

                            String text = jsonArray1.get(0).toString();

                            JSONObject jsonObject13 = new JSONObject(text);

                            comment = jsonObject13.getString("text");

                        }catch(Exception e){

                        }

                        //end another info

                        //address
                        JSONObject jsonObject4 = new JSONObject(location);

                        String formatted_address = jsonObject4.getString("formattedAddress");

                        JSONArray jsonArray1 = new JSONArray(formatted_address);

                        int limit_2 = jsonArray1.length() - 1;

                        String address = "";

                        for(int j = 0;j < limit_2;j++){

                            address += jsonArray1.get(j).toString();

                        }

                        String lat = jsonObject4.getString("lat");
                        String lng = jsonObject4.getString("lng");

                        String icon = "";
                        String real_photo_url = "";

                        //get icon and photo
                        String photos = jsonObject3.getString("featuredPhotos");

                        JSONObject jsonObject5 = new JSONObject(photos);

                        String groups_photo = jsonObject5.getJSONArray("items").get(0).toString();

                        JSONObject jsonObject7 = new JSONObject(groups_photo);

                        String prefix = jsonObject7.getString("prefix");
                        String suffix = jsonObject7.getString("suffix");

                        icon = prefix + "50x50" + suffix;

                        real_photo_url = prefix + "768x1024" + suffix;

                        interactionlists.add(new InteractionList(name, icon, rating, address, lat, lng,real_photo_url, hours_value,status, phone, comment));

                    }

                    InteractionListAdapter adapter = new InteractionListAdapter(LookAroundActivity.this, interactionlists);

                    interactionView.setAdapter(adapter);

                }catch(Exception e){

                    Log.d("JSON", e.toString());

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
