package com.aniharu.alertapet;

import android.support.v4.app.Fragment;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.Marker;
import com.squareup.picasso.Picasso;


class CustomInfoWindow implements GoogleMap.InfoWindowAdapter {

    public Fragment context;

    public CustomInfoWindow(Fragment context){
        this.context = context;
    }

    @Override
    public View getInfoWindow(Marker marker) {
        return null;
    }

    @Override
    public View getInfoContents(Marker marker) {
        View v = context.getActivity().getLayoutInflater().inflate(R.layout.custom_info_window, null);

        ImageView mImage = (ImageView) v.findViewById(R.id.window_picture);
        TextView mInfo = v.findViewById(R.id.window_info);

        mInfo.setText(marker.getSnippet());


        if (!marker.getTitle().equalsIgnoreCase(""))
        Picasso.with(v.getContext())
                .load(marker.getTitle())
                .placeholder(R.drawable.placeholder)
                .error(R.mipmap.ic_launcher)
                .into(mImage);

        return v;
    }

}