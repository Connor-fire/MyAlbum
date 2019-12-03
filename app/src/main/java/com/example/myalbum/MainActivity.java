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
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private List<String> data=new ArrayList<>();
    private MyDataBaseHelper dbHelper;

    Photos p1 = new Photos("pic1",R.drawable.pic1);
    Photos p2 = new Photos("pic2",R.drawable.pic2);
    Photos p3 = new Photos("pic3",R.drawable.pic3);
    Photos p4 = new Photos("pic4",R.drawable.pic4);
    Photos p5 = new Photos("pic5",R.drawable.pic5);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        dbHelper =new MyDataBaseHelper(this,"MyAlbum.db",null ,1);


//        try{
//            Button new_db=(Button) findViewById(R.id.new_db);
//            new_db.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//                    dbHelper.getWritableDatabase();
//                }
//            });
//        } catch (Exception e) {
//            e.printStackTrace();
//        }



        initData();
        final ArrayAdapter<String> adapter=new ArrayAdapter<String>(
                MainActivity.this,android.R.layout.simple_list_item_1,data);
        ListView listofalbum =(ListView) findViewById(R.id.listofalbum);
        listofalbum.setAdapter(adapter);

        listofalbum.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                final String album_name = data.get(i);
                AlertDialog.Builder dialog= new AlertDialog.Builder(MainActivity.this);
                dialog.setTitle("提醒！");
                dialog.setMessage("确定要打开所选相册？");
                dialog.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Intent intent = new Intent(MainActivity.this,AlbumPage.class);
                        intent.putExtra("album_name",album_name);
                        startActivity(intent);
                    }
                });
                dialog.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                    }
                });
                dialog.show();
            }
        });

        listofalbum.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {

                final String album_name = data.get(i);
                AlertDialog.Builder dialog= new AlertDialog.Builder(MainActivity.this);
                dialog.setTitle("提醒！");
                dialog.setMessage("确定要删除所选相册？");
                dialog.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        SQLiteDatabase db = dbHelper.getWritableDatabase();
                        db.delete("albums","AlbumName = ?",new String[]{album_name});
                        Toast.makeText(MainActivity.this,"已删除",Toast.LENGTH_SHORT).show();

                        try{
                            data.clear();
                            initData();
                            ListView listofalbum =(ListView) findViewById(R.id.listofalbum);
                            listofalbum.setAdapter(adapter);
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

        Button new_album=(Button) findViewById(R.id.new_album);
        new_album.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText edit_album_name = (EditText) findViewById(R.id.edit_album_name);
                SQLiteDatabase db = dbHelper.getWritableDatabase();
                ContentValues values = new ContentValues();
                try{
                    values.put("AlbumName",edit_album_name.getText().toString());
                    values.put("Path","");
                    //values.put("Path",edit_chn_txt.getText().toString());
                    db.insert("albums",null,values);
                    Toast.makeText(MainActivity.this,"SUCCESS",Toast.LENGTH_SHORT).show();
                } catch (Exception e) {
                    e.printStackTrace();
                    Toast.makeText(MainActivity.this,"ERROR",Toast.LENGTH_SHORT).show();
                }
                try{
                    data.clear();
                    initData();
                    ListView listofalbum =(ListView) findViewById(R.id.listofalbum);
                    listofalbum.setAdapter(adapter);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        Button select_btn = (Button) findViewById(R.id.select_btn);
        select_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText edit_album_name = (EditText) findViewById(R.id.edit_album_name);
                SQLiteDatabase db = dbHelper.getWritableDatabase();
                final String str = edit_album_name.getText().toString();
                //Toast.makeText(MainActivity.this,str,Toast.LENGTH_SHORT).show();
                //Toast.makeText(MainActivity.this,data.get(0),Toast.LENGTH_SHORT).show();
                for(int i =0;i<data.size();i++){
                    final String s= data.get(i);
                    if(str.equals(s)){
                        AlertDialog.Builder dialog1= new AlertDialog.Builder(MainActivity.this);
                        dialog1.setTitle("提醒！");
                        dialog1.setMessage("确定要打开相册 "+"?");
                        dialog1.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                Intent intent1 = new Intent(MainActivity.this,AlbumPage.class);
                                intent1.putExtra("album_name",s);
                                startActivity(intent1);
                            }
                        });
                        dialog1.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                            }
                        });
                        dialog1.show();
                        break;
                    }
                }

                if(str.equals(p1.getName())){
                    Intent intent = new Intent(MainActivity.this,Picture.class);
                    intent.putExtra("photo_name",p1.getName());
                    intent.putExtra("photo_resId",String.valueOf( p1.getImageId()));
                    startActivity(intent);
                }
                if(str.equals(p2.getName())){
                    Intent intent = new Intent(MainActivity.this,Picture.class);
                    intent.putExtra("photo_name",p2.getName());
                    intent.putExtra("photo_resId",String.valueOf( p2.getImageId()));
                    startActivity(intent);
                }
                if(str.equals(p3.getName())){
                    Intent intent = new Intent(MainActivity.this,Picture.class);
                    intent.putExtra("photo_name",p3.getName());
                    intent.putExtra("photo_resId",String.valueOf( p3.getImageId()));
                    startActivity(intent);
                }
                if(str.equals(p4.getName())){
                    Intent intent = new Intent(MainActivity.this,Picture.class);
                    intent.putExtra("photo_name",p4.getName());
                    intent.putExtra("photo_resId",String.valueOf( p4.getImageId()));
                    startActivity(intent);
                }
                if(str.equals(p5.getName())){
                    Intent intent = new Intent(MainActivity.this,Picture.class);
                    intent.putExtra("photo_name",p5.getName());
                    intent.putExtra("photo_resId",String.valueOf( p5.getImageId()));
                    startActivity(intent);
                }

            }
        });


    }

    private void initData(){
//        data.add("Album1");
//        data.add("Album2");
//        data.add("Album3");
        try{
            SQLiteDatabase db = dbHelper.getWritableDatabase();
            //Cursor cursor = db.rawQuery("Select * from Word", null);
            String[] coiumns={"AlbumName","Path"};
            Cursor cursor =db.query("albums",coiumns,null,null,null,null,null);
            if(cursor.moveToFirst()){
                do{
                    data.add(cursor.getString(0));
                }while(cursor.moveToNext());
            }
            cursor.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
