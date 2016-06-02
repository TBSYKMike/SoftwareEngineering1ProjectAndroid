package com.example.iuliu.androiddb;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

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

public class New_Transaction extends AppCompatActivity {
    Button createOffer;
    JSONObject jsonObject;
    JSONArray jsonArray;
    AddsAdapterOnlyPicture addsNewAdapter;
    ListView listView;
    ArrayList<Adds> arrayUsers;
    private String stringJSON;
    private TextView viewName;
    private String temp1, temp2;
    private String imgBigUrl;

    ImageView pictureView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new__transaction);
        // createOffer=(Button)findViewById(R.id.button7);
        stringJSON = Singleton.getInstance().getMyListonJSON();
        arrayUsers = new ArrayList<Adds>();
        listView = (ListView) findViewById(R.id.listView5);
        viewName = (TextView) findViewById(R.id.textView2);
        viewName.setText(Singleton.getInstance().getNameItem());
        imgBigUrl = Singleton.getInstance().getStringBigPictureUrl();
        pictureView = (ImageView) findViewById(R.id.imageViewBigPicture);
        addsNewAdapter = new AddsAdapterOnlyPicture(this, R.layout.row_layout_small_picture, arrayUsers);
        listView.setAdapter(addsNewAdapter);

        try {
            ArrayList<Adds> listData = new ArrayList<>();


            jsonObject = new JSONObject(stringJSON);
            jsonArray = jsonObject.getJSONArray("server_response");
            int count = 0;
            String item_id, item_name, item_picture_small;
            while (count < jsonArray.length()) {
                JSONObject JO = jsonArray.getJSONObject(count);
                item_id = JO.getString("item_id");
                item_name = JO.getString("item_name");
                item_picture_small = JO.getString("item_picture_small");


                Adds user = new Adds(item_id, item_name, item_picture_small);
                addsNewAdapter.add(user);
                count++;
                listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
                    @Override
                    public boolean onItemLongClick(AdapterView<?> arg0, View arg1,
                                                   int pos, long id) {
                        Adds newsData = (Adds) listView.getItemAtPosition(pos);
                        temp1 = Singleton.getInstance().getItemId();
                        temp2 = newsData.getItem_id();

                        // object.createOffer(temp1,temp2);
                        createOffer(temp2, temp1);
                        Toast.makeText(New_Transaction.this, "A new barter was created :" + " " + temp2 + "" + temp1, Toast.LENGTH_LONG).show();
                        Log.v("long clicked", "pos: " + pos);


                        return true;
                    }
                });
            }
            Picasso.with(this.getBaseContext()).load(imgBigUrl).into(pictureView);
        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    public void goBack(View view) {
        startActivity(new Intent(this, MainActivity.class));
    }

    public void createOffer(String s, String x) {

        if (s.equals(x)) {
            Toast.makeText(New_Transaction.this, "It is now possible to make the deal :" + " " + temp1 + "" + temp2, Toast.LENGTH_LONG).show();

        } else {
            BackgroundTask backgroundTask = new BackgroundTask();
            backgroundTask.execute(s, x);
        }
    }

    public void createNewAdvert(View view){
        startActivity(new Intent(New_Transaction.this, AddNewAdvert.class));
    }


    class BackgroundTask extends AsyncTask<String, Void, String> {
        String login_check_url;

        @Override
        protected void onPreExecute() {
            login_check_url = "http://mybarter.net16.net/add_new_bussines.php";
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(String... args) {
            String stringSupply1, stringDemand1;
            stringSupply1 = args[0];
            stringDemand1 = args[1];

            try {

                URL url = new URL(login_check_url);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                OutputStream outputStream = httpURLConnection.getOutputStream();

                try {

                    BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));
                    String data_string = URLEncoder.encode("suply", "UTF-8") + "=" + URLEncoder.encode(stringSupply1, "UTF-8") + "&" +
                            URLEncoder.encode("demand", "UTF-8") + "=" + URLEncoder.encode(stringDemand1, "UTF-8");
                    bufferedWriter.write(data_string);

                    bufferedWriter.flush();
                    bufferedWriter.close();
                    outputStream.close();
                    InputStream inputStream = httpURLConnection.getInputStream();
                    inputStream.close();
                    httpURLConnection.disconnect();
                    return "Data insert";
                } catch (Exception e) {
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

    public void showComments(View view) {
        startActivity(new Intent(this, commentsSection.class));
    }

    public void showReport(View view) {
        startActivity(new Intent(this, reportUserSection.class));
    }

    public void showLargePic(View view) {
        System.out.println(imgBigUrl);
        Intent i = new Intent(this, ShowLargePicture.class);
        i.putExtra("largepic", imgBigUrl);
        startActivity(i);

    }
}
