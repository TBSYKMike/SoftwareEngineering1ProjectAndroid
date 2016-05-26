package com.example.iuliu.androiddb;


/**
 * Created by Mike on 2016-05-10.
 */
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.SmsMessage;
import android.widget.Toast;

public class Activity_SMS1SMSBroadcastRecivier extends BroadcastReceiver
{
    @Override
    public void onReceive(Context context, Intent intent) {
        if (Activity_SMS_TempValues.getInstance().isCheckSMSOn()) {

            // Get Bundle object contained in the SMS intent passed in
            Bundle bundle = intent.getExtras();
            SmsMessage[] smsm = null;
            String sms_str = "";
            if (bundle != null) {

                // Get the SMS message
                Object[] pdus = (Object[]) bundle.get("pdus");
                smsm = new SmsMessage[pdus.length];
                for (int i = 0; i < smsm.length; i++) {

                    smsm[i] = SmsMessage.createFromPdu((byte[]) pdus[i]);

                    String phoneNbr = smsm[i].getOriginatingAddress();
                    String messsageBdy = smsm[i].getMessageBody().toString();
                    Activity_SMS_TempValues.getInstance().validateNumber(phoneNbr);
                    Activity_SMS_TempValues.getInstance().validateRandomNumber(messsageBdy);

                    sms_str += "Sent From: " + phoneNbr;
                    sms_str += "\r\nMessage: ";
                    sms_str += messsageBdy;
                    sms_str += "\r\n";
                }


                if (Activity_SMS_TempValues.getInstance().validationCleared()) {
                    // Start Application's  MainActivty activity
                    Intent smsIntent = new Intent(context, Activity_SMS2AuthenticationComplete.class);
                    smsIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    //smsIntent.putExtra("sms_str", sms_str);
                    context.startActivity(smsIntent);
                    Toast.makeText(context, "klar", Toast.LENGTH_LONG).show();
                    Activity_SMS_TempValues.getInstance().setCheckSMSOn(false);
                } else {

                    Toast.makeText(context, "Försök Igen", Toast.LENGTH_LONG).show();
                }


            }
        }
    }
}