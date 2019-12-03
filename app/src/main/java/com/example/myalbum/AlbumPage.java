package com.example.myalbum;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class AlbumPage extends AppCompatActivity {

    private List<Photos> photoList = new ArrayList<>();
    private List<Photos> album_photo = new ArrayList<>();
    private MyDataBaseHelper dbHelper;
    String album_name;

    Photos p1 = new Photos("pic1",R.drawable.pic1);
    Photos p2 = new Photos("pic2",R.drawable.pic2);
    Photos p3 = new Photos("pic3",R.drawable.pic3);
    Photos p4 = new Photos("pic4",R.drawable.pic4);
    Photos p5 = new Photos("pic5",R.drawable.pic5);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_album_page);

        dbHelper =new MyDataBaseHelper(this,"MyAlbum.db",null ,1);

        Intent intent= getIntent();
        album_name=intent.getStringExtra("album_name");
        TextView textView = (TextView) findViewById(R.id.textView);
        textView.setText("目前所在位置："+album_name);


        initAlbum_Photo();
        final PhotoAdapter adapter1 = new PhotoAdapter(AlbumPage.this,R.layout.photo_item,album_photo);
        ListView photoofalbum = (ListView) findViewById(R.id.photoofalbum);
        photoofalbum.setAdapter(adapter1);

        photoofalbum.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Photos photo = album_photo.get(i);
                Intent intent = new Intent(AlbumPage.this,Picture.class);
                intent.putExtra("photo_name",photo.getName());
                intent.putExtra("photo_resId",String.valueOf( photo.getImageId()));
                startActivity(intent);
            }
        });


        photoofalbum.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {

                final Photos photo=album_photo.get(i);

                AlertDialog.Builder dialog= new AlertDialog.Builder(AlbumPage.this);
                dialog.setTitle("删除提醒！");
                dialog.setMessage("确定将所选图片从本相册删除？");
                dialog.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        SQLiteDatabase db = dbHelper.getWritableDatabase();
                        int resId=photo.getImageId();

                        Cursor cursor=db.rawQuery("Select * from albums where AlbumName = ?", new String[]{album_name});
                        cursor.moveToFirst();
                        String Path=cursor.getString(cursor.getColumnIndex("Path"));
                        Path=Path.replaceAll("@"+String.valueOf(resId),"");

                        ContentValues values=new ContentValues();
                        values.put("Path",Path);
                        db.update("albums",values,"AlbumName = ?",new String[]{album_name});


                        Toast.makeText(AlbumPage.this,"删除成功",Toast.LENGTH_SHORT).show();

                        try{
                            album_photo.clear();
                            initAlbum_Photo();
                            ListView photoofalbum =(ListView) findViewById(R.id.photoofalbum);
                            photoofalbum.setAdapter(adapter1);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                    }
                });
                dialog.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                    }
                });
                dialog.show();
                return true;
            }
        });






        initPhotos();
        final PhotoAdapter adapter = new PhotoAdapter(AlbumPage.this,R.layout.photo_item,photoList);
        ListView listofphoto = (ListView) findViewById(R.id.listofphoto);
        listofphoto.setAdapter(adapter);

        listofphoto.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {

                final Photos photo=photoList.get(i);

                AlertDialog.Builder dialog= new AlertDialog.Builder(AlbumPage.this);
                dialog.setTitle("添加提醒！");
                dialog.setMessage("确定将所选图片添加至相册？");
                dialog.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        SQLiteDatabase db = dbHelper.getWritableDatabase();

                        Cursor cursor=db.rawQuery("Select * from albums where AlbumName = ?", new String[]{album_name});
                        cursor.moveToFirst();
                        String Path=cursor.getString(cursor.getColumnIndex("Path"));

                        String newPath = Path+"@"+photo.getImageId();
                        ContentValues values=new ContentValues();
                        values.put("Path",newPath);
                        db.update("albums",values,"AlbumName = ?",new String[]{album_name});
                        Toast.makeText(AlbumPage.this,"添加成功",Toast.LENGTH_SHORT).show();

                        try{
                            album_photo.clear();
                            initAlbum_Photo();
                            ListView photoofalbum =(ListView) findViewById(R.id.photoofalbum);
                            photoofalbum.setAdapter(adapter1);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                    }
                });
                dialog.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                    }
                });
                dialog.show();
                return true;
            }
        });



    }

    private void initPhotos(){

        photoList.add(p1);
        photoList.add(p2);
        photoList.add(p3);
        photoList.add(p4);
        photoList.add(p5);
    }

    private void initAlbum_Photo(){
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        try{

            Cursor cursor=db.rawQuery("Select * from albums where AlbumName = ?", new String[]{album_name});
            cursor.moveToFirst();
            String Path=cursor.getString(cursor.getColumnIndex("Path"));

            String a[] = Path.split("@");
            for(int i=1;i<a.length;i++){
                int m = Integer.parseInt( a[i] );
                if(p1.getImageId()==m){
                    album_photo.add(p1);
                }
                if(p2.getImageId()==m){
                    album_photo.add(p2);
                }
                if(p3.getImageId()==m){
                    album_photo.add(p3);
                }
                if(p4.getImageId()==m){
                    album_photo.add(p4);
                }
                if(p5.getImageId()==m){
                    album_photo.add(p5);
                }
            }
            cursor.close();
        } catch (Exception e) {
            e.printStackTrace();

        }
    }


}
