package com.example.user.lookup.Adapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.user.lookup.Builder.InteractionList;
import com.example.user.lookup.R;
import com.example.user.lookup.ViewPlaceActivity;

import java.util.ArrayList;

import it.sephiroth.android.library.picasso.Picasso;

/**
 * Created by USER on 31/5/2017.
 */

public class InteractionListAdapter  extends ArrayAdapter<InteractionList> {

    public InteractionListAdapter(Context context, ArrayList<InteractionList> pm) {

        super(context,0,pm);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if (convertView == null) {

            convertView = LayoutInflater.from(getContext()).inflate(R.layout.list_item_interaction, parent, false);

        }
        LinearLayout layout_list = (LinearLayout) convertView.findViewById(R.id.list_interaction);

        InteractionList order = getItem(position);

        TextView place_name = (TextView) convertView.findViewById(R.id.place_name);
        TextView place_address = (TextView) convertView.findViewById(R.id.place_address);
        TextView place_rating = (TextView) convertView.findViewById(R.id.place_rating);
        ImageView place_icon = (ImageView) convertView.findViewById(R.id.place_icon);

        place_name.setText(order.name);
        place_address.setText(order.address);
        place_rating.setText(order.rating);
        Picasso.with(getContext()).load(order.icon).into(place_icon);

        final String lat = order.lat;
        final String lng = order.lng;
        final String photo_url = order.photo_url;

        layout_list.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

//                Intent intent = new Intent(android.content.Intent.ACTION_VIEW,
//                        Uri.parse("http://maps.google.com/maps?daddr="+lat+","+lng));
//
//                getContext().startActivity(intent);

                Intent intent = new Intent(getContext(), ViewPlaceActivity.class);

                intent.putExtra("LAT",lat);
                intent.putExtra("LNG",lng);
                intent.putExtra("WEBURL",photo_url);

                getContext().startActivity(intent);

            }
        });


        return convertView;
    }
}
