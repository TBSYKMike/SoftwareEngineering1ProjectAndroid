package com.example.iuliu.androiddb;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
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
import java.util.Random;

public class CheckOwnAdds extends AppCompatActivity {
 String string;
    int supply,demand;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_own_adds);
    }
    public void acceptOffer(View view)
    {
        Random rand=new Random();
        //int supply=rand.nextInt(10);
        //int demand=rand.nextInt(10);
         supply=4;
         demand=8;
        string="http://mybarter.net16.net/delete_accept_barter.php";
        String stringSupply=Integer.toString(supply);
        String stringDemand=Integer.toString(demand);
        BackgroundTask backgroundTask = new BackgroundTask();
        backgroundTask.execute(stringSupply, stringDemand);
     //   backgroundTask.execute(stringDemand);

    }
    public void declineOffer(View view)
    {
        string="http://mybarter.net16.net/decline_barter.php";
        supply=7;
        demand=9;
        String stringSupply=Integer.toString(supply);
        String stringDemand=Integer.toString(demand);
        BackgroundTask backgroundTask = new BackgroundTask();
        backgroundTask.execute(stringSupply, stringDemand);
    }
    public void disableAdd(View view)
    {
        string="http://mybarter.net16.net/disable_add.php";
        supply=1;
        demand=0;
        String stringSupply=Integer.toString(supply);
        String stringDemand=Integer.toString(demand);
        BackgroundTask backgroundTask = new BackgroundTask();
        backgroundTask.execute(stringSupply, stringDemand);
    }
    public void goBack(View view)
    {
        startActivity(new Intent(this, GetJSON.class));
    }
    class BackgroundTask extends AsyncTask<String,Void,String> {
        String login_check_url;

        @Override
        protected void onPreExecute() {
            login_check_url = string;
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(String... args) {
            String  stringDemand1,stringSupply1;
            stringSupply1=args[0];
            stringDemand1=args[1];

            try {

                URL url =new URL(login_check_url);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                OutputStream outputStream = httpURLConnection.getOutputStream();

                try {

                    BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));
                    String data_string = URLEncoder.encode("suply", "UTF-8")+"="+URLEncoder.encode(stringSupply1,"UTF-8")+"&"+
                            URLEncoder.encode("demand","UTF-8")+"="+URLEncoder.encode(stringDemand1,"UTF-8");
                    bufferedWriter.write(data_string);

                    bufferedWriter.flush();
                    bufferedWriter.close();
                    outputStream.close();
                    InputStream inputStream=httpURLConnection.getInputStream();
                    inputStream.close();
                    httpURLConnection.disconnect();
                    return "Data delete";
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
            Toast.makeText(getApplicationContext(), result, Toast.LENGTH_LONG).show();
        }
    }
}
