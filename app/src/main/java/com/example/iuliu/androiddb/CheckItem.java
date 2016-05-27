package com.example.iuliu.androiddb;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Random;

public class CheckItem extends AppCompatActivity {

    JSONObject jsonObject;
    JSONArray jsonArray;
    AddsAdapter addsAdapter;
    ListView listView;
    ArrayList<Adds> arrayUsers;


    private String stringJSON;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_item);
        stringJSON=Singleton.getInstance().getMyListonJSON();
        arrayUsers=new ArrayList<Adds>();
        listView=(ListView)findViewById(R.id.listView2);

      //  ImageView pictureView=(ImageView)findViewById(R.id.picture_random);
        addsAdapter =new AddsAdapter(this,R.layout.row_layout,arrayUsers);
        listView.setAdapter(addsAdapter);

        try {
            ArrayList<Adds> listData = new ArrayList<>();


            jsonObject=new JSONObject(stringJSON);
            jsonArray=jsonObject.getJSONArray("server_response");
            int count=0;
            String item_id,item_name,item_info,item_picture_small,item_picture_large,item_condition,item_date,item_status,item_visit_count,item_winner_userID,item_user_userID,accountName,email;
            while (count<jsonArray.length())
            {
                JSONObject JO=jsonArray.getJSONObject(count);
                item_id=JO.getString("item_id");
                item_name=JO.getString("item_name");
                item_info=JO.getString("item_info");
                item_picture_small=JO.getString("item_picture_small");
                item_picture_large=JO.getString("item_picture_large");
                item_condition=JO.getString("item_condition");
                item_date=JO.getString("item_date");
                item_status=JO.getString("item_status");
                item_visit_count=JO.getString("item_visit_count");
                item_winner_userID=JO.getString("item_winner_userID");
                item_user_userID=JO.getString("item_user_userID");
                accountName=JO.getString("userName");
                email=JO.getString("email");
                Adds user=new Adds(item_id,item_name,item_info,item_picture_small,item_picture_large,item_condition,item_date,item_status,item_visit_count,item_winner_userID,item_user_userID,accountName,email);
                addsAdapter.add(user);
                count++;
            }

            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                @Override
                public void onItemClick(AdapterView<?> a, View v, int position, long id) {
                    Adds newsData = (Adds) listView.getItemAtPosition(position);
                    Toast.makeText(CheckItem.this, "Selected :" + " " + newsData.getItem_name(), Toast.LENGTH_LONG).show();

                    Intent intent=new Intent (getApplicationContext(),CheckItem.class);

                    startActivity(intent);

                }
            });

        } catch (Exception e) {
            e.printStackTrace();
        }

    }
    public void goBack(View view)
    {
        startActivity(new Intent(this, MainActivity.class));
    }
    public void createOffer(View view){
        Random rand=new Random();
        int supply=rand.nextInt(10);
        int demand=rand.nextInt(10);
        String stringSupply=Integer.toString(supply);
        String stringDemand=Integer.toString(demand);
        BackgroundTask backgroundTask = new BackgroundTask();
        backgroundTask.execute(stringSupply, stringDemand);

    }
    class BackgroundTask extends AsyncTask<String,Void,String> {
        String login_check_url;
        @Override
        protected void onPreExecute() {
            login_check_url = "http://mybarter.net16.net/add_new_bussines.php";
            super.onPreExecute();
    }

        @Override
        protected String doInBackground(String... args) {
            String stringSupply1, stringDemand1;
            stringSupply1 =args[0];
             stringDemand1=args[1];

        try {

            URL url =new URL(login_check_url);
            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
            httpURLConnection.setRequestMethod("POST");
            httpURLConnection.setDoOutput(true);
            OutputStream outputStream = httpURLConnection.getOutputStream();

            try {

                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));
                String data_string = URLEncoder.encode("suply","UTF-8")+"="+URLEncoder.encode(stringSupply1,"UTF-8")+"&"+
                        URLEncoder.encode("demand","UTF-8")+"="+URLEncoder.encode(stringDemand1,"UTF-8");
                bufferedWriter.write(data_string);

                bufferedWriter.flush();
                bufferedWriter.close();
                outputStream.close();
                InputStream inputStream=httpURLConnection.getInputStream();
                inputStream.close();
                httpURLConnection.disconnect();
                return "Data insert";
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
        Toast.makeText(getApplicationContext(),result,Toast.LENGTH_LONG).show();
    }
    }


    public void showComments(View view)
    {

        startActivity(new Intent(this, commentsSection.class));
    }
    public void showReport(View view)
    {
        startActivity(new Intent(this, commentsSection.class));
    }
}
