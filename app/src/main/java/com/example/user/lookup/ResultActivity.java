package com.example.user.lookup;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.user.lookup.Adapter.StationListAdapter;
import com.example.user.lookup.Builder.FareBuilder;
import com.example.user.lookup.Builder.StationList;

import org.json.JSONObject;

import java.util.ArrayList;

public class ResultActivity extends AppCompatActivity {

    Button from_btn, to_btn;
    TextView price_text, message_text, departure_text, arrival_text, duration_text, distance_text;
    ImageView static_map;
    ListView stationlistvew;
    Intent intent;
    String from, to, from_val, to_val, code_1, code_2;
    Boolean ig_stat = false;

    int ag_interchange = 48;
    int kj_interchange_3 = 14;//kl sentral
    int kj_interchange = 12;//masjid jamek
    int kj_interchange_2 = 11;//dang wangi
    int ml_interchange = 78;
    int ml_interchange_2 = 71;

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

        FareBuilder fareBuilder;
        String ktm_track = "";
        String ktm_track_end = "";
        String indice = "";

        if(code_1.equals("KJ") && code_2.equals("KJ")){      indice = "KJKJ";}
        else if(code_1.equals("AG") && code_2.equals("AG")){ indice = "AGAG";}
        else if(code_1.equals("ML") && code_2.equals("ML")){ indice = "MLML";}
        else if(code_1.equals("KJ") && code_2.equals("AG")){ indice = "KJAG";}
        else if(code_1.equals("AG") && code_2.equals("KJ")){ indice = "AGKJ";}
        else if(code_1.equals("ML") && code_2.equals("KJ")){ indice = "MLKJ";}
        else if(code_1.equals("KJ") && code_2.equals("ML")){ indice = "KJML";}
        else if(code_1.equals("AG") && code_2.equals("ML")){ indice = "AGML";}
        else if(code_1.equals("ML") && code_2.equals("AG")){ indice = "MLAG";}
        else if(code_1.equals("MR") && code_2.equals("MR")){ indice = "MR";}
        else if(code_1.equals("KTM") && code_2.equals("KTM")){ indice = "KTKT";}
        else if(code_1.equals("KTM") && code_2.equals("KJ")){ indice = "KTKJ";}
        else if(code_1.equals("KJ") && code_2.equals("KTM")){ indice = "KJKT";}
        else if(code_1.equals("KTM") && code_2.equals("AG")){ indice = "KTAG";}
        else if(code_1.equals("AG") && code_2.equals("KTM")){ indice = "AGKT";}
        else if(code_1.equals("KTM") && code_2.equals("ML")){ indice = "KTML";}
        else if(code_1.equals("ML") && code_2.equals("KTM")){ indice = "AGAG";}

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

        if(start_index > 134){

            ktm_track = "D";

        }else if(start_index > 124 && start_index < 134){

            ktm_track = "C";

        }else if(start_index > 101 && start_index < 123){

            ktm_track = "B";

        }else if(start_index < 101){

            ktm_track = "A";
        }

        if(end_index > 134){

            ktm_track_end = "D";

        }else if(end_index > 124 && start_index < 134){

            ktm_track_end = "C";

        }else if(end_index > 101 && start_index < 123){

            ktm_track_end = "B";

        }else if(end_index < 101){

            ktm_track_end = "A";
        }

        ArrayList<StationList> stationlists = new ArrayList<>();

        fareBuilder = new FareBuilder("","", this);

