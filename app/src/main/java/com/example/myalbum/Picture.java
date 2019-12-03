package com.example.myalbum;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

public class Picture extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_picture);

        Intent intent= getIntent();
        String photo_resId=intent.getStringExtra("photo_resId");
        String photo_name=intent.getStringExtra("photo_name");

        ImageView imageView = (ImageView) findViewById(R.id.image);
        imageView.setImageResource(Integer.parseInt( photo_resId ));

        TextView image_name = (TextView) findViewById(R.id.image_name);
        image_name.setText("图片名："+photo_name);
    }
}
