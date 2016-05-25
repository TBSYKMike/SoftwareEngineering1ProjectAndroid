package com.example.iuliu.androiddb;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;

public class GetJSON extends AppCompatActivity {
   /* String JSON_STRING1,JSON_STRING2,JSON_STRING3;
    String json_string1,json_string2,json_string3;
    private String json_url;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_items_layout);
        this.getJSON1();
       // this.getJSON2();
      //  this.getJSON3();

    }

    public void getJSON1() {
       BackgroundTask backgroundTask =new BackgroundTask();
        backgroundTask.execute();


    }


    public void parseJSON(View view)
    {

        Intent intent=new Intent (this,DisplayList.class);
        intent.putExtra("json_data",json_string1);

        startActivity(intent);

    }
    public void checkList(View view)
    {

        Intent intent=new Intent (this,TradingLists.class);
       // intent.putExtra("json_data3",json_string3);
        intent.putExtra("json_data2",json_string2);
        intent.putExtra("json_data1", json_string1);
        Singleton.getInstance().setMyListonJSON(json_string2);

        startActivity(intent);

    }

    public String getJson_url() {
        return json_url;
    }

    public void setJson_url(String json_url) {
        this.json_url = json_url;
    }
//////////////////////////////////////////////////////////////////////////////////


    class BackgroundTask extends AsyncTask<String, Void, String> {

        @Override
        protected void onPreExecute()
        {
            setJson_url("http://mybarter.net16.net/json_data_new.php");
        }

        @Override
        protected String doInBackground(String... params)
        {
            try
            {
                URL url = new URL(getJson_url());
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                InputStream inputStream = httpURLConnection.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
                StringBuilder stringBuilder = new StringBuilder();
                while((JSON_STRING1 = bufferedReader.readLine())!= null)
                {
                stringBuilder.append(JSON_STRING1);
                }
                bufferedReader.close();
                inputStream.close();
                httpURLConnection.disconnect();
                return stringBuilder.toString().trim();
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onProgressUpdate(Void... values)
        {
            super.onProgressUpdate(values);
        }

        @Override
        protected void onPostExecute(String result)
        {
         //   TextView textView = (TextView)findViewById(R.id.textView);
           // textView.setText(result);
            json_string1=result;
           // Toast.makeText(getApplicationContext(), result, Toast.LENGTH_LONG).show();
        }
    }
/////////////////////////////////////////////////////////////////////////////////////////////////////////


    public void checkOwn(View view)
    {
        startActivity(new Intent(this, CheckTrades.class));
    }*/
}