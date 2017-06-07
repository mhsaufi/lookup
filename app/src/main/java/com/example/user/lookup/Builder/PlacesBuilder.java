package com.example.user.lookup.Builder;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by USER on 31/5/2017.
 */

public class PlacesBuilder {

    String main_point;
    Context context;

    public PlacesBuilder(String main_point, Context context){

        this.main_point = main_point;
        this.context = context;

    }

    public void lookPlace(final Callback call){

        String url = "https://maps.googleapis.com/maps/api/place/nearbysearch/json?location=";

        final String location = main_point;

        final String type = "&type=atm,bank,beauty_salon,book_store,bowling_alley,bus_station,cafe," +
                            "car_repair,clothing_store,convenience_store,doctor," +
                            "gas_station,gym,hair_care,hospital,laundry,mosque,movie_theater,night_club," +
                            "pet_store,pharmacy,police,restaurant,shopping_mall,spa,train_station," +
                            "transit_station,veterinary_care";

        final String radius = "&radius=1000&key=";

        final String key = "AIzaSyDcda4oK9STrZkUBC77NfWDVdsXEIPNrTY ";

        final String real_url = url + location + type + radius + key;

        Log.d("JSON",real_url);

        StringRequest request = new StringRequest(Request.Method.POST, real_url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                call.onSuccess(response);

            }

        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(context,error.toString(),Toast.LENGTH_LONG ).show();
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                Map<String,String> params = new HashMap<String, String>();

                return params;

            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(context);
        requestQueue.add(request);


    }

    public interface Callback{
        void onSuccess(String result);
    }
}
