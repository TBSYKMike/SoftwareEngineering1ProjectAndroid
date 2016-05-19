package com.example.iuliu.androiddb;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.net.URLEncoder;

/**
 * Created by Jocke on 2016-05-12.
 */
public class CreateAcc extends AppCompatActivity {

    String check;
    String line;
    String accName;
    String password;
    String conPassword;
    String phoneNr;
    String email;
    String create_acc_url = "http://mybarter.net16.net/create_account.php";

    EditText inputAccName;
    EditText inputPassword;
    EditText inputConPassword;
    EditText inputPhoneNr;
    EditText inputEmail;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_acc);

        inputAccName = (EditText) findViewById(R.id.editTextAccName);
        inputPassword = (EditText) findViewById(R.id.editTextPass);
        inputConPassword = (EditText) findViewById(R.id.editTextConPass);
        inputPhoneNr = (EditText) findViewById(R.id.editTextPhoneNr);
        inputEmail = (EditText) findViewById(R.id.editTextEmail);
    }

    public void createButton(View view) {
        accName = inputAccName.getText().toString();
        password = inputPassword.getText().toString();
        conPassword = inputConPassword.getText().toString();
        phoneNr = inputPhoneNr.getText().toString();
        email = inputEmail.getText().toString();

        if(TextUtils.isEmpty(accName) || TextUtils.isEmpty(password) || TextUtils.isEmpty(conPassword) || TextUtils.isEmpty(phoneNr) || TextUtils.isEmpty(email)) {
            Toast.makeText(CreateAcc.this, "Please fill in all fields!", Toast.LENGTH_LONG).show();
        }else{
            if(password.matches(conPassword)) {
                BackgroundTask backgroundTask = new BackgroundTask();
                backgroundTask.execute(accName, phoneNr, email);
            }else{
                showError();
            }

        }
    }

    class BackgroundTask extends AsyncTask<String,Void,String> {

        @Override
        protected void onPreExecute(){
            super.onPreExecute();
        }

        @Override
        protected void onPostExecute(String check){
            super.onPostExecute(check);

            if(check.contains("Success!")){
                Toast.makeText(CreateAcc.this, "Success! Authentication code has been sent to your phone!", Toast.LENGTH_LONG).show();
                startActivity(new Intent(CreateAcc.this, MainActivity.class));
            }else{
                Toast.makeText(CreateAcc.this, "Account name, phone nummber or email already exist!", Toast.LENGTH_LONG).show();
            }
        }
        @Override
        protected String doInBackground(String... params) {
            String accName = params[0];
            String phoneNr = params[1];
            String email = params[2];

            try{
                URL url = new URL(create_acc_url);
                HttpURLConnection httpURLConnection = (HttpURLConnection)url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                httpURLConnection.setDoInput(true);
                OutputStream outputStream = httpURLConnection.getOutputStream();

                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));
                String data = URLEncoder.encode("accName", "UTF-8") + "=" + URLEncoder.encode(accName,"UTF-8") + "&" +
                        URLEncoder.encode("phoneNr", "UTF-8") + "=" + URLEncoder.encode(phoneNr,"UTF-8") + "&" +
                        URLEncoder.encode("email", "UTF-8") + "=" + URLEncoder.encode(email, "UTF-8");
                bufferedWriter.write(data);

                bufferedWriter.flush();
                bufferedWriter.close();
                outputStream.close();

                InputStream inputStream = httpURLConnection.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream,"UTF-8"));

                check = "";
                line = "";

                while((line = bufferedReader.readLine())!= null) {
                    check += line;
                }
                bufferedReader.close();
                inputStream.close();
                httpURLConnection.disconnect();
                return check;
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (ProtocolException e) {
                e.printStackTrace();
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }
    }

    private void showError(){
        inputConPassword.setError("Passwords does not match!");
    }

    public void cancelButton(View view){
        startActivity(new Intent(CreateAcc.this, Login.class));
    }
}
