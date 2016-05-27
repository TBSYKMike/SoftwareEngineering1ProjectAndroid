package com.example.iuliu.androiddb;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.util.HashMap;

/**
 * Created by Mike on 2016-05-17.
 */
public class Activity_SMS2AuthenticationComplete extends AppCompatActivity {


    Button buttonPressToContinue;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_authentication_complete);

        buttonPressToContinue = (Button) findViewById(R.id.buttonPressToContinue);
        buttonPressToContinue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Activity_SMS2AuthenticationComplete.this, MainActivity.class));
            }
        });
        registerUser();

    }


//-------------------------------------- PHP mySQL codes start---------------------------------------------------------------------------------------------




    private static final String REGISTER_URL = "http://mybarter.net16.net/SMS_Authen.php";

    private void registerUser() {
        saveLoadData();

        String userId = userID;
        String phoneNumber = userPhoneNumber;

        register(userId, phoneNumber);

        createToast("ID = " + userId + " -- phoneNumber = " + phoneNumber);

    }


    private void register(String userId, String phoneNr ) {
        class RegisterUser extends AsyncTask<String, Void, String> {
            ProgressDialog loading;
            Activity_SMS2_DBConnectionClass ruc = new Activity_SMS2_DBConnectionClass();


            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(Activity_SMS2AuthenticationComplete.this, "Please Wait",null, true, true);

            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                loading.dismiss();
                Toast.makeText(getApplicationContext(),s, Toast.LENGTH_LONG).show();

                startActivity(new Intent(Activity_SMS2AuthenticationComplete.this, MainActivity.class));
finish();
                createToast("You Are Now Logged In");
            }

            @Override
            protected String doInBackground(String... params) {

                HashMap<String, String> data = new HashMap<String,String>();
                data.put("userId",params[0]);
                data.put("phoneNr",params[1]);




                String result = ruc.sendPostRequest(REGISTER_URL,data);

                return  result;
            }
        }

        RegisterUser ru = new RegisterUser();
        ru.execute(userId, phoneNr);
    }





    public void createToast(String text) {
        Toast.makeText(getApplicationContext(), text, Toast.LENGTH_LONG).show();
    }




//-------------------------------------- PHP mySQL codes end---------------------------------------------------------------------------------------------


    public final String SPARAD_DATA = Activity_Check_If_User_Is_Logged_In.SPARAD_DATA;

    private String userID;
    private String userPhoneNumber;

    private void saveLoadData(){
        userID = Singleton.getInstance().getItemOwn_id();
        userPhoneNumber = Activity_SMS_TempValues.getInstance().getPhoneNumber();

        SharedPreferences preferences = getSharedPreferences(SPARAD_DATA, MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("LoggedIn", "true");
        editor.putString("UserId", userID);
        editor.commit();
    }





}
