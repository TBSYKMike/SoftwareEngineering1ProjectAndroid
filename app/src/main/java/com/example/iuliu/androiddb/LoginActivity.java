package com.example.iuliu.androiddb;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;


public class LoginActivity extends AppCompatActivity {

    EditText inputAccountName;
    EditText inputPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_activity);

    }

    public void loginButton(View view){
        //asd
    }

    public void createAccButton(View view){

    }
}
