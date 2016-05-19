package com.example.iuliu.androiddb;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
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
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Jocke on 2016-05-12.
 */
public class CreateAcc extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    String check;
    String line;
    String accName;
    String password;
    String conPassword;
    String email;
    String city;
    int cityId;
    String stringCityId;
    String create_acc_url = "http://mybarter.net16.net/create_account.php";

    List<String> cities;
    ArrayAdapter<String> spinnerAdapter;

    EditText inputAccName;
    EditText inputPassword;
    EditText inputConPassword;
    EditText inputEmail;
    Spinner spinner;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_acc);

        inputAccName = (EditText) findViewById(R.id.editTextAccName);
        inputPassword = (EditText) findViewById(R.id.editTextPass);
        inputConPassword = (EditText) findViewById(R.id.editTextConPass);
        inputEmail = (EditText) findViewById(R.id.editTextEmail);
        spinner = (Spinner) findViewById(R.id.spinnerCity);

        spinner.setOnItemSelectedListener(this);

        cities = new ArrayList<>();
        cities.add("Helsingborg");
        cities.add("Kristianstad");
        cities.add("Malmo");

        spinnerAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, cities);
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(spinnerAdapter);
        spinner.setPrompt("Select City");

    }

    public void createButton(View view) {
        accName = inputAccName.getText().toString();
        password = inputPassword.getText().toString();
        conPassword = inputConPassword.getText().toString();
        email = inputEmail.getText().toString();

        if(TextUtils.isEmpty(accName) || TextUtils.isEmpty(password) || TextUtils.isEmpty(conPassword) || TextUtils.isEmpty(email) || city == null) {
            Toast.makeText(CreateAcc.this, "Please fill in all fields!", Toast.LENGTH_LONG).show();
        }else{
            if(password.matches(conPassword)) {
                BackgroundTask backgroundTask = new BackgroundTask();
                backgroundTask.execute(accName,password, email, stringCityId);
            }else{
                showError();
            }

        }
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        city = parent.getItemAtPosition(position).toString();
        Toast.makeText(parent.getContext(), "Selected: " + city, Toast.LENGTH_LONG).show();

        if(city.matches("Helsingborg")){
            cityId = 1;
            stringCityId = Integer.toString(cityId);
        }else if(city.matches("Kristianstad")){
            cityId = 2;
            stringCityId = Integer.toString(cityId);
        }else if(city.matches("Malmo")){
            cityId = 3;
            stringCityId = Integer.toString(cityId);
        }


    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

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
                Toast.makeText(CreateAcc.this, "Your account has been successfully registered!", Toast.LENGTH_LONG).show();
                startActivity(new Intent(CreateAcc.this, MainActivity.class));
            }else{
                Toast.makeText(CreateAcc.this, "Account name or email already exist!", Toast.LENGTH_LONG).show();
            }
        }
        @Override
        protected String doInBackground(String... params) {
            String accName = params[0];
            String password = params[1];
            String email = params[2];
            String city = params[3];

            try{
                URL url = new URL(create_acc_url);
                HttpURLConnection httpURLConnection = (HttpURLConnection)url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                httpURLConnection.setDoInput(true);
                OutputStream outputStream = httpURLConnection.getOutputStream();

                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));
                String data = URLEncoder.encode("accName", "UTF-8") + "=" + URLEncoder.encode(accName,"UTF-8") + "&" +
                        URLEncoder.encode("password", "UTF-8") + "=" + URLEncoder.encode(password,"UTF-8") + "&" +
                        URLEncoder.encode("email", "UTF-8") + "=" + URLEncoder.encode(email,"UTF-8") + "&" +
                        URLEncoder.encode("city", "UTF-8") + "=" + URLEncoder.encode(city, "UTF-8");
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