        if(indice.equals("KJKJ")){

            //same track
            if(start_index > end_index){
                for(int j = start_index; j >= end_index; j--){
                    stationlists.add(new StationList(station_name_list[j],j));
                }
            }else{
                for(int j = start_index; j <= end_index; j++){
                    stationlists.add(new StationList(station_name_list[j],j));
                }
            }

            from_val = "Stesen%25LRT%25" + from_val.replaceAll(" ", "%25");
            to_val = "Stesen%25LRT%25" + to_val.replaceAll(" ", "%25");

            fareBuilder = new FareBuilder(from_val, to_val, this);

        }else if(indice.equals("AGAG")){

            if(start_index > end_index){
                for(int j = start_index; j >= end_index; j--){
                    stationlists.add(new StationList(station_name_list[j],j));
                }
            }else{
                for(int j = start_index; j <= end_index; j++){
                    stationlists.add(new StationList(station_name_list[j],j));
                }
            }

            from_val = "Stesen%25LRT%25" + from_val.replaceAll(" ", "%25");
            to_val = "Stesen%25LRT%25" + to_val.replaceAll(" ", "%25");

            fareBuilder = new FareBuilder(from_val, to_val, this);

        }else if(indice.equals("MLML")){

            if(start_index > end_index){
                for(int j = start_index; j >= end_index; j--){
                    stationlists.add(new StationList(station_name_list[j],j));
                }
            }else{
                for(int j = start_index; j <= end_index; j++){
                    stationlists.add(new StationList(station_name_list[j],j));
                }
            }

            from_val = "Monorail%25Station%25" + from_val.replaceAll(" ", "%25");
            to_val = "Monorail%25Station%25" + to_val.replaceAll(" ", "%25");

            fareBuilder = new FareBuilder(from_val, to_val, this);


        }else if(indice.equals("KJAG")){

            message_text.setText("Stop at Masjid Jamek and take Ampang Line to continue your journey");

            if(start_index > kj_interchange){

                for(int j = start_index; j >= kj_interchange; j--){
                    stationlists.add(new StationList(station_name_list[j],j));
                }

            }else{

                for(int j = start_index; j <= kj_interchange; j++){
                    stationlists.add(new StationList(station_name_list[j],j));
                }

            }

            if(end_index > ag_interchange){
                for(int h = ag_interchange; h <= end_index; h++){
                    stationlists.add(new StationList(station_name_list[h],h));
                }
            }else{
                for(int h = ag_interchange; h >= end_index; h--){
                    stationlists.add(new StationList(station_name_list[h],h));
                }
            }

            from_val = "Stesen%25LRT%25" + from_val.replaceAll(" ", "%25");
            to_val = "Stesen%25LRT%25" + to_val.replaceAll(" ", "%25");

            fareBuilder = new FareBuilder(from_val, to_val, this);


        }else if(indice.equals("AGKJ")){

            message_text.setText("Stop at Masjid Jamek and take Kelana jaya Line to continue your journey");

            if(start_index < ag_interchange){

                for(int j = start_index; j <= ag_interchange; j++){
                    stationlists.add(new StationList(station_name_list[j],j));
                }
                if(end_index > kj_interchange){
                    for(int h = kj_interchange; h <= end_index; h++){
                        stationlists.add(new StationList(station_name_list[h],h));
                    }
                }else{
                    for(int h = kj_interchange; h >= end_index; h--){
                        stationlists.add(new StationList(station_name_list[h],h));
                    }
                }

            }else{

                for(int j = start_index; j >= ag_interchange; j--){
                    stationlists.add(new StationList(station_name_list[j],j));
                }
                if(end_index > kj_interchange){
                    for(int h = kj_interchange; h <= end_index; h++){
                        stationlists.add(new StationList(station_name_list[h],h));
                    }
                }else{
                    for(int h = kj_interchange; h >= end_index; h--){
                        stationlists.add(new StationList(station_name_list[h],h));
                    }
                }
            }

            from_val = "Stesen%25LRT%25" + from_val.replaceAll(" ", "%25");
            to_val = "Stesen%25LRT%25" + to_val.replaceAll(" ", "%25");

            fareBuilder = new FareBuilder(from_val, to_val, this);

        }else if(indice.equals("MLKJ")){

            message_text.setText("Stop at KL Sentral and take LRT Kelana jaya Line to continue your journey");

            if (start_index > ml_interchange) {

                for (int j = start_index; j >= ml_interchange; j--) {
                    //from start node to interchange point
                    stationlists.add(new StationList(station_name_list[j],j));
                }
            }else{

                for(int j = start_index; j >= ml_interchange_2; j--){
                    stationlists.add(new StationList(station_name_list[j],j));
                }

            }

            if(end_index > kj_interchange_2){

                for(int h = kj_interchange_2;h <= end_index;h++){
                    stationlists.add(new StationList(station_name_list[h],h));
                }

            }else{

                for(int h = kj_interchange_3;h >= end_index;h--){
                    stationlists.add(new StationList(station_name_list[h],h));
                }

            }

            from_val = "Monorail%25Station%25" + from_val.replaceAll(" ", "%25");
            to_val = "Stesen%25LRT%25" + to_val.replaceAll(" ", "%25");

            fareBuilder = new FareBuilder(from_val, to_val, this);

        }else if(indice.equals("KJML")){

            message_text.setText("Stop at Dang Wangi and take Bukit Nenas Monorail to continue your journey");

            if(start_index < kj_interchange_2){

                for(int j = start_index;j <= kj_interchange_2;j++){
                    stationlists.add(new StationList(station_name_list[j],j));
                }

            }else{

                for(int j = start_index;j >= kj_interchange_2;j--){
                    stationlists.add(new StationList(station_name_list[j],j));
                }
            }

            if(end_index > ml_interchange){

                for(int h = ml_interchange;h <= end_index;h++){
                    stationlists.add(new StationList(station_name_list[h],h));
                }

            }else{

                for(int h = ml_interchange;h >= end_index;h--){
                    stationlists.add(new StationList(station_name_list[h],h));
                }

            }

            from_val = "Stesen%25LRT%25" + from_val.replaceAll(" ", "%25");
            to_val = "Monorail%25Station%25" + to_val.replaceAll(" ", "%25");

            fareBuilder = new FareBuilder(from_val, to_val, this);

        }else if(indice.equals("AGML")){

            if(start_index < ag_interchange){
                for(int k = start_index;k <= ag_interchange;k++){
                    stationlists.add(new StationList(station_name_list[k],k));
                }
            }else{

                for(int k = start_index;k >= ag_interchange;k--){
                    stationlists.add(new StationList(station_name_list[k],k));
                }
            }

            for(int h = kj_interchange;h <= kj_interchange_3;h++){
                stationlists.add(new StationList(station_name_list[h],h));
            }

            for(int i = ml_interchange_2;i <= end_index;i++){
                stationlists.add(new StationList(station_name_list[i],i));
            }

            from_val = "Stesen%25LRT%25" + from_val.replaceAll(" ", "%25");
            to_val = "Monorail%25Station%25" + to_val.replaceAll(" ", "%25");

            fareBuilder = new FareBuilder(from_val, to_val, this);

        }else if(indice.equals("MLAG")){

            for(int j = start_index;j >= ml_interchange_2;j--){

                stationlists.add(new StationList(station_name_list[j],j));
            }

            for(int h = kj_interchange_3;h >= kj_interchange;h--){
                stationlists.add(new StationList(station_name_list[h],h));
            }

            if(end_index < ag_interchange){
                for(int k = ag_interchange;k >= end_index;k--){
                    stationlists.add(new StationList(station_name_list[k],k));
                }
            }else{

                for(int k = ag_interchange;k <= end_index;k++){
                    stationlists.add(new StationList(station_name_list[k],k));
                }
            }

            to_val = "Stesen%25LRT%25" + to_val.replaceAll(" ", "%25");
            from_val = "Monorail%25Station%25" + from_val.replaceAll(" ", "%25");

            fareBuilder = new FareBuilder(from_val, to_val, this);

        }else if(indice.equals("MR")){

            if(start_index > end_index){

                for(int j = start_index;j >= end_index;j--){
                    stationlists.add(new StationList(station_name_list[j],j));
                }

            }else{

                for(int j = start_index;j <= end_index;j++){
                    stationlists.add(new StationList(station_name_list[j],j));
                }

            }

            from_val = "MRT%25" + from_val.replaceAll(" ", "%25");
            to_val = "MRT%25" + to_val.replaceAll(" ", "%25");

            fareBuilder = new FareBuilder(from_val, to_val, this);

        }else if(indice.equals("KTKT")){

            if(ktm_track.equals(ktm_track_end)){

                if(start_index > end_index){

                    for(int j = start_index;j >= end_index;j--){
                        stationlists.add(new StationList(station_name_list[j],j));
                    }

                }else{

                    for(int j = start_index;j <= end_index;j++){
                        stationlists.add(new StationList(station_name_list[j],j));
                    }
                }

            }else if(ktm_track.equals("A") && ktm_track_end.equals("B")){

                for(int j = start_index;j <= end_index;j++){
                    stationlists.add(new StationList(station_name_list[j],j));
                }

            }else if(ktm_track.equals("A") && ktm_track_end.equals("C")){

                //98 is Putra station
                for(int j = start_index; j <= 98; j++){
                    stationlists.add(new StationList(station_name_list[j],j));
                }

                for(int h = 131;h >= end_index;h--){
                    stationlists.add(new StationList(station_name_list[h],h));
                }

            }else if(ktm_track.equals("A") && ktm_track_end.equals("D")){

                //101 is KL Sentral
                for(int j = start_index; j <= 101; j++){
                    stationlists.add(new StationList(station_name_list[j],j));
                }

                for(int h = 134;h <= end_index;h++){
                    stationlists.add(new StationList(station_name_list[h],h));
                }

            }else if(ktm_track.equals("B") && ktm_track_end.equals("A")){

                for(int j = start_index;j >= end_index;j--){
                    stationlists.add(new StationList(station_name_list[j],j));
                }

            }else if(ktm_track.equals("B") && ktm_track_end.equals("C")){

                //101 is KL Sentral
                for(int j = start_index;j >= 101; j--){
                    stationlists.add(new StationList(station_name_list[j],j));
                }

                for(int h = 134;h >= end_index;h--){
                    stationlists.add(new StationList(station_name_list[h],h));
                }

            }else if(ktm_track.equals("B") && ktm_track_end.equals("D")){

                //101 is KL Sentral
                for(int j = start_index;j >= 101; j--){
                    stationlists.add(new StationList(station_name_list[j],j));
                }

                for(int h = 134;h <= end_index;h++){
                    stationlists.add(new StationList(station_name_list[h],h));
                }

            }else if(ktm_track.equals("C") && ktm_track_end.equals("A")){

                //98 is Putra station
                for(int j = start_index; j <= 131; j++){
                    stationlists.add(new StationList(station_name_list[j],j));
                }

                for(int h = 98;h >= end_index;h--){
                    stationlists.add(new StationList(station_name_list[h],h));
                }

            }else if(ktm_track.equals("C") && ktm_track_end.equals("B")){

                //101 is KL Sentral
                for(int j = start_index; j <= 134; j++){
                    stationlists.add(new StationList(station_name_list[j],j));
                }

                for(int h = 101;h <= end_index;h++){
                    stationlists.add(new StationList(station_name_list[h],h));
                }

            }else if(ktm_track.equals("C") && ktm_track_end.equals("D")){

                for(int j = start_index;j >= end_index;j++){
                    stationlists.add(new StationList(station_name_list[j],j));
                }

            }else if(ktm_track.equals("D") && ktm_track_end.equals("A")){

                //101 is KL Sentral
                for(int j = start_index;j >= 134; j--){
                    stationlists.add(new StationList(station_name_list[j],j));
                }

                for(int h = 101;h >= end_index;h--){
                    stationlists.add(new StationList(station_name_list[h],h));
                }

            }else if(ktm_track.equals("D") && ktm_track_end.equals("B")){

                //101 is KL Sentral
                for(int j = start_index;j >= 134; j--){
                    stationlists.add(new StationList(station_name_list[j],j));
                }

                for(int h = 101;h <= end_index;h++){
                    stationlists.add(new StationList(station_name_list[h],h));
                }

            }else if(ktm_track.equals("D") && ktm_track_end.equals("C")){

                for(int j = start_index;j >= end_index;j--){
                    stationlists.add(new StationList(station_name_list[j],j));
                }

            }


            from_val = "Stesen%25Komuter%25" + from_val.replaceAll(" ", "%25");
            to_val = "Stesen%25Komuter%25" + to_val.replaceAll(" ", "%25");

            fareBuilder = new FareBuilder(from_val, to_val, this);

        }else if(indice.equals("KTKJ")){

            if(ktm_track.equals("A")){

                message_text.setText("We couldn't calculate the fare for KTM. " +
                        "               Displayed cost is for LRT fares only");

                for(int j = start_index;j <= 101;j++){
                    stationlists.add(new StationList(station_name_list[j],j));
                }

            }else if(ktm_track.equals("B")){

                message_text.setText("We couldn't calculate the fare for KTM. " +
                        "               Displayed cost is for LRT fares only");

                for(int j = start_index;j >= 101;j--){
                    stationlists.add(new StationList(station_name_list[j],j));
                }

            }else if(ktm_track.equals("C")){

                message_text.setText("We couldn't calculate the fare for KTM. " +
                        "               Displayed cost is for LRT fares only");

                for(int j = start_index;j <= 134;j++){
                    stationlists.add(new StationList(station_name_list[j],j));
                }

            }else if(ktm_track.equals("D")){

                message_text.setText("We couldn't calculate the fare for KTM. " +
                        "               Displayed cost is for LRT fares only");

                for(int j = start_index;j >= 134;j--){
                    stationlists.add(new StationList(station_name_list[j],j));
                }
            }

            from_val = "KL Sentral";

            if(end_index > kj_interchange_3){
                for(int h = kj_interchange_3;h <= end_index;h++){
                    stationlists.add(new StationList(station_name_list[h],h));
                }
            }else{
                for(int h = kj_interchange_3;h >= end_index;h--) {
                    stationlists.add(new StationList(station_name_list[h],h));
                }
            }

            from_val = "LRT%25" + from_val.replaceAll(" ", "%25");
            to_val = "LRT%25" + to_val.replaceAll(" ", "%25");

            fareBuilder = new FareBuilder(from_val, to_val, this);

        }else if(indice.equals("KJKT")){

            to_val = "KL Sentral";

            if(start_index > kj_interchange_3){
                for(int h = start_index;h > kj_interchange_3;h--){
                    stationlists.add(new StationList(station_name_list[h],h));
                }
            }else {
                for (int h = start_index; h < kj_interchange_3;h++) {
                    stationlists.add(new StationList(station_name_list[h],h));
                }
            }

            from_val = "LRT%25" + from_val.replaceAll(" ", "%25");
            to_val = "LRT%25" + to_val.replaceAll(" ", "%25");

            fareBuilder = new FareBuilder(from_val, to_val, this);

            Log.d("TRIP",ktm_track_end);

            Log.d("TRIP",Integer.toString(end_index));

            if(ktm_track_end.equals("A")){

                message_text.setText("We couldn't calculate the fare for KTM. " +
                        "Displayed cost is for LRT fares only");

                if(end_index == 131){end_index = 98;}
                else if(end_index == 132){end_index = 99;}
                else if(end_index == 133){end_index = 100;}
                else if(end_index == 134){end_index = 101;}

                for(int j = 101;j >= end_index;j--){
                    stationlists.add(new StationList(station_name_list[j],j));
                }

            }else if(ktm_track_end.equals("B")){

                message_text.setText("We couldn't calculate the fare for KTM. " +
                        "Displayed cost is for LRT fares only");


                for(int j = 101;j <= end_index;j++){
                    stationlists.add(new StationList(station_name_list[j],j));
                }

            }else if(ktm_track_end.equals("C")){

                message_text.setText("We couldn't calculate the fare for KTM. " +
                        "               Displayed cost is for LRT fares only");

                for(int j = 134;j >= end_index;j--){
                    stationlists.add(new StationList(station_name_list[j],j));
                }

            }else if(ktm_track_end.equals("D")){

                message_text.setText("We couldn't calculate the fare for KTM. " +
                        "               Displayed cost is for LRT fares only");

                for(int j = 134;j <= end_index;j++){
                    stationlists.add(new StationList(station_name_list[j],j));
                }
            }

        }else if(indice.equals("KTAG")){

            if(ktm_track.equals("A")){

                message_text.setText("We couldn't calculate the fare for KTM. " +
                        "Displayed cost is for LRT fares only");

                for(int j = start_index;j <= 101;j++){
                    stationlists.add(new StationList(station_name_list[j],j));
                }

            }else if(ktm_track.equals("B")){

                message_text.setText("We couldn't calculate the fare for KTM. " +
                        "Displayed cost is for LRT fares only");

                for(int j = start_index;j >= 101;j--){
                    stationlists.add(new StationList(station_name_list[j],j));
                }

            }else if(ktm_track.equals("C")){

                message_text.setText("We couldn't calculate the fare for KTM. " +
                        "Displayed cost is for LRT fares only");

                for(int j = start_index;j <= 134;j++){
                    stationlists.add(new StationList(station_name_list[j],j));
                }

            }else if(ktm_track.equals("D")) {

                message_text.setText("We couldn't calculate the fare for KTM. " +
                        "Displayed cost is for LRT fares only");

                for (int j = start_index; j >= 134; j--) {
                    stationlists.add(new StationList(station_name_list[j],j));
                }
            }

            for(int h = kj_interchange_3;h >= kj_interchange;h--){
                stationlists.add(new StationList(station_name_list[h],h));
            }

            if(end_index > ag_interchange){

                for(int k = ag_interchange;k <= end_index;k++){
                    stationlists.add(new StationList(station_name_list[k],k));
                }

            }else{
                for(int k = ag_interchange;k >= end_index;k--){
                    stationlists.add(new StationList(station_name_list[k],k));
                }
            }

            from_val = "KL Sentral";

            from_val = "LRT%25" + from_val.replaceAll(" ", "%25");
            to_val = "LRT%25" + to_val.replaceAll(" ", "%25");

            fareBuilder = new FareBuilder(from_val, to_val, this);

        }else if(indice.equals("AGKT")){


            if(start_index > ag_interchange){
                for(int j = start_index;j >= ag_interchange;j--){
                    stationlists.add(new StationList(station_name_list[j],j));
                }
            }else{
                for(int j = start_index;j <= ag_interchange;j++){
                    stationlists.add(new StationList(station_name_list[j],j));
                }
            }

            for(int k = kj_interchange;k <= kj_interchange_3;k++){
                stationlists.add(new StationList(station_name_list[k],k));
            }

            if(ktm_track_end.equals("A")){

                message_text.setText("We couldn't calculate the fare for KTM. " +
                        "Displayed cost is for LRT fares only");

                if(end_index == 131){end_index = 98;}
                else if(end_index == 132){end_index = 99;}
                else if(end_index == 133){end_index = 100;}
                else if(end_index == 134){end_index = 101;}

                for(int j = 101;j >= end_index;j--){
                    stationlists.add(new StationList(station_name_list[j],j));
                }

            }else if(ktm_track_end.equals("B")){

                message_text.setText("We couldn't calculate the fare for KTM. " +
                        "Displayed cost is for LRT fares only");


                for(int j = 101;j <= end_index;j++){
                    stationlists.add(new StationList(station_name_list[j],j));
                }

            }else if(ktm_track_end.equals("C")){

                message_text.setText("We couldn't calculate the fare for KTM. " +
                        "               Displayed cost is for LRT fares only");

                for(int j = 134;j >= end_index;j--){
                    stationlists.add(new StationList(station_name_list[j],j));
                }

            }else if(ktm_track_end.equals("D")){

                message_text.setText("We couldn't calculate the fare for KTM. " +
                        "               Displayed cost is for LRT fares only");

                for(int j = 134;j <= end_index;j++){
                    stationlists.add(new StationList(station_name_list[j],j));
                }
            }

            to_val = "KL Sentral";

            from_val = "LRT%25" + from_val.replaceAll(" ", "%25");
            to_val = "LRT%25" + to_val.replaceAll(" ", "%25");

            fareBuilder = new FareBuilder(from_val, to_val, this);

        }else if(indice.equals("KTML")){

            if(ktm_track.equals("A")){

                message_text.setText("We couldn't calculate the fare for KTM. " +
                        "               Displayed cost is for LRT fares only");

                for(int j = start_index;j <= 101;j++){
                    stationlists.add(new StationList(station_name_list[j],j));
                }

            }else if(ktm_track.equals("B")){

                message_text.setText("We couldn't calculate the fare for KTM. " +
                        "               Displayed cost is for LRT fares only");

                for(int j = start_index;j >= 101;j--){
                    stationlists.add(new StationList(station_name_list[j],j));
                }

            }else if(ktm_track.equals("C")){

                message_text.setText("We couldn't calculate the fare for KTM. " +
                        "               Displayed cost is for LRT fares only");

                for(int j = start_index;j <= 134;j++){
                    stationlists.add(new StationList(station_name_list[j],j));
                }

            }else if(ktm_track.equals("D")){

                message_text.setText("We couldn't calculate the fare for KTM. " +
                        "               Displayed cost is for LRT fares only");

                for(int j = start_index;j >= 134;j--){
                    stationlists.add(new StationList(station_name_list[j],j));
                }
            }

            from_val = "Kl Sentral";

            for(int h = 71;h <= end_index;h++){
                stationlists.add(new StationList(station_name_list[h],h));
            }

            from_val = "Monorail%25Station%25" + from_val.replaceAll(" ", "%25");
            to_val = "Monorail%25Station%25" + to_val.replaceAll(" ", "%25");

            fareBuilder = new FareBuilder(from_val, to_val, this);


        }else if(indice.equals("MLKT")){

            to_val = "Kl Sentral";

            for(int h = start_index;h >= 71;h--){
                stationlists.add(new StationList(station_name_list[h],h));
            }

            from_val = "Monorail%25Station%25" + from_val.replaceAll(" ", "%25");
            to_val = "Monorail%25Station%25" + to_val.replaceAll(" ", "%25");

            fareBuilder = new FareBuilder(from_val, to_val, this);

            if(ktm_track_end.equals("A")){

                message_text.setText("We couldn't calculate the fare for KTM. " +
                        "               Displayed cost is for LRT fares only");

                for(int j = start_index;j <= 101;j++){
                    stationlists.add(new StationList(station_name_list[j],j));
                }

            }else if(ktm_track_end.equals("B")){

                message_text.setText("We couldn't calculate the fare for KTM. " +
                        "               Displayed cost is for LRT fares only");

                for(int j = start_index;j >= 101;j--){
                    stationlists.add(new StationList(station_name_list[j],j));
                }

            }else if(ktm_track_end.equals("C")){

                message_text.setText("We couldn't calculate the fare for KTM. " +
                        "               Displayed cost is for LRT fares only");

                for(int j = start_index;j <= 134;j++){
                    stationlists.add(new StationList(station_name_list[j],j));
                }

            }else if(ktm_track_end.equals("D")){

                message_text.setText("We couldn't calculate the fare for KTM. " +
                        "               Displayed cost is for LRT fares only");

                for(int j = start_index;j >= 134;j--){
                    stationlists.add(new StationList(station_name_list[j],j));
                }
            }

        }

