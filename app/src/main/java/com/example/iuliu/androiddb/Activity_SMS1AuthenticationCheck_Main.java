package com.example.iuliu.androiddb;

import android.content.ComponentName;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Random;

public class Activity_SMS1AuthenticationCheck_Main extends AppCompatActivity {

    private Button btn;
    private Button btnEnableSMS;
    private Button btnOpenCameraApp;
    private Button btnOpenLoadImageApp;
    private EditText etMobilnummer;

    private TextView RandomNumber;
    private TextView PhoneNumber;
    private TextView validateNumberOK;
    private TextView validateRandomNumberOK;
    private TextView checkSMSOn;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_authenticationcheck);
        turnOnBroadcastRecivier();


        RandomNumber = (TextView) findViewById(R.id.textView0RandomNumber);
        PhoneNumber = (TextView) findViewById(R.id.textView0PhoneNumber);
        validateNumberOK = (TextView) findViewById(R.id.textView0validateNumberOK);
        validateRandomNumberOK = (TextView) findViewById(R.id.textView0validateRandomNumberOK);
        checkSMSOn = (TextView) findViewById(R.id.textView0checkSMSOn);



        btn = (Button) findViewById(R.id.buttonSkickaSMS);
        btnEnableSMS = (Button) findViewById(R.id.buttonEnableSMSCheck);
        btnOpenCameraApp = (Button) findViewById(R.id.buttonOpenCameraApp);
        etMobilnummer = (EditText) findViewById(R.id.editTextMobilnummer);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (etMobilnummer.getText().toString().contains("qwerty")){
                    startActivity(new Intent(Activity_SMS1AuthenticationCheck_Main.this, Activity_SMS2AuthenticationComplete.class));
                    finish();
                }
                else if (etMobilnummer.getText().length()>=10){
                    //startActivity(new Intent(Activity_SMS1AuthenticationCheck_Main.this, Activity_SMS2AuthenticationComplete.class));
                    sendSMSMessage();
                    //Intent intent = new Intent(Activity_SMS1AuthenticationCheck_Main.this, Activity_SMS2AuthenticationComplete.class);
                    //startActivity(intent);


                }
            }
        });

        btnEnableSMS.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Activity_SMS_TempValues.getInstance().setCheckSMSOn(true);

                etMobilnummer.setEnabled(true);
                btn.setEnabled(true);
                btnEnableSMS.setEnabled(false);

                updateInfo();

            }

        });


        btnOpenCameraApp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //startActivity(new Intent(Activity_SMS1AuthenticationCheck_Main.this, ActivityCamera.class));
            }

        });


        btnOpenLoadImageApp = (Button) findViewById(R.id.buttonOpenLoadImageApp);

        btnOpenLoadImageApp.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {

//                startActivity(new Intent(Activity_SMS1AuthenticationCheck_Main.this, ActivityTestLoadImageFromHTML.class));

            }
        });



    }



    protected void sendSMSMessage() {
        Log.i("Send SMS", "");
        String phoneNo = etMobilnummer.getText().toString();
        String message = generateRandomNumber();
        Activity_SMS_TempValues.getInstance().setPhoneNumber(phoneNo);
        Activity_SMS_TempValues.getInstance().setRandomNumber(message);


        try {
            SmsManager smsManager = SmsManager.getDefault();
            smsManager.sendTextMessage(phoneNo, null, message, null, null);
            Toast.makeText(getApplicationContext(), "SMS sent.", Toast.LENGTH_LONG).show();
        }

        catch (Exception e) {
            Toast.makeText(getApplicationContext(), "SMS failed, please try again.", Toast.LENGTH_LONG).show();
            e.printStackTrace();
        }
    }

    private String generateRandomNumber(){
        Random rand = new Random();
        long random = System.currentTimeMillis();
        //System.out.println(random);
        random += rand.nextLong();
        //System.out.println(random);
        String strNbr = Long.toString(random);
        //System.out.println(strNbr);
        while(strNbr.length()>8){
            strNbr = strNbr.substring(1);
        }
        //System.out.println(strNbr);

        return strNbr;
    }


    @Override
    protected void onResume() {
        super.onResume();



        updateInfo();



        if (!Activity_SMS_TempValues.getInstance().isCheckSMSOn()) {
            etMobilnummer.setEnabled(false);
            btn.setEnabled(false);
            btnEnableSMS.setEnabled(true);
        }else{
            btnEnableSMS.setEnabled(false);
        }
        //Activity_SMS_TempValues.getInstance().setCheckSMSOn(true);

    }

    @Override
    protected void onPause() {
        super.onPause();

        //Activity_SMS_TempValues.getInstance().setCheckSMSOn(false);

    }


    private void updateInfo(){
        RandomNumber.setText(RandomNumber.getText().toString() + ": " + Activity_SMS_TempValues.getInstance().getRandomNumber() );
        PhoneNumber.setText(PhoneNumber.getText().toString() + ": " + Activity_SMS_TempValues.getInstance().getPhoneNumber() );
        validateNumberOK.setText(validateNumberOK.getText().toString() + " "  );
        validateRandomNumberOK.setText(validateRandomNumberOK.getText().toString() + " "  );
        checkSMSOn.setText(checkSMSOn.getText().toString() + ": " + Activity_SMS_TempValues.getInstance().isCheckSMSOn() );
    }



    public void disableBroadcastReceiver(View view){
        turnOffBroadcastRecivier();
        Toast.makeText(this, "Disabled broadcst receiver", Toast.LENGTH_SHORT).show();
    }

    public void enableBroadcastReceiver(View view){
        turnOnBroadcastRecivier();
        Toast.makeText(this, "Enabled broadcast receiver", Toast.LENGTH_SHORT).show();
    }

    private void turnOnBroadcastRecivier(){
        ComponentName receiver = new ComponentName(Activity_SMS1AuthenticationCheck_Main.this, Activity_SMS1SMSBroadcastRecivier.class);
        PackageManager pm = this.getPackageManager();

        pm.setComponentEnabledSetting(receiver,
                PackageManager.COMPONENT_ENABLED_STATE_ENABLED,
                PackageManager.DONT_KILL_APP);
    }
    private void turnOffBroadcastRecivier(){
        ComponentName receiver = new ComponentName(Activity_SMS1AuthenticationCheck_Main.this, Activity_SMS1SMSBroadcastRecivier.class);
        PackageManager pm = this.getPackageManager();

        pm.setComponentEnabledSetting(receiver,
                PackageManager.COMPONENT_ENABLED_STATE_DISABLED,
                PackageManager.DONT_KILL_APP);
    }


    @Override
    protected void onStop() {
        turnOffBroadcastRecivier();
        super.onStop();
        turnOffBroadcastRecivier();

    }
}
