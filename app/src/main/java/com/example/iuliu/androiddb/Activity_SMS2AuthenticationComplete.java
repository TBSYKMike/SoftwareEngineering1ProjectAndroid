package com.example.iuliu.androiddb;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import java.util.HashMap;

/**
 * Created by Mike on 2016-05-17.
 */
public class Activity_SMS2AuthenticationComplete extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_authentication_complete);

        registerUser();

    }


//-------------------------------------- PHP mySQL codes start---------------------------------------------------------------------------------------------




    private static final String REGISTER_URL = "http://mybarter.net16.net/SMS_Authen.php";

    private void registerUser() {
        //   String name = editTextName.getText().toString().trim().toLowerCase();
        //   String username = editTextUsername.getText().toString().trim().toLowerCase();
        //   String password = editTextPassword.getText().toString().trim().toLowerCase();
        //   String email = editTextEmail.getText().toString().trim().toLowerCase();



        String userId = "9";
        String phoneNumber = "070-12345677899";

            register(userId, phoneNumber);

createToast("ID = "+userId+" -- phoneNumber = "+phoneNumber);


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





}
