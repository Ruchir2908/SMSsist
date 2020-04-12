package com.example.smsassist;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.icu.text.SimpleDateFormat;
import android.os.Build;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.telephony.SmsMessage;
import android.text.format.DateFormat;
import android.util.Log;
import android.widget.AbsListView;
import android.widget.Toast;

import androidx.core.app.NotificationCompat;

import java.sql.Time;
import java.util.ArrayList;
import java.util.Date;

public class MyReceiver extends BroadcastReceiver {

    public static String convertDate(String dateInMilliseconds,String dateFormat) {
        return DateFormat.format(dateFormat, Long.parseLong(dateInMilliseconds)).toString();
    }

    @Override
    public void onReceive(Context context, Intent intent) {

        SmsManager smsManager = SmsManager.getDefault();
        String dateTime = "";
        String[] timeStamp = {};
        String finalMsg = "";
        String sender = "";
        Long time;
        ArrayList<String> forwardMessageBody;

        if(intent.getAction().equals("android.provider.Telephony.SMS_RECEIVED")){
            ArrayList<String> messageBody = new ArrayList<>();
            forwardMessageBody = new ArrayList<>();
            Bundle data = intent.getExtras();
            Object[] pdus = (Object[]) data.get("pdus");
            for (int i = 0; i < pdus.length; i++) {
                SmsMessage smsMessage = SmsMessage.createFromPdu((byte[]) pdus[i]);
                sender = smsMessage.getDisplayOriginatingAddress();
                messageBody.add(smsMessage.getDisplayMessageBody());
                time = smsMessage.getTimestampMillis();
                dateTime = convertDate(time.toString(),"dd/MM/yyyy hh:mm:ss");
                timeStamp = dateTime.split(" ");
            }

            if(sender.contains("GSTIND")
                    || sender.contains("ITDEPT")
                    || sender.contains("ITDCPC")
                    || sender.contains("ITDEFL")
                    || sender.contains("GOIMCA")
                    || sender.contains("DVATDL")
                    || sender.contains("TDSCPC")
                    || sender.contains("CLRTAX") ){
                for(int i=0;i<messageBody.size();i++) {
                    if(i==messageBody.size()-1 && messageBody.get(i).length()<=145){
                        String last = messageBody.remove(i);
                        last += "SMSsist:"+timeStamp[1].substring(0,5);
                        //smsManager.sendTextMessage(MainActivity.forwardNumber.substring(3), null, last, null, null);
                        smsManager.sendTextMessage(MainActivity.forwardNumber2.substring(3), null, last, null, null);
                    }else{
                        //smsManager.sendTextMessage(MainActivity.forwardNumber.substring(3), null, messageBody.get(i), null, null);
                        smsManager.sendTextMessage(MainActivity.forwardNumber2.substring(3), null, messageBody.get(i), null, null);
                    }

                    }
            }

        }
    }
}
