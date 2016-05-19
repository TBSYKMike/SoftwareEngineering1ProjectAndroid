package com.example.iuliu.androiddb;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

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
    String JSON_STRING1,JSON_STRING2;
    String json_string1,json_string2;
    private String json_url;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_items_layout);
        this.getJSON1();
        this.getJSON2();

    }

    public void getJSON1() {
       BackgroundTask backgroundTask =new BackgroundTask();
        backgroundTask.execute();


    }
    public void getJSON2() {
         int userId=3;
        String stringUserId=Integer.toString(userId);
        BackgroundTask2 backgroundTask2 =new BackgroundTask2();
        backgroundTask2.execute(stringUserId);

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
       intent.putExtra("json_data2",json_string2);
        intent.putExtra("json_data1",json_string1);
        startActivity(intent);

    }

    public String getJson_url() {
        return json_url;
    }

    public void setJson_url(String json_url) {
        this.json_url = json_url;
    }

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

    class BackgroundTask2 extends AsyncTask<String,Void,String> {
        String login_check_url;

        @Override
        protected void onPreExecute() {
            login_check_url ="http://mybarter.net16.net/json_data_item_user_select.php" ;
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(String... args) {
            String  stringUserID;
            stringUserID=args[0];


            try {

                URL url =new URL(login_check_url);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                OutputStream outputStream = httpURLConnection.getOutputStream();

                try {

                    BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));
                    String data_string = URLEncoder.encode("user_id", "UTF-8")+"="+URLEncoder.encode(stringUserID,"UTF-8");
                    bufferedWriter.write(data_string);
                    bufferedWriter.flush();
                    bufferedWriter.close();
                    outputStream.close();
                    InputStream inputStream=httpURLConnection.getInputStream();
                    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
                    StringBuilder stringBuilder = new StringBuilder();
                    while((JSON_STRING2 = bufferedReader.readLine())!= null)
                    {
                        stringBuilder.append(JSON_STRING2);
                    }
                    bufferedReader.close();
                    inputStream.close();
                    httpURLConnection.disconnect();
                    return stringBuilder.toString().trim();
                }catch (Exception e) {
                    e.printStackTrace();
                }
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
            json_string2=result;
        }
    }

    public void checkOwn(View view)
    {
        startActivity(new Intent(this, CheckOwnAdds.class));
    }
}