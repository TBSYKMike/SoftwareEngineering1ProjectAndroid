package com.example.iuliu.androiddb;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by Mike on 2016-05-24.
 */
public class Activity_Check_If_User_Is_Logged_In extends AppCompatActivity {


    public static final String SPARAD_DATA = "sparadData";



    @Override
    public void onCreate(Bundle savedInstanceState, PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);
        setContentView(R.layout.activity_check_if_user_is_logged_in);





    }


    @Override
    protected void onResume() {

        super.onResume();

//atEditText.setText("ewette");
        SharedPreferences preferences = getSharedPreferences(SPARAD_DATA, MODE_PRIVATE);
        String sparadText1 = preferences.getString("Title", "true");
        String sparadText2 = preferences.getString("Name", "no Name");



        if(sparadText1.contains("true")) {
            startActivity(new Intent(Activity_Check_If_User_Is_Logged_In.this, MainActivity.class));
        }else{
            startActivity(new Intent(Activity_Check_If_User_Is_Logged_In.this, Login.class));
        }
    }


    @Override
    public void onPause() {
        super.onPause();


        SharedPreferences preferences = getSharedPreferences(SPARAD_DATA, MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("Title", "false");
        editor.putString("Name", "true");
        editor.commit();


    }




}
