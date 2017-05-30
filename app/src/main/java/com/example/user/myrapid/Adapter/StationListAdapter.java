package com.example.user.myrapid.Adapter;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.user.myrapid.Builder.StationList;
import com.example.user.myrapid.R;

import java.util.ArrayList;

/**
 * Created by USER on 29/5/2017.
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

        TextView button_fake = (TextView) convertView.findViewById(R.id.button_fake_station);
        LinearLayout palette = (LinearLayout) convertView.findViewById(R.id.station_pallete);

        String [] arr = stationList.name.split(" ", 2);

        String code = arr[0];

        Drawable draw;

        if(code.equals("KJ")){

            draw = convertView.getResources().getDrawable(R.drawable.curvy_bg_station_kj);
            palette.setBackground(draw);

        }else if(code.equals("AG")){

            draw = convertView.getResources().getDrawable(R.drawable.curvy_bg_station_ag);
            palette.setBackground(draw);

        }

        button_fake.setText(stationList.name);

        return convertView;
    }
}
