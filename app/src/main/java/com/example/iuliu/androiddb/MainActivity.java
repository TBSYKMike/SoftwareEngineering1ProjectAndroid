package com.example.iuliu.androiddb;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
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
        B3 = (Button)findViewById(R.id.b3);
        textView = (TextView)findViewById(R.id.textView);
        ConnectivityManager connectivityManager = (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo =connectivityManager.getActiveNetworkInfo();
        if(networkInfo!=null&&networkInfo.isConnected())
        {
            textView.setVisibility(View.INVISIBLE);
        }
        else
        {
            B1.setEnabled(false);
            B2.setEnabled(false);
            B3.setEnabled(false);

        }

    }

    public void addContact(View view)
    {
    startActivity(new Intent(this, AddInfo.class));
    }
    public void viewItems(View view)
    {
        startActivity(new Intent(this, ViewItems.class));
    }

    public void testLogin(View view){
        startActivity(new Intent(MainActivity.this, LoginActivity.class));
    }

}
