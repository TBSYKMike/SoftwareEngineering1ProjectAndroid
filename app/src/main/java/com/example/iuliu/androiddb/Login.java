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


public class Login extends AppCompatActivity {

    String check;
    String line;
    String accName;
    String password;
    String login_check_url = "http://mybarter.net16.net/login_check.php";

    EditText inputAccName;
    EditText inputPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        inputAccName = (EditText) findViewById(R.id.editTextAcc);
        inputPassword = (EditText) findViewById(R.id.editTextPass);

    }

    public void loginButton(View view){
        accName = inputAccName.getText().toString();
        password = inputPassword.getText().toString();

        if(TextUtils.isEmpty(accName) || TextUtils.isEmpty(password)){
            Toast.makeText(Login.this, "Please fill in all fields!", Toast.LENGTH_LONG).show();
        }else {
            BackgroundTask backgroundTask = new BackgroundTask();
            backgroundTask.execute(accName, password);
        }
    }

        class BackgroundTask extends AsyncTask<String,Void,String>{

            @Override
            protected void onPreExecute(){
                super.onPreExecute();
            }

            @Override
            protected void onPostExecute(String check){
                super.onPostExecute(check);

                if(check.contains("Success!")){
                    Toast.makeText(Login.this, "Success!", Toast.LENGTH_LONG).show();
                    startActivity(new Intent(Login.this, MainActivity.class));
                }else{
                    Toast.makeText(Login.this, "User details does not match an existing account!", Toast.LENGTH_LONG).show();
                }
            }
            @Override
            protected String doInBackground(String... params) {
                String accName = params[0];
                String password = params[1];

                try{
                    URL url = new URL(login_check_url);
                    HttpURLConnection httpURLConnection = (HttpURLConnection)url.openConnection();
                    httpURLConnection.setRequestMethod("POST");
                    httpURLConnection.setDoOutput(true);
                    httpURLConnection.setDoInput(true);
                    OutputStream outputStream = httpURLConnection.getOutputStream();

                    BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));
                    String data = URLEncoder.encode("accName", "UTF-8") + "=" + URLEncoder.encode(accName,"UTF-8") + "&" + URLEncoder.encode("password","UTF-8")
                            + "=" + URLEncoder.encode(password,"UTF-8");
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


    public void createAccButton(View view){
        startActivity(new Intent(Login.this, CreateAcc.class));
    }
}
