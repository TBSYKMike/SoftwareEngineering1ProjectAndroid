package com.example.iuliu.androiddb;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;

public class AddInfo extends AppCompatActivity {
    EditText Name,Password,Random;
    String name,password,random;

    String test;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_info_layout);
        Name=(EditText)findViewById(R.id.et_name);
        Password=(EditText)findViewById(R.id.et_password);
        Random=(EditText)findViewById(R.id.et_random);
    }
    public void saveInfo(View view)
    {
        name=Name.getText().toString();
        password=Password.getText().toString();
        random=Random.getText().toString();
        BackgroundTask backgroundTask = new BackgroundTask();
        backgroundTask.execute(name,password,random);

    }
    class BackgroundTask extends AsyncTask<String,Void,String>
    {
        String add_info_url;
        @Override
        protected void onPreExecute() {
            add_info_url= "http://mybarter.net16.net/add_info.php";
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(String... args) {
            String name,password,random;
            name=args[0];
            password=args[1];
            random=args[2];
            try {

                URL url =new URL(add_info_url);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                OutputStream outputStream = httpURLConnection.getOutputStream();

                try {
                   test= Encrypt.encryptPassword(random);
                   // test=Kripto.encrypt(random);
                    int i=0;
                  //  i=test.length();
                   // System.out.println("LenghtTTTTTTTTTTTTTTTTTTTTTTTTT"+i);
                    BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));
                    String data_string = URLEncoder.encode("name","UTF-8")+"="+URLEncoder.encode(name,"UTF-8")+"&"+
                            URLEncoder.encode("password","UTF-8")+"="+URLEncoder.encode(password,"UTF-8")+"&"+
                            URLEncoder.encode("random","UTF-8")+"="+URLEncoder.encode(test,"UTF-8");
                    bufferedWriter.write(data_string);

                    bufferedWriter.flush();
                    bufferedWriter.close();
                    outputStream.close();
                    InputStream inputStream=httpURLConnection.getInputStream();
                    inputStream.close();
                    httpURLConnection.disconnect();
                    return "Data insert";
                 //   System.out.println(test+"ZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZz");
                }catch (Exception e) {
                    e.printStackTrace();
                }

          //   String test=objectSecret.encrypt(random);
             //

              //  System.out.println(randomSecret+"DDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDD");


            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            }


            return null;
        }

        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);
        }

        @Override
        protected void onPostExecute(String result) {
            Toast.makeText(getApplicationContext(),result,Toast.LENGTH_LONG).show();
        }
    }
}
