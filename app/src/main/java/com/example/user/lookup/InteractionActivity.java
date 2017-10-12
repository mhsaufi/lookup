package com.example.user.lookup;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.Toast;

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

//                            Log.d("TESTER",name + ", " + test);
                            status = jsonObject1.getString("status");

                        }catch(Exception e){

//                            Log.d("TESTER",name);

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
                        String real_url = "";

                        try{
                            //get icon and photo
                            String photos = jsonObject3.getString("featuredPhotos");

                            JSONObject jsonObject5 = new JSONObject(photos);

                            String groups_photo = jsonObject5.getJSONArray("items").get(0).toString();

                            JSONObject jsonObject7 = new JSONObject(groups_photo);

                            String prefix = jsonObject7.getString("prefix");
                            String suffix = jsonObject7.getString("suffix");

                            icon = prefix + "50x50" + suffix;
                            real_url = prefix + "150x250" + suffix;

                        }catch(Exception e){

                            icon = "http://www.iconsdb.com/icons/preview/gray/google-place-optimization-xxl.png";

                        }
                        interactionlists.add(new InteractionList(name, icon, rating, address, lat, lng, real_url, hours_value, status, phone, comment));
                    }

                    InteractionListAdapter adapter = new InteractionListAdapter(InteractionActivity.this, interactionlists);

                    interactionView.setAdapter(adapter);

                }catch(Exception e){

//                    Log.d("KOTARAYA","KITAAA : " + e.toString());

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
