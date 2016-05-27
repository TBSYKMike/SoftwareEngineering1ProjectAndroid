package com.example.iuliu.androiddb;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Process;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends Activity {
    Button B1,B2, B3;
    TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        B1 = (Button)findViewById(R.id.b1);
        B2 = (Button)findViewById(R.id.b2);
        textView = (TextView)findViewById(R.id.textView);
        ConnectivityManager connectivityManager = (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo =connectivityManager.getActiveNetworkInfo();
        if(networkInfo!=null&&networkInfo.isConnected())
        {
            textView.setVisibility(View.INVISIBLE);
        }
        else
        {

        }

    }

    public void addContact(View view)
    {
    startActivity(new Intent(this, AddInfo.class));
    }
    public void viewItems(View view)
    {
        startActivity(new Intent(this, TradingLists.class));
    }

    public final String SPARAD_DATA = Activity_Check_If_User_Is_Logged_In.SPARAD_DATA;
    public void buttonPressLogout(View view){

        SharedPreferences preferences = getSharedPreferences(SPARAD_DATA, MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("LoggedIn", "false");
        editor.putString("UserId", "0");
        editor.commit();

        startActivity(new Intent(MainActivity.this, Activity_Check_If_User_Is_Logged_In.class));

    }


    @Override
    public void onBackPressed() {
        finish();
    }

    @Override
    protected void onDestroy() {
        //android.os.Process.killProcess(android.os.Process.myPid());
        Process.killProcess(Process.myPid());
        super.onDestroy();

    }

}
