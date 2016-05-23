package com.example.iuliu.androiddb;

import android.content.Intent;
import android.os.AsyncTask;
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

/**
 * Created by Iuliu on 2016-05-21.
 */
public class GetNewJSON {
    private String stringUrl,stringValue;
    String JSON_STRING;
    String json_string;
    private String json_url;

    public GetNewJSON(String stringUrl)
    {
        this.stringUrl=stringUrl;

    }


    public void createStringJSON(String stringValue)
    {
      //  string="http://mybarter.net16.net/disable_add.php";

        BackgroundTask backgroundTask = new BackgroundTask();
        backgroundTask.execute(stringValue);
    }

    class BackgroundTask extends AsyncTask<String,Void,String> {
        String login_check_url;

        @Override
        protected void onPreExecute() {
            login_check_url = stringUrl;
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
                    while((JSON_STRING = bufferedReader.readLine())!= null)
                    {
                        stringBuilder.append(JSON_STRING);
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
        protected void onProgressUpdate(Void... values)
        {
            super.onProgressUpdate(values);
        }

        @Override
        protected void onPostExecute(String result)
        {
            json_string=result;

        }
    }

    public String getResult()
    {
        return json_string;
    }
}
