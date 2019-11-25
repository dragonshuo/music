package com.example.music;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.io.File;

public class list_delete extends AppCompatActivity implements View.OnClickListener {
    private EditText ed_delete;
    private Button delete_ok;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_delete);
        ed_delete=(EditText)findViewById(R.id.ed_delete);
        delete_ok=(Button)findViewById(R.id.delete_ok);
        delete_ok.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        String name=ed_delete.getText().toString();
        File file=new File(Environment.getExternalStorageDirectory()+"/mymusic/"+name+".mp3");
        System.out.println("删除路径为"+Environment.getExternalStorageDirectory()+"/mymusic/"+name+".mp3");
        if(file.exists())
        {
            file.delete();
            System.out.println("删除成功");
        }
    }
}
