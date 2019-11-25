package com.example.music;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class selectmusic extends AppCompatActivity implements AdapterView.OnItemClickListener {

    private File music;
    private ListView select;
    private ArrayList<File> files;
    private ArrayList<String> filenames=new ArrayList<String>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_selectmusic);
        select=(ListView)findViewById(R.id.select);
        searchfile searchfile=new searchfile();
        String Path=Environment.getExternalStorageDirectory()+"/mymusic";
        File file=new File(Path);
        if(file.exists()) {
            System.out.println("select文件存在");
            files=searchfile.searchmusicfile(file);
            for(File file1:files)
            {
                filenames.add(file1.getName());
                System.out.println(file1.getName());
            }
            ArrayAdapter<String> adapter=new ArrayAdapter<String>(selectmusic.this,android.R.layout.simple_list_item_1,filenames);
            select.setAdapter(adapter);
            select.setOnItemClickListener(this);
        }
    }

    @Override
    public void onItemClick(AdapterView<?> partent, View view, int position, long id) {
        music= (File) files.toArray()[position];
        if(music.exists())
        {
            System.out.println("music 存在");
            Intent intent=new Intent(selectmusic.this,MainActivity.class);
            intent.putExtra("file",music.getPath());//或许传名字更好
            intent.putExtra("int",position);
            setResult(RESULT_OK,intent);
            finish();
//            startActivityForResult(intent,1);
        }
    }
//    protected void onActivityResult(int requestCode,int ResultCode,Intent intent) {
//        super.onActivityResult(requestCode, ResultCode, intent);
//        switch (requestCode) {
//            case 1:
//                if (requestCode == RESULT_OK) {
//                    String returnedData = music.getPath();
//                }break;
//                default:break;
//        }
//    }
}
