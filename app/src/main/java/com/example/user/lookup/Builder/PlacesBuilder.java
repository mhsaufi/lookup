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
    // Using FourSquare Endpoint to list places around the given location
    public void lookPlace(final Callback call){

//        final String real_url = googlePlaceUrl();

        final String real_url = fourSquarePlaceApi();

        Log.d("JSON",real_url);

        StringRequest request = new StringRequest(Request.Method.GET, real_url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                call.onSuccess(response);

            }

        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Toast.makeText(context,"UNKNOWN : " + error.toString(),Toast.LENGTH_LONG ).show();

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

    public String googlePlaceUrl(){

        String url = "https://maps.googleapis.com/maps/api/place/nearbysearch/json?location=";
        String location = main_point;
        String type = "&type=atm,bank,beauty_salon,book_store,bowling_alley,bus_station,cafe," +
                "car_repair,clothing_store,convenience_store,doctor," +
                "gas_station,gym,hair_care,hospital,laundry,mosque,movie_theater,night_club," +
                "pet_store,pharmacy,police,restaurant,shopping_mall,spa,train_station," +
                "transit_station,veterinary_care";

        String radius = "&radius=1000&key=";
        String key = "AIzaSyDcda4oK9STrZkUBC77NfWDVdsXEIPNrTY ";

        String real_url = url + location + type + radius + key;

        return real_url;

    }

    public String fourSquarePlaceApi(){

        String CLIENTID = "VDOX0VBX4VNV4ZV0GPONZ4ZRJEBXKHIAGH0NNNQDEGHPYMKR";

        String CLIENTSECRET = "3KXSKE50Z0VGPMBSUXB2UBGN2QAYTAGNBPW1AI0BW5N01OMS";

        String url = "https://api.foursquare.com/v2/venues/explore?";

        String client_id = "client_id=" + CLIENTID;

        String client_secret = "&client_secret=" + CLIENTSECRET + "&v=20170615";

        String ll = "&ll=" + main_point;

        String limit = "&limit=50";

        String venuePhotos = "&venuePhotos=1";

        String real_url = url + client_id + client_secret + ll + limit + venuePhotos;


        return real_url;
    }

    public interface Callback{
        void onSuccess(String result);
    }
}
