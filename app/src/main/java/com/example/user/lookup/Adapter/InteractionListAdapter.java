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

import com.example.user.lookup.Builder.CircleTransform;
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

        InteractionList interaction = getItem(position);

        TextView place_name = (TextView) convertView.findViewById(R.id.place_name);
        TextView place_address = (TextView) convertView.findViewById(R.id.place_address);
        TextView place_rating = (TextView) convertView.findViewById(R.id.place_rating);
        TextView indicator = (TextView) convertView.findViewById(R.id.place_indicator);
        ImageView place_icon = (ImageView) convertView.findViewById(R.id.place_icon);

        place_name.setText(interaction.name);
        place_address.setText(interaction.address);
        place_rating.setText(interaction.rating);

        //Picasso class to make the image circle shaped
        Picasso.with(getContext()).
                load(interaction.icon).
                transform(new CircleTransform()).
                into(place_icon);

        final String name = interaction.name;
        final String address = interaction.address;
        final String rating = interaction.rating;
        final String lat = interaction.lat;
        final String lng = interaction.lng;
        final String photo_url = interaction.photo_url;
        final Boolean open = interaction.open;
        final String status = interaction.status;
        final String phone = interaction.phone;
        final String comment = interaction.comment;

        if(open == true){

            indicator.setText("OPEN");
            indicator.setBackgroundColor(getContext().getResources().getColor(android.R.color.holo_green_dark));

        }else{

            indicator.setText("CLOSE");
            indicator.setBackgroundColor(getContext().getResources().getColor(android.R.color.holo_red_dark));

        }

        layout_list.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(getContext(), ViewPlaceActivity.class);

                intent.putExtra("LAT",lat);
                intent.putExtra("LNG",lng);
                intent.putExtra("NAME",name);
                intent.putExtra("ADDRESS",address);
                intent.putExtra("RATING",rating);
                intent.putExtra("STATUS", status);
                intent.putExtra("WEBURL",photo_url);
                intent.putExtra("PHONE",phone);
                intent.putExtra("COMMENT",comment);

                if(open == true){
                    intent.putExtra("OPEN","open");
                }else{
                    intent.putExtra("OPEN","close");
                }

                getContext().startActivity(intent);

            }
        });


        return convertView;
    }
}
