package com.example.music;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;

public class dfile extends android.os.AsyncTask<String,Integer, InputStream> {


    @Override
    protected void onPreExecute() {
        System.out.println("进程对话框");
    }

    protected InputStream doInBackground(String... urlStr) {//String...为可变长数组，接收0到多个参数

        StringBuffer StringBuffer = new StringBuffer();//字符串数组对象
        InputStream inputStream=null;
        try {
            /**
             httpURLConnection.getInputStream()以字节的方式读取需要下载文件
             InputStreamReader把一个字节流转换成字符流
             BufferedReader把字符流套上一个BufferReader装饰方便一行行读取
             **/
            System.out.println("网址为"+urlStr[0]);
            URL url = new URL(urlStr[0]);
            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
            //httpURLConnection.setRequestMethod("POST");
            httpURLConnection.setRequestProperty("Accept", "*/*");
            httpURLConnection.connect();
            //httpURLConnection.setRequestMethod("POST");
            //httpURLConnection.setDoInput(true);
            //httpURLConnection.setDoOutput(true);
            int code=httpURLConnection.getResponseCode();
            System.out.println("状态码为"+code);
            if(code!=HttpURLConnection.HTTP_OK)
            {
                throw new Exception("文件读取失败");
            }
//            String line=null;
            inputStream = httpURLConnection.getInputStream();
            HttpDownloader downloader=new HttpDownloader();
            FileUtils fileUtils=new FileUtils();
            System.out.println("文件路径为"+fileUtils.getSDPATH());
            try {
                File dir = fileUtils.creadSDDir("mymusic");
                File file = fileUtils.creadSDfile(urlStr[1]+".mp3");
                System.out.println("文件创建成功");
                if (fileUtils.isFileExist("mymusic/"+urlStr[1]+".mp3")) {
                    System.out.println("存在");
                } else {
                    System.out.println("不存在");
                }
                OutputStream outputStream = null;
                System.out.println("下载文件");
                System.out.println("文件绝对路径为"+file.getAbsolutePath());
                System.out.println("输入流为"+inputStream.toString());
                outputStream = new FileOutputStream(file);
                int length=0;
                byte[] buffer = new byte[4 * 1024];
                //length = inputStream.read(buffer);//分开写竟然成功了
                while (( length = inputStream.read(buffer)) >0) {
                    System.out.println("------------------------------------------------------------------------------------------------------------------------------------------");
                    System.out.println(length);
                    outputStream.write(buffer,0,length);//此种方法可以保证不会将缓存里的都写进去（没有数据的地方）
                    //length = inputStream.read(buffer);
                }
                outputStream.flush();
                System.out.println("写入成功");
            } catch (FileNotFoundException e) {
                e.printStackTrace();
                System.out.println("写入失败");
            } catch (IOException e) {
                e.printStackTrace();
            }
            System.out.println("写入成功");
//            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
//            while ((line = bufferedReader.readLine()) != null) {
//                StringBuffer.append(line);
//                System.out.println(line);
//            }
//            System.out.println(StringBuffer.toString());
            //inputStream=new DataInputStream(httpURLConnection.getInputStream());
            System.out.println("已经获取输入流 ");
            //bufferedReader=new BufferedReader(new InputStreamReader(httpURLConnection.getInputStream()));
             /*
                以行为单位读取数据，每次读取一行，并把这个行字符返回到line中
                当line不为null指针，则line里有数据
                这时把读取的line对象添加到将要返回的Stringbuffer中
                当字符读取完毕readline方法没有读取到数据则返回null
                 */
            System.out.println(StringBuffer.toString());
            if (httpURLConnection != null) {
                httpURLConnection.disconnect();
            }

        } catch (ProtocolException e) {
            e.printStackTrace();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        String line;
        StringBuffer stringBuffer1=new StringBuffer();
//        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
//        System.out.println("============================================================================================================================");
//        try {
//
//            while ((line = bufferedReader.readLine()) != null) {
//                stringBuffer1.append(line);
//                System.out.println(line);
//            }
//            System.out.println("打印buffer:"+stringBuffer1.toString());
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
        return inputStream;
    }

    @Override
    protected void onPostExecute(InputStream inputStream) {
        super.onPostExecute(inputStream);
//        HttpDownloader downloader=new HttpDownloader();
//        FileUtils fileUtils=new FileUtils();
//        System.out.println("文件路径为"+fileUtils.getSDPATH());
//        try {
//            File dir=fileUtils.creadSDDir("Qbina1");
//            File file=fileUtils.creadSDfile("dragon.mp3");
//            System.out.println("文件创建成功");
//            if (fileUtils.isFileExist("Qbina1/dragon.mp3")) {
//                System.out.println("存在");
//            } else {
//                System.out.println("不存在");
//            }
//            OutputStream outputStream=null;
        /*try {
            fileUtils.creadSDfile("dragon");
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (fileUtils.isFileExist("Qbina/dragon")) {
            System.out.println("存在");
        } else {
            System.out.println("不存在");
        }*/
//        System.out.println("下载文件");
//        System.out.println("文件绝对路径为"+file.getAbsolutePath());
//        System.out.println("输入流为"+inputStream.toString());
//            outputStream = new FileOutputStream(file);
//            int length;
//            byte[] buffer = new byte[4 * 1024];
//            length = inputStream.read(buffer);//分开写竟然成功了
//            while (length !=-1) {
//                System.out.println("------------------------------------------------------------------------------------------------------------------------------------------");
//                System.out.println(length);
//                outputStream.write(buffer);
//            }
//            outputStream.flush();
//            System.out.println("写入成功");
//        } catch (FileNotFoundException e) {
//            e.printStackTrace();
//            System.out.println("写入失败");
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        System.out.println("写入成功");
        //int result=downloader.downloadFile("https://res.iciba.com/resource/amp3/oxford/0/1c/f3/1cf3980c4529878b690ded143c409664.mp3","111","111");
        //System.out.println(result);

    }
}
