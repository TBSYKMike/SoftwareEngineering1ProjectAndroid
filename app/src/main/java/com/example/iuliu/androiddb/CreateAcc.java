package com.example.iuliu.androiddb;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
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
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.ArrayList;
import java.util.List;


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

    String accPass;
    String encryptedAccPass;

    List<String> cities;
    ArrayAdapter<String> spinnerAdapter;

    EditText inputAccName;
    EditText inputPassword;
    EditText inputConPassword;
    EditText inputEmail;
    Spinner spinner;
    CheckBox checkBox;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_acc);

        inputAccName = (EditText) findViewById(R.id.editTextAccName);
        inputPassword = (EditText) findViewById(R.id.editTextPass);
        inputConPassword = (EditText) findViewById(R.id.editTextConPass);
        inputEmail = (EditText) findViewById(R.id.editTextEmail);
        spinner = (Spinner) findViewById(R.id.spinnerCity);
        checkBox = (CheckBox) findViewById(R.id.checkBoxTermsOfUse);

        spinner.setOnItemSelectedListener(this);

        cities = new ArrayList<>();
        cities.add("Helsingborg");
        cities.add("Kristianstad");
        cities.add("Malmo");

        spinnerAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, cities);
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(spinnerAdapter);

    }

    @Override
    public void onBackPressed() {
        finish();
    }

    public void createButton(View view) throws Exception {
        Pattern ps = Pattern.compile("^[a-zA-Z0-9 ]+$");
        Matcher ms = ps.matcher(inputAccName.getText().toString());
        boolean verifyName = ms.matches();
        accName = inputAccName.getText().toString();
        password = inputPassword.getText().toString();
        conPassword = inputConPassword.getText().toString();
        email = inputEmail.getText().toString();

        accPass = accName + " " + password;

        if(verifyName == true) {

        if(TextUtils.isEmpty(accName) || TextUtils.isEmpty(password) || TextUtils.isEmpty(conPassword) || TextUtils.isEmpty(email) || city == null) {
            Toast.makeText(CreateAcc.this, "Please fill in all fields!", Toast.LENGTH_LONG).show();
        }else{
            if(checkBox.isChecked()) {
                if (password.matches(conPassword)) {
                    encryptedAccPass = Kripto.encrypt(accPass);
                    BackgroundTask backgroundTask = new BackgroundTask();
                    backgroundTask.execute(accName, encryptedAccPass, email, stringCityId);
                } else {
                    showError();
                }
            }else{
                Toast.makeText(CreateAcc.this, "Please check the Terms of Use!", Toast.LENGTH_LONG).show();
            }
            }
        }else{
            Toast.makeText(CreateAcc.this, "Accountname can only contain alphabetic letters and numbers!", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        city = parent.getItemAtPosition(position).toString();

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
        ProgressDialog loading;

        @Override
        protected void onPreExecute(){
            super.onPreExecute();
            loading = ProgressDialog.show(CreateAcc.this, "Please Wait",null, true, true);
        }

        @Override
        protected void onPostExecute(String check){
            super.onPostExecute(check);
            loading.dismiss();

            if(check.contains("Success!")){
                Toast.makeText(CreateAcc.this, "Success!", Toast.LENGTH_LONG).show();
                startActivity(new Intent(CreateAcc.this, Login.class));
                finish();
            }else{
                Toast.makeText(CreateAcc.this, "Account name or email already exist!", Toast.LENGTH_LONG).show();
            }
        }
        @Override
        protected String doInBackground(String... params) {
            String accName = params[0];
            String encryptedAccPass = params[1];
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
                        URLEncoder.encode("encryptedAccPass", "UTF-8") + "=" + URLEncoder.encode(encryptedAccPass,"UTF-8") + "&" +
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
        finish();
    }

    public void readTermsButton(View view){
        startActivity(new Intent(CreateAcc.this, TermsOfService.class));
    }
}
