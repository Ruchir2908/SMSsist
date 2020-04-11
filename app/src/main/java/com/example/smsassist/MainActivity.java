package com.example.smsassist;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.telephony.SmsMessage;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    static TextView tv1, tv2;
    //BroadcastReceiver MyReceiver;
    static ArrayList<String> sender;
    static ArrayList<String> messageBody;
    static String forwardNumber = "+";
    static int count = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tv1 = findViewById(R.id.tv1);
        tv2 = findViewById(R.id.tv2);
        tv1.setText("Hello");
        tv2.setText("HELLOOOO");

        sender = new ArrayList<>();
        messageBody = new ArrayList<>();

        if((ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_SMS) == PackageManager.PERMISSION_DENIED || ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_SMS) != PackageManager.PERMISSION_GRANTED)  &&  (ActivityCompat.checkSelfPermission(this, Manifest.permission.SEND_SMS) == PackageManager.PERMISSION_DENIED || ActivityCompat.checkSelfPermission(this, Manifest.permission.SEND_SMS) != PackageManager.PERMISSION_GRANTED)){
            Toast.makeText(this, "Permission", Toast.LENGTH_LONG).show();
            String[] permissions = {Manifest.permission.RECEIVE_SMS, Manifest.permission.READ_SMS, Manifest.permission.SEND_SMS};
            ActivityCompat.requestPermissions(this, permissions, 1);
        }else{
            Toast.makeText(this, "Not granted", Toast.LENGTH_SHORT).show();
        }

//        registerReceiver(MyReceiver,new IntentFilter("MyReceiver"));
//
//        BroadcastReceiver myReceiver =  new BroadcastReceiver() {
//            @Override
//            public void onReceive(Context context, Intent intent) {
//                Bundle bundle = intent.getExtras();
//                String sender = bundle.getString("Sender");
//                String message = bundle.getString("Message");
//                Log.i("test",sender + " " + message);
//                tv1.setText(sender);
//                tv2.setText(message);
//            }
//        };



    }
}
