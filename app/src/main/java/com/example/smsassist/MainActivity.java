package com.example.smsassist;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.getbase.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    static TextView textView1, textView2;
    static ArrayList<String> destinationNumbers;
    static ArrayList<String> sourceNumbers;
    FloatingActionButton sourceNumbersFAB, destinationNumbersFAB;
    FrameLayout container;
    //static String forwardNumber = "";
    //static String forwardNumber2 = "";
    boolean autoStart = false;
    boolean sms = false;
    SharedPreferences sharedpreferences;
    SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getSupportFragmentManager().beginTransaction().add(R.id.container, new SplashScreenOneFragment()).addToBackStack(null).commit();


        sourceNumbersFAB = findViewById(R.id.sourceNumbersFAB);
        destinationNumbersFAB = findViewById(R.id.destinationNumbersFAB);

        destinationNumbers = new ArrayList<>();
        sourceNumbers = new ArrayList<>();

        sharedpreferences = getPreferences(Context.MODE_PRIVATE);
        editor = sharedpreferences.edit();
        autoStart = sharedpreferences.getBoolean("autoStart",autoStart);
        //destinationNumbers = sharedpreferences.getStringSet("forwardNumbers",forwardNumbers);

        destinationNumbers.add("");
        destinationNumbers.add("");
        sourceNumbers.add("");
        sourceNumbers.add("");

        String manufacturer = android.os.Build.MANUFACTURER;
        if (!autoStart && ("xiaomi".equalsIgnoreCase(manufacturer)
                || "oppo".equalsIgnoreCase(manufacturer)
                || "vivo".equalsIgnoreCase(manufacturer)
                || "Letv".equalsIgnoreCase(manufacturer)
                || "Honor".equalsIgnoreCase(manufacturer)) ){
            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
            alertDialogBuilder.setNegativeButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    Intent intent = new Intent();
                    intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                    Uri uri = Uri.fromParts("package", getPackageName(), null);
                    intent.setData(uri);
                    startActivity(intent);
                    autoStart = true;
                    editor.putBoolean("autoStart", autoStart);
                    editor.commit();
                }
            });
            alertDialogBuilder.setCancelable(false);
            alertDialogBuilder.setTitle("AutoStart Permission Required");
            alertDialogBuilder.setMessage("This app requires AutoStart to function properly. Click OK to open settings and then permit auto start.");
            AlertDialog alertDialog = alertDialogBuilder.create();
            alertDialog.show();
        }

//        textView1 = findViewById(R.id.textView1);
//        textView2 = findViewById(R.id.textView2);
//        textView1.setText("SMSsist");
//        textView2.setText("to assist in statutory compliance by forwarding SMS for filling returns and other compliance. \n\n A User is granted a non-exclusive, non-transferable, non-sharable, revocable, limited license to use the app solely for personal, non-commercial use.");

        if(!sms && ((ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_SMS) == PackageManager.PERMISSION_DENIED
                || ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_SMS) != PackageManager.PERMISSION_GRANTED)
                &&  (ActivityCompat.checkSelfPermission(this, Manifest.permission.SEND_SMS) == PackageManager.PERMISSION_DENIED
                || ActivityCompat.checkSelfPermission(this, Manifest.permission.SEND_SMS) != PackageManager.PERMISSION_GRANTED)) ){
            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
            alertDialogBuilder.setNegativeButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    String[] permissions = {Manifest.permission.RECEIVE_SMS, Manifest.permission.READ_SMS, Manifest.permission.SEND_SMS};
                    ActivityCompat.requestPermissions(MainActivity.this, permissions, 1);
                    sms = true;
                }
            });
            alertDialogBuilder.setCancelable(false);
            alertDialogBuilder.setTitle("SMS Permission Required");
            alertDialogBuilder.setMessage("This app requires SMS permissions to function. Click OK and grant permission.");
            AlertDialog alertDialog = alertDialogBuilder.create();
            alertDialog.show();
        }

    }
}
