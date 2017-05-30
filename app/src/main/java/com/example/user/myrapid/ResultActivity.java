package com.example.user.myrapid;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.user.myrapid.Adapter.StationListAdapter;
import com.example.user.myrapid.Builder.FareBuilder;
import com.example.user.myrapid.Builder.StationList;

import org.json.JSONArray;
import org.json.JSONObject;

import java.net.URLEncoder;
import java.util.ArrayList;

import it.sephiroth.android.library.picasso.Picasso;

public class ResultActivity extends AppCompatActivity {

    Button from_btn, to_btn;
    TextView price_text, message_text, departure_text, arrival_text, duration_text, distance_text;
    ImageView static_map;
    ListView stationlistvew;
    Intent intent;
    String from, to, from_val, to_val, code_1, code_2;
    Boolean ig_stat = false;

    int ag_interchange = 48;
    int kj_interchange = 12;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        from_btn = (Button) findViewById(R.id.from_btn);
        to_btn = (Button) findViewById(R.id.to_btn);
        price_text = (TextView) findViewById(R.id.cost);
        message_text = (TextView) findViewById(R.id.message);
        stationlistvew = (ListView) findViewById(R.id.listview);

        departure_text = (TextView) findViewById(R.id.view_departure);
        arrival_text = (TextView) findViewById(R.id.view_arrival);
        duration_text = (TextView) findViewById(R.id.view_duration);
        distance_text = (TextView) findViewById(R.id.view_distance);

        intent = this.getIntent();

        from = intent.getStringExtra("FROM");
        to = intent.getStringExtra("TO");

        //work on these string
        String [] arr = from.split(" ", 2);
        String [] arr1 = to.split(" ", 2);

        from_val = arr[1];
        to_val = arr1[1];

        code_1 = arr[0];
        code_2 = arr1[0];

        from_val = "Stesen%25LRT%25" + from_val.replaceAll(" ", "%25");

        to_val = "Stesen%25LRT%25" + to_val.replaceAll(" ", "%25");

        //ed work

        FareBuilder fareBuilder = new FareBuilder(from_val, to_val, this);

        fareBuilder.fareGrab(new FareBuilder.Callback() {
            @Override
            public void onSuccess(String result) {

                try{

                    JSONObject jsonObject = new JSONObject(result);

                    String routes = jsonObject.getJSONArray("routes").get(0).toString();

                    JSONObject jsonObject1 = new JSONObject(routes);

                    String fare = jsonObject1.getString("fare");

                    JSONObject jsonObject2 = new JSONObject(fare);

                    String price = jsonObject2.getString("text");

                    Toast.makeText(ResultActivity.this, "PRICE : " + price, Toast.LENGTH_SHORT).show();

                    from_btn.setText(from);
                    to_btn.setText(to);
                    price_text.setText("Cost : " + price);

                    String legs = jsonObject1.getJSONArray("legs").get(0).toString();

                    JSONObject jsonObject3 = new JSONObject(legs);

                    //===============================================ARRIVAL

                    String arrival_time = jsonObject3.getString("arrival_time");

                    JSONObject jsonObject4 = new JSONObject(arrival_time);

                    String arrival_time_val = jsonObject4.getString("text");

                    arrival_text.setText(arrival_time_val);


                    //===============================================DEPARTURE

                    String departure_time = jsonObject3.getString("departure_time");

                    JSONObject jsonObject5 = new JSONObject(departure_time);

                    String departure_time_val = jsonObject5.getString("text");

                    departure_text.setText(departure_time_val);

                    //===============================================DURATION

                    String duration = jsonObject3.getString("duration");

                    JSONObject jsonObject6 = new JSONObject(duration);

                    String duration_val = jsonObject6.getString("text");

                    duration_text.setText(duration_val);

                    //===============================================DISTANCE

                    String distance = jsonObject3.getString("distance");

                    JSONObject jsonObject7 = new JSONObject(distance);

                    String distance_val = jsonObject7.getString("text");

                    distance_text.setText(distance_val);



                    Log.d("KUKU",distance_val + "-" + duration_val);


                }catch(Exception e){

                }
            }
        });

        String[] station_name_list = getResources().getStringArray(R.array.lrt_stop);

        int start_index = 0;
        int end_index = 0;

        for(int i = 0; i < station_name_list.length; i++){

            if(station_name_list[i].equals(from)){
                start_index = i;
            }

            if(station_name_list[i].equals(to)){
                end_index = i;
            }
        }

        ArrayList<StationList> stationlists = new ArrayList<>();

        if (code_1.equals(code_2)) {

            //same track
            if(start_index > end_index){

                for(int j = start_index; j >= end_index; j--){

                    stationlists.add(new StationList(station_name_list[j]));

                }

            }else{

                for(int j = start_index; j <= end_index; j++){

                    stationlists.add(new StationList(station_name_list[j]));

                }
            }

        }else{

            ig_stat = true;

            if(start_index > end_index){

                message_text.setText("Stop at Masjid Jamek and take Kelana jaya Line to continue your journey");

                if(start_index < ag_interchange){

                    Toast.makeText(this, "KURRRR", Toast.LENGTH_SHORT).show();

                    for(int j = start_index; j <= ag_interchange; j++){

                        stationlists.add(new StationList(station_name_list[j]));

                    }

                    if(end_index > kj_interchange){

                        for(int h = kj_interchange; h <= end_index; h++){

                            stationlists.add(new StationList(station_name_list[h]));

                        }

                    }else{

                        for(int h = kj_interchange; h >= end_index; h--){

                            stationlists.add(new StationList(station_name_list[h]));

                        }

                    }


                }else{

                    for(int j = start_index; j >= ag_interchange; j--){

                        stationlists.add(new StationList(station_name_list[j]));

                    }

                    if(end_index > kj_interchange){

                        for(int h = kj_interchange; h <= end_index; h++){

                            stationlists.add(new StationList(station_name_list[h]));

                        }

                    }else{

                        for(int h = kj_interchange; h >= end_index; h--){

                            stationlists.add(new StationList(station_name_list[h]));

                        }

                    }

                }


            }else{

                message_text.setText("Stop at Masjid Jamek and take Ampang Line to continue your journey");

                for(int j = start_index; j <= kj_interchange; j++){

                    stationlists.add(new StationList(station_name_list[j]));

                }

                if(end_index > ag_interchange){

                    for(int h = ag_interchange; h <= end_index; h++){

                        stationlists.add(new StationList(station_name_list[h]));
                    }

                }else{

                    for(int h = ag_interchange; h >= end_index; h--){

                        stationlists.add(new StationList(station_name_list[h]));
                    }
                }

            }

        }

        StationListAdapter adapter = new StationListAdapter(ig_stat,ResultActivity.this, stationlists);

        stationlistvew.setAdapter(adapter);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        finish();

        return true;
    }
}
