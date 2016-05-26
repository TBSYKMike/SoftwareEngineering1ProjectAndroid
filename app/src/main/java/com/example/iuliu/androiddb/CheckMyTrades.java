package com.example.iuliu.androiddb;

import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.RadioButton;
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

public class CheckMyTrades extends AppCompatActivity {

    protected String JSON_STRING3, json_string3;
    protected String JSON_STRING4, json_string4;
    protected String stringOwnIndex;
    protected String stringURL;
    protected String stringIndex;
    private Button acceptButton;
    private Button declineButton;
    ArrayList<Adds> arrayUsers;
    private int indexToDelete;
    AddsAdapter addsAdapter;
    ListView listView;
    String  json_string2;
    JSONObject jsonObject;
    JSONArray jsonArray;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_own_adds);
        listView = (ListView) findViewById(R.id.listView4);
        acceptButton = (Button) findViewById(R.id.buttonAcceptBarter);
        acceptButton.setEnabled(false);
        declineButton =(Button)findViewById(R.id.buttonDeclineOffer);
        declineButton.setEnabled(false);
        stringOwnIndex = Singleton.getInstance().getItemOwn_id();
        indexToDelete=-1;

        getJSON3();
        getJSON4();
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> a, View v, int position, long id) {
                Adds newsData = (Adds) listView.getItemAtPosition(position);
                indexToDelete=position;
                stringIndex = newsData.getItem_id();

            }
        });

    }
    @Override
    public void onResume(){
        super.onResume();
        getJSON3();
        getJSON4();
    }
    public void getJSON3() {
        BackgroundTask3 backgroundTask = new BackgroundTask3();
        backgroundTask.execute(stringOwnIndex);

    }

    public void getJSON4() {
        BackgroundTask4 backgroundTask4 = new BackgroundTask4();
        backgroundTask4.execute(stringOwnIndex);

    }

    public void onRadioButtonClicked(View v) {

        boolean checked = ((RadioButton) v).isChecked();

        switch (v.getId()) {

            case R.id.radio2:
                if (checked)
                onResume();
                this.populate(json_string3);
                acceptButton.setEnabled(false);
                declineButton.setEnabled(false);
                break;
            case R.id.radio1:
                if (checked)
                    onResume();
                    this.populate(json_string4);
                acceptButton.setEnabled(true);
                declineButton.setEnabled(true);
                break;
            case R.id.radio:
                break;
        }
    }

    public void doAcceptBarted(String stringSupply, String stringDemand) {

        stringURL = "http://mybarter.net16.net/delete_accept_barter.php";

        BackgroundTaskAcceptOrDecline backgroundTaskAccept = new BackgroundTaskAcceptOrDecline();
        backgroundTaskAccept.execute(stringSupply, stringDemand);


    }
    public void doInactiveItems(String stringSupply,String stringDemand) {



        BackgroundTaskInactiveItems backgroundTaskAccept = new BackgroundTaskInactiveItems();
        backgroundTaskAccept.execute(stringSupply, stringDemand);


    }
    public void doDeclineBarted(String stringSupply, String stringDemand) {

        stringURL = "http://mybarter.net16.net/decline_barter.php";

        BackgroundTaskAcceptOrDecline backgroundTaskAccept = new BackgroundTaskAcceptOrDecline();
        backgroundTaskAccept.execute(stringSupply, stringDemand);
        addsAdapter.remove(indexToDelete);
        addsAdapter.notifyDataSetChanged();

    }

    public void acceptOffer(View view) {
        doAcceptBarted(stringIndex, stringOwnIndex);
        doInactiveItems(stringIndex,stringOwnIndex);
        Toast.makeText(CheckMyTrades.this, "You got a new barter" + stringIndex, Toast.LENGTH_LONG).show();

        this.sendEmail();
        addsAdapter.reset();
        super.onResume();
       // Intent intent = new Intent(this, MainActivity.class);
       // startActivity(intent);
    }


        public void declineOffer(View view)
        {

            doDeclineBarted(stringIndex, stringOwnIndex);
            Toast.makeText(CheckMyTrades.this, "You decline this offert" + stringIndex+""+stringOwnIndex, Toast.LENGTH_LONG).show();
        }
    private void sendEmail(){
        Intent intent=null,chooser=null;
        intent=new Intent(Intent.ACTION_SEND);
        intent.setData(Uri.parse("mailto:"));
        String[]to ={"iuliunicolaebarcan@gmail.com","iuliunicolaebarcan@gmail.com"};
        intent.putExtra(Intent.EXTRA_EMAIL,to);
        intent.putExtra(Intent.EXTRA_SUBJECT, "Hello");
        intent.putExtra(Intent.EXTRA_TEXT,"Dear Mr.");
        intent.setType("message/rfc822");
        chooser=Intent.createChooser(intent,"Email");
        startActivity(chooser);


    }
    public void populate(String ss) {

        arrayUsers = new ArrayList<Adds>();

        addsAdapter = new AddsAdapter(this, R.layout.row_layout, arrayUsers);



        listView.setAdapter(addsAdapter);
        json_string2 = ss;
        try {

            jsonObject = new JSONObject(json_string2);
            jsonArray = jsonObject.getJSONArray("server_response");
            int count = 0;
            String item_id, item_name, item_info, item_picture_small, item_picture_large, item_condition, item_date, item_status, item_visit_count, item_winner_userID, item_user_userID, accountName;
            while (count < jsonArray.length()) {
                JSONObject JO = jsonArray.getJSONObject(count);
                item_id = JO.getString("item_id");
                item_name = JO.getString("item_name");
                item_info = JO.getString("item_info");
                item_picture_small = JO.getString("item_picture_small");
                item_picture_large = JO.getString("item_picture_large");
                item_condition = JO.getString("item_condition");
                item_date = JO.getString("item_date");
                item_status = JO.getString("item_status");
                item_visit_count = JO.getString("item_visit_count");
                item_winner_userID = JO.getString("item_winner_userID");
                item_user_userID = JO.getString("item_user_userID");
                accountName = JO.getString("userName");
                Adds user = new Adds(item_id, item_name, item_info, item_picture_small, item_picture_large, item_condition, item_date, item_status, item_visit_count, item_winner_userID, item_user_userID, accountName);
                addsAdapter.add(user);
                count++;
            }


        } catch (Exception e) {
            e.printStackTrace();

        }


    }

    class BackgroundTask3 extends AsyncTask<String, Void, String> {
        String login_check_url;

        @Override
        protected void onPreExecute() {
            login_check_url = "http://mybarter.net16.net/json_data_item__select_to_OthersBid.php";
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(String... args) {
            String stringUserID;
            stringUserID = args[0];


            try {

                URL url = new URL(login_check_url);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                OutputStream outputStream = httpURLConnection.getOutputStream();

                try {

                    BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));
                    String data_string = URLEncoder.encode("user_id", "UTF-8") + "=" + URLEncoder.encode(stringUserID, "UTF-8");
                    bufferedWriter.write(data_string);
                    bufferedWriter.flush();
                    bufferedWriter.close();
                    outputStream.close();
                    InputStream inputStream = httpURLConnection.getInputStream();
                    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
                    StringBuilder stringBuilder = new StringBuilder();
                    while ((JSON_STRING3 = bufferedReader.readLine()) != null) {
                        stringBuilder.append(JSON_STRING3);
                    }
                    bufferedReader.close();
                    inputStream.close();
                    httpURLConnection.disconnect();
                    return stringBuilder.toString().trim();
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
            super.onPostExecute(result);
            json_string3 = result;
        }
    }

    class BackgroundTask4 extends AsyncTask<String, Void, String> {
        String login_check_url;

        @Override
        protected void onPreExecute() {
            login_check_url = "http://mybarter.net16.net/json_data_item__select_fromOthersBid.php";

            super.onPreExecute();
        }

        @Override
        protected String doInBackground(String... args) {
            String stringUserID;
            stringUserID = args[0];


            try {

                URL url = new URL(login_check_url);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                OutputStream outputStream = httpURLConnection.getOutputStream();

                try {

                    BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));
                    String data_string = URLEncoder.encode("user_id", "UTF-8") + "=" + URLEncoder.encode(stringUserID, "UTF-8");
                    bufferedWriter.write(data_string);
                    bufferedWriter.flush();
                    bufferedWriter.close();
                    outputStream.close();
                    InputStream inputStream = httpURLConnection.getInputStream();
                    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
                    StringBuilder stringBuilder = new StringBuilder();
                    while ((JSON_STRING4 = bufferedReader.readLine()) != null) {
                        stringBuilder.append(JSON_STRING4);
                    }
                    bufferedReader.close();
                    inputStream.close();
                    httpURLConnection.disconnect();
                    return stringBuilder.toString().trim();
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
            super.onPostExecute(result);
            json_string4 = result;
        }
    }

    class BackgroundTaskAcceptOrDecline extends AsyncTask<String, Void, String> {
        String login_check_url;

        @Override
        protected void onPreExecute() {
            login_check_url = stringURL;
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(String... args) {
            String stringDemand1, stringSupply1;
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
                    return "Data delete";
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
    class BackgroundTaskInactiveItems extends AsyncTask<String,Void,String> {
        String login_check_url;

        @Override
        protected void onPreExecute() {
            login_check_url = "http://mybarter.net16.net/inactive_adds_accept.php";
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
                    String data_string = URLEncoder.encode("suply", "UTF-8") + "=" + URLEncoder.encode(stringSupply1, "UTF-8") + "&" +
                            URLEncoder.encode("demand", "UTF-8") + "=" + URLEncoder.encode(stringDemand1, "UTF-8");
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