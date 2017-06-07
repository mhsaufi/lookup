package com.example.user.lookup.Adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.user.lookup.Builder.StationList;
import com.example.user.lookup.InteractionActivity;
import com.example.user.lookup.R;

import java.util.ArrayList;

/**
 * Created by HABIB on 29/5/2017.
 */

public class StationListAdapter  extends ArrayAdapter<StationList> {

    Boolean ig_stat;

    public StationListAdapter(Boolean ig_stat,Context context, ArrayList<StationList> pm) {

        super(context,0,pm);
        this.ig_stat = ig_stat;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if (convertView == null) {

            convertView = LayoutInflater.from(getContext()).inflate(R.layout.list_item_station, parent, false);

        }

        StationList stationList = getItem(position);

        String[] latlng_bank = getContext().getResources().getStringArray(R.array.lrt_latlng);

        String latlng = latlng_bank[position];

        final String main_point = latlng.replaceAll(" ", "");

        final TextView button_fake = (TextView) convertView.findViewById(R.id.button_fake_station);
        ImageView company_logo = (ImageView) convertView.findViewById(R.id.company_logo);
        LinearLayout palette = (LinearLayout) convertView.findViewById(R.id.station_pallete);

        final String [] arr = stationList.name.split(" ", 2);

        String code = arr[0];

        Drawable draw;

        if(code.equals("KJ")){

            draw = convertView.getResources().getDrawable(R.drawable.curvy_bg_station_kj);
            palette.setBackground(draw);
            company_logo.setImageResource(R.drawable.logorapidkl);

        }else if(code.equals("AG")){

            draw = convertView.getResources().getDrawable(R.drawable.curvy_bg_station_ag);
            palette.setBackground(draw);
            company_logo.setImageResource(R.drawable.logorapidkl);

        }else if(code.equals("ML")){

            draw = convertView.getResources().getDrawable(R.drawable.curvy_bg_station_ml);
            palette.setBackground(draw);
            company_logo.setImageResource(R.drawable.logorapidkl);

        }else if(code.equals("MR")){

            draw = convertView.getResources().getDrawable(R.drawable.curvy_bg_station_mr);
            palette.setBackground(draw);
            company_logo.setImageResource(R.drawable.mrtlogo);

        }else if(code.equals("KTM")){

            draw = convertView.getResources().getDrawable(R.drawable.curvy_bg_station_ktm);
            palette.setBackground(draw);
            company_logo.setImageResource(R.drawable.ktmb);

        }

        button_fake.setText(stationList.name);

        palette.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent interaction = new Intent(getContext(), InteractionActivity.class);

                interaction.putExtra("INTERACTIONPOINT",main_point);
                interaction.putExtra("INTERACTIONCENTER",arr[1]);

                getContext().startActivity(interaction);

            }
        });

        return convertView;
    }
}
