package com.example.music;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;

public class list_add extends AppCompatActivity implements View.OnClickListener {
private EditText ed_add;
private Button add_ok;
private  String[] bigid=new String[30];
private String key=new String();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_add);
        ed_add=(EditText)findViewById(R.id.ed_add);
        add_ok=(Button)findViewById(R.id.add_ok);
        add_ok.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        key=ed_add.getText().toString();
        SendRequestWithOkhttp();
    }
    private void SendRequestWithOkhttp(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println("进入多线程");
                HttpURLConnection connection=null;
                BufferedReader reader=null;
                try{
                    URL url=new URL("http://bzpnb.xyz:3000/search?keywords="+key+"");
                    connection=(HttpURLConnection)url.openConnection();
                    connection.setRequestProperty("Accept", "*/*");
                    System.out.println("发送get请求");
                    connection.connect();
                    System.out.println("网络已成功连接");
                    //connection.setConnectTimeout(8000);
                    //connection.setReadTimeout(8000);
                    InputStream in=connection.getInputStream();
                    System.out.println("获取数据");
                    reader=new BufferedReader(new InputStreamReader(in));
                    StringBuilder response=new StringBuilder();
                    String line;
                    while ((line=reader.readLine())!=null)
                    {
                        response.append(line);
                        System.out.println("缓存读取"+line);
                    }
                    parseJSONwithJSONobject(response.toString());
                } catch (ProtocolException e) {
                    e.printStackTrace();
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }
    private void parseJSONwithJSONobject(String jsonData){
        try {
            JSONObject d = new JSONObject(jsonData);//获取数据为对象(这是最外层没有名字的)
            JSONObject d1=d.getJSONObject("result");//获取第一层：首先出现的有名字的对象(获取对象里的对象)
            JSONArray s1 = d1.getJSONArray("songs");//获取对象里的数组
            for(int i=0;i<s1.length();i++)
            {
                JSONObject d2=s1.getJSONObject(i);
                String id=d2.getString("id");
                String name=d2.getString("name");
                System.out.println("获取第"+i+"个名为"+name+"id为"+id);
                bigid[i]=id;
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        findmusic();
    }
    private void findmusic() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println("进入多线程");
                HttpURLConnection connection=null;
                BufferedReader reader=null;
                try{
                    URL url=new URL("http://bzpnb.xyz:3000/song/url?id="+bigid[1]);
                    connection=(HttpURLConnection)url.openConnection();
                    connection.setRequestProperty("Accept", "*/*");
                    System.out.println("发送get请求");
                    connection.connect();
                    System.out.println("网络已成功连接");
                    //connection.setConnectTimeout(8000);
                    //connection.setReadTimeout(8000);
                    InputStream in=connection.getInputStream();
                    System.out.println("获取数据");
                    reader=new BufferedReader(new InputStreamReader(in));
                    StringBuilder response=new StringBuilder();
                    String line;
                    while ((line=reader.readLine())!=null)
                    {
                        response.append(line);
                        System.out.println("缓存读取"+line);
                    }
                    parseJSONwithJSONobject1(response.toString());
                } catch (ProtocolException e) {
                    e.printStackTrace();
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    private void parseJSONwithJSONobject1(String jsobdata) {
        try {
            JSONObject d = new JSONObject(jsobdata);
            JSONArray s=d.getJSONArray("data");
            String url=new String();
            for(int i=0;i<s.length();i++)
            {
                JSONObject d1=s.getJSONObject(i);
                url=d1.getString("url");
                System.out.println("获得MP3网址为"+url);
            }
            HttpDownloader httpDownloader = new HttpDownloader();
            int result = httpDownloader.downloadFile(url,ed_add.getText().toString());
            System.out.println("下载结果为" + result);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
