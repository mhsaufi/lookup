package com.example.user.myrapid.Builder;

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
 * Created by HABIB on 29/5/2017.
 */

public class FareBuilder {

    private String from_station, to_station;
    private Context context;

    public FareBuilder(String from_station, String to_station, Context context){

        this.from_station = from_station;
        this.to_station = to_station;
        this.context = context;

    }

    public void fareGrab(final Callback call){

        String url = "https://maps.googleapis.com/maps/api/directions/json?";
        final String transit_mode = "train";
        final String mode = "transit";

        final String key = "AIzaSyDguAtCDPNZRSsoIdRVLzVyQ7zSt1sdfdk ";

        String real_url = "origin="
                + from_station
                + "&destination="
                + to_station
                + "&mode="
                + mode
                + "&key="
                + key;

        //String encoded_url = URLEncoder.encode(real_url, "UTF-8");

        Log.d("URL", url+real_url);

        StringRequest request = new StringRequest(Request.Method.POST, url + real_url, new Response.Listener<String>() {
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
