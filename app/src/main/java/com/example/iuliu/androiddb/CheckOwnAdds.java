package com.example.iuliu.androiddb;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
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
import java.util.Random;

public class CheckOwnAdds extends AppCompatActivity {
    private RadioGroup radioGroup1;
    private RadioButton radio1,radio2,radio;
    String JSON_STRING3,json_string3;
    String JSON_STRING4,json_string4;
    String stringItemId;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_own_adds);
        stringItemId=Singleton.getInstance().getItem_id();
        getJSON3();
        getJSON4();
        this.populate(json_string4);

    }

    public void getJSON3() {
        BackgroundTask3 backgroundTask =new BackgroundTask3();
        backgroundTask.execute(stringItemId);

    }
    public void getJSON4() {
       BackgroundTask4 backgroundTask4 =new BackgroundTask4();
       backgroundTask4.execute(stringItemId);

    }
    public void onRadioButtonClicked(View v) {
        //require to import the RadioButton class
      //  RadioButton rb1 = (RadioButton) findViewById(R.id.radio1);
     //   RadioButton rb2 = (RadioButton) findViewById(R.id.radio2);


        //is the current radio button now checked?
        boolean checked = ((RadioButton) v).isChecked();

        //now check which radio button is selected
        //android switch statement
        switch (v.getId()) {

            case R.id.radio2:
                if (checked)

                this.populate(json_string3);

                    break;
            case R.id.radio1:
                if (checked)

                    this.populate(json_string4);

                break;
            case R.id.radio:
                break;
        }
    }
   /* public void onRadioButtonClicked(View v) {
        radioGroup1 = (RadioGroup) findViewById(R.id.radioGroup1);
        //  btnDisplay = (Button) findViewById(R.id.btnDisplay);
        radio1 = (RadioButton) findViewById(R.id.radio1);
        radio2 = (RadioButton) findViewById(R.id.radio2);
        radioGroup1.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.radio1:
                        // do operations specific to this selection
                        string = "http://mybarter.net16.net/delete_accept_barter.php";
                        Toast.makeText(CheckOwnAdds.this,
                                "1", Toast.LENGTH_SHORT).show();
                        String itemId = Singleton.getInstance().getItem_id();
                        // GetJSON object= new GetJSON();
                        //  String stringJson=object.getJSON3(itemId);

                        ownAdd.populateList();


                        break;

                    case R.id.radio2:
                        string = "http://mybarter.net16.net/delete_accept_barter.php";
                        Toast.makeText(CheckOwnAdds.this,
                                "2", Toast.LENGTH_LONG).show();
                        // do operations specific to this selection
                        break;

                }


            }
        });

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


    }*/
    public void populate(String ss){
        ArrayList<Adds> arrayUsers;

        AddsAdapter addsAdapter;
        ListView listView;
        String json_string,json_string2;
        JSONObject jsonObject;
        JSONArray jsonArray;
        arrayUsers=new ArrayList<Adds>();

        listView=(ListView)findViewById(R.id.listView4);
        addsAdapter =new AddsAdapter(this,R.layout.row_layout,arrayUsers);

        // ImageView pictureView=(ImageView)findViewById(R.id.picture_random);

        listView.setAdapter(addsAdapter);
        json_string2=ss;
        try {
            //  ArrayList<Adds> listData = new ArrayList<>();


            jsonObject=new JSONObject(json_string2);
            jsonArray=jsonObject.getJSONArray("server_response");
            int count=0;
            String item_id,item_name,item_info,item_picture_small,item_picture_large,item_condition,item_date,item_status,item_visit_count,item_winner_userID,item_user_userID,accountName;
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
                Adds user=new Adds(item_id,item_name,item_info,item_picture_small,item_picture_large,item_condition,item_date,item_status,item_visit_count,item_winner_userID,item_user_userID,accountName);
                addsAdapter.add(user);
                count++;
            }


        } catch (Exception e) {
            e.printStackTrace();

        }


    }

    class BackgroundTask3 extends AsyncTask<String,Void,String> {
        String login_check_url;

        @Override
        protected void onPreExecute() {
            login_check_url ="http://mybarter.net16.net/json_data_item__select_to_OthersBid.php" ;
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
                    while((JSON_STRING3 = bufferedReader.readLine())!= null)
                    {
                        stringBuilder.append(JSON_STRING3);
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
            super.onPostExecute(result);
            json_string3=result;
        }
    }
    class BackgroundTask4 extends AsyncTask<String,Void,String> {
        String login_check_url;

        @Override
        protected void onPreExecute() {
            login_check_url ="http://mybarter.net16.net/json_data_item__select_fromOthersBid.php" ;

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
                    while((JSON_STRING4 = bufferedReader.readLine())!= null)
                    {
                        stringBuilder.append(JSON_STRING4);
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
            super.onPostExecute(result);
            json_string4=result;
        }
    }


}