        final String temp = indice;

        StationListAdapter adapter = new StationListAdapter(ig_stat,ResultActivity.this, stationlists);

        stationlistvew.setAdapter(adapter);

        fareBuilder.fareGrab(new FareBuilder.Callback() {
            @Override
            public void onSuccess(String result) {


                if(temp.equals("KTKT")){

                    try{

                        JSONObject jsonObject = new JSONObject(result);

                        String routes = jsonObject.getJSONArray("routes").get(0).toString();

                        JSONObject jsonObject1 = new JSONObject(routes);

                        String legs = jsonObject1.getJSONArray("legs").get(0).toString();

                        JSONObject jsonObject2 = new JSONObject(legs);


                        from_btn.setText(from);
                        to_btn.setText(to);
                        price_text.setText("");

                        //set arrival time======================================
                        String arrival = jsonObject2.getString("arrival_time");

                        JSONObject jsonObject3 = new JSONObject(arrival);

                        String arrival_value = jsonObject3.getString("text");

                        arrival_text.setText(arrival_value);

                        //set departure time====================================

                        String departure = jsonObject2.getString("departure_time");

                        JSONObject jsonObject4 = new JSONObject(departure);

                        String departure_value = jsonObject4.getString("text");

                        departure_text.setText(departure_value);

                        //set eta ===============================================

                        String eta = jsonObject2.getString("duration");

                        JSONObject jsonObject5 = new JSONObject(eta);

                        String eta_value = jsonObject5.getString("text");

                        duration_text.setText(eta_value);

                        //set distance ===========================================

                        String distance = jsonObject2.getString("distance");

                        JSONObject jsonObject6 = new JSONObject(distance);

                        String distance_value = jsonObject6.getString("text");

                        distance_text.setText(distance_value);

                    }catch(Exception e){

                    }

                }else{

                    try{

                        JSONObject jsonObject = new JSONObject(result);

                        String routes = jsonObject.getJSONArray("routes").get(0).toString();

                        JSONObject jsonObject1 = new JSONObject(routes);

                        String fare = jsonObject1.getString("fare");

                        JSONObject jsonObject2 = new JSONObject(fare);

                        String price = jsonObject2.getString("text");

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

                    }catch(Exception e){

                    }

                }

            }
        });

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        finish();

        return true;
    }
}
