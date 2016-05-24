package com.example.iuliu.androiddb;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

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
import java.util.ArrayList;

import android.app.AlertDialog;
import android.content.DialogInterface.OnClickListener;

public class TradingLists extends AppCompatActivity  {
    String json_strin;
    String json_string2;
    String json_string1;
    String JSON_STRING2,JSON_STRING1;
    JSONObject jsonObject2;
    JSONArray jsonArray2;
    AddsAdapter addsAdapter2;
    ListView listView2;
    ArrayList<Adds> arrayUsers;
    ImageButton disableButton;
    protected String value,stringURL, stringDisable;
    protected int intDelete;
    protected Adds myPosition;
    private String json_url;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trading_lists);
        getJSONMyItem();
        getJSON();
        //json_string2=getIntent().getExtras().getString("json_data2");
        listView2=(ListView)findViewById(R.id.listView3);
        disableButton=(ImageButton)findViewById(R.id.btn_disable);
        stringDisable =null;
        intDelete=-1;
        this.populate(json_string2);


                listView2.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener(){
                    @Override
                    public boolean onItemLongClick(AdapterView<?> arg0, View arg1,
                    int pos, long id) {

                        Adds newsData = (Adds) listView2.getItemAtPosition(pos);
                        Toast.makeText(TradingLists.this, "Selected :" + " " + newsData.getItem_id(), Toast.LENGTH_LONG).show();
                        value=newsData.getItem_id();

                        Singleton.getInstance().setItem_id(value);
                        Intent intent=new Intent (getApplicationContext(),CheckTrades.class);
                        startActivity(intent);
                        return false;
                }
            });
        listView2.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                @Override
                public void onItemClick(AdapterView<?> a, View v, int position, long id) {
                    Adds newsData = (Adds) listView2.getItemAtPosition(position);
                    myPosition=newsData;
                    stringDisable =newsData.getItem_id();
                    intDelete=position;
                  //  Toast.makeText(TradingLists.this, "Selected :" + " " + newsData.getItem_name() + "-deleted", Toast.LENGTH_LONG).show();
                   // Log.v("long clicked", "pos: " + position);
                 //   removeItemFromList(position);
                    String item_id,item_name,item_info,item_picture_small,item_picture_large,item_condition,item_date,item_status,item_visit_count,item_winner_userID,item_user_userID,accountName;
                    item_id=newsData.getItem_name();
                    item_name=newsData.getItem_name();
                    item_info=newsData.getItem_info();
                    item_picture_small=newsData.getItem_picture_small();
                    item_picture_large=newsData.getItem_picture_large();
                    item_condition=newsData.getItem_condition();
                    item_date=newsData.getItem_date();
                    item_status=newsData.getItem_status();
                    item_visit_count=newsData.getItem_visit_count();
                    item_winner_userID=newsData.getItem_winner_userID();
                    item_user_userID=newsData.getItem_user_userID();
                    accountName=newsData.getUser_name();
                    Adds user=new Adds(item_id,item_name,item_info,item_picture_small,item_picture_large,item_condition,item_date,item_status,item_visit_count,item_winner_userID,item_user_userID,accountName);
                    addsAdapter2.remove(user);
                    addsAdapter2.notifyDataSetChanged();


                }
            });
        }

    @Override
    protected void onResume() {
        super.onResume();
        populate(json_string2);
    }

    @Override
    protected void onPostResume() {
        super.onPostResume();

        populate(json_string2);
    }

    public void getJSONMyItem(){
    int userId=9;
    String stringUserId=Integer.toString(userId);
    BackgroundTask2 backgroundTask2 =new BackgroundTask2();
    backgroundTask2.execute(stringUserId);
}
    public void getJSON(){

        BackgroundTaskAllItems backgroundTask =new BackgroundTaskAllItems();
        backgroundTask.execute();
    }


        public void populate(String ss){
        arrayUsers=new ArrayList<Adds>();


        ImageView pictureView=(ImageView)findViewById(R.id.picture_random);
        addsAdapter2 =new AddsAdapter(this,R.layout.row_layout,arrayUsers);
        listView2.setAdapter(addsAdapter2);

        try {
            jsonObject2=new JSONObject(ss);
            jsonArray2=jsonObject2.getJSONArray("server_response");
            int count=0;
            String item_id,item_name,item_info,item_picture_small,item_picture_large,item_condition,item_date,item_status,item_visit_count,item_winner_userID,item_user_userID,accountName;
            while (count<jsonArray2.length())
            {
                JSONObject JO2=jsonArray2.getJSONObject(count);
                item_id=JO2.getString("item_id");
                item_name=JO2.getString("item_name");
                item_info=JO2.getString("item_info");
                item_picture_small=JO2.getString("item_picture_small");
                item_picture_large=JO2.getString("item_picture_large");
                item_condition=JO2.getString("item_condition");
                item_date=JO2.getString("item_date");
                item_status=JO2.getString("item_status");
                item_visit_count=JO2.getString("item_visit_count");
                item_winner_userID=JO2.getString("item_winner_userID");
                item_user_userID=JO2.getString("item_user_userID");
                accountName=JO2.getString("userName");
                Adds user=new Adds(item_id,item_name,item_info,item_picture_small,item_picture_large,item_condition,item_date,item_status,item_visit_count,item_winner_userID,item_user_userID,accountName);
                addsAdapter2.add(user);
                count++;
            }
        } catch (Exception e) {
            e.printStackTrace();

        }


    }

    public void addItems(View view)
    {
        startActivity(new Intent(this, AddNewAdvert.class));
    }
    public void viewItems(View view)
    {
        //json_string=getIntent().getExtras().getString("json_data1");
        Intent intent=new Intent (this,DisplayList.class);
        intent.putExtra("json_data",json_string1);
        startActivity(intent);
    }

    public void disableAdd(String id)
    {
        stringURL="http://mybarter.net16.net/disable_add.php";

        BackgroundTask backgroundTask = new BackgroundTask();
        backgroundTask.execute(id);
    }

    public void disableAddActive(String id)
    {
        stringURL="http://mybarter.net16.net/disable_add_active.php";

        BackgroundTaskActive backgroundTask2 = new BackgroundTaskActive();
        backgroundTask2.execute(id);
    }


    public void disableItem(View view){
       /* if(intDelete ==-1){
            Toast.makeText(TradingLists.this, "You must select by long click an item", Toast.LENGTH_LONG).show();
        }
        else
        {
            disableAdd(stringDisable);
            disableAddActive(stringDisable);

           this.removeItemFromList(intDelete);
            addsAdapter2.notifyDataSetChanged();
            stringDisable =null;
            intDelete=-1;


        }*/
        populate(json_string2);
    }

    protected void removeItemFromList(int position) {
        final int deletePosition = position;

        AlertDialog.Builder alert = new AlertDialog.Builder(
              TradingLists.this);

        alert.setTitle("Delete");
        alert.setMessage("Do you want delete this item?");
        alert.setPositiveButton("YES", new OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // TOD O Auto-generated method stub

                // main code on after clicking yes
                arrayUsers.remove(deletePosition);
                addsAdapter2.notifyDataSetChanged();
                addsAdapter2.notifyDataSetInvalidated();

            }
        });
        alert.setNegativeButton("CANCEL", new OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // TODO Auto-generated method stub
                dialog.dismiss();
            }
        });

        alert.show();

    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////
    class BackgroundTask extends AsyncTask<String,Void,String> {
        String login_check_url;

        @Override
        protected void onPreExecute() {
            login_check_url = stringURL;
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(String... args) {
            String  stringDemand1,stringSupply1;
            stringSupply1=args[0];


            try {

                URL url =new URL(login_check_url);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                OutputStream outputStream = httpURLConnection.getOutputStream();

                try {

                    BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));
                    String data_string = URLEncoder.encode("suply", "UTF-8")+"="+URLEncoder.encode(stringSupply1,"UTF-8");
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

 //////////////////////////////////////////////////////////////////////////////////////////////////////

    class BackgroundTaskActive extends AsyncTask<String,Void,String> {
        String login_check_url;

        @Override
        protected void onPreExecute() {
            login_check_url = stringURL;
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(String... args) {
            String  stringDemand1,stringSupply1;
            stringSupply1=args[0];


            try {

                URL url =new URL(login_check_url);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                OutputStream outputStream = httpURLConnection.getOutputStream();

                try {

                    BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));
                    String data_string = URLEncoder.encode("suply", "UTF-8")+"="+URLEncoder.encode(stringSupply1,"UTF-8");
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

    class BackgroundTask2 extends AsyncTask<String,Void,String> {
        String login_check_url;

        @Override
        protected void onPreExecute() {
            login_check_url ="http://mybarter.net16.net/json_data_item_user_select1.php" ;
            super.onPreExecute();
        }
        //http://mybarter.net16.net/json_data_item__select_to_OthersBid.php
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
    public String getJson_url() {
        return json_url;
    }

    public void setJson_url(String json_url) {
        this.json_url = json_url;
    }


    /////////////////////////////////////////////////////////////////////////////////////////////////////////////
    class BackgroundTaskAllItems extends AsyncTask<String, Void, String> {

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

}


