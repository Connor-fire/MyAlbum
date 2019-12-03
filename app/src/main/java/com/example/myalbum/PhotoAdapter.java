package com.example.myalbum;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.List;

public class PhotoAdapter extends ArrayAdapter<Photos> {
    private int resourceId;

    public PhotoAdapter( Context context, int resource,  List<Photos> objects) {
        super(context, resource, objects);
        resourceId=resource;
    }

    @Override
    public View getView(int position,  View convertView,  ViewGroup parent) {
        Photos photo = getItem(position);
        View view = LayoutInflater.from(getContext()).inflate(resourceId,parent,false);
        ImageView photoImage = (ImageView) view.findViewById(R.id.photo_image);
        TextView photoName = (TextView) view.findViewById(R.id.photo_name);
        photoImage.setImageResource(photo.getImageId());
        photoName.setText(photo.getName());
        return view;
    }
}
