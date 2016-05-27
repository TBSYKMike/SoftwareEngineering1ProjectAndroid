package com.example.iuliu.androiddb;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.method.ScrollingMovementMethod;
import android.widget.TextView;

/**
 * Created by Jocke on 2016-05-27.
 */
public class TermsOfService extends AppCompatActivity {

    TextView textViewText;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        textViewText = (TextView) findViewById(R.id.textViewText);
        setContentView(R.layout.activity_terms_of_service);
    }

    @Override
    public void onBackPressed() {
        finish();
    }

    @Override
    protected void onResume(){
        super.onResume();
    }

}
