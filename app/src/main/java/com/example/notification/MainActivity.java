package com.example.notification;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {
public  static String CHANNEL_ID="CH1";
Button btnShow;
@RequiresApi(api=Build.VERSION_CODES.TIRAMISU)
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
       btnShow=findViewById(R.id.btnShow);
    Intent intent =new Intent(this,NotificationResponseActivity.class);
    PendingIntent pendingIntent =
         PendingIntent.getActivity(this,1,intent,PendingIntent.FLAG_IMMUTABLE);

    String bigText = "No sooner said than done.At the beginning of May,a delegation of 40 catholics from Amsterdam met Pope Francis and " +
            "presented him with a tulip created especially for him.\n"+
            "\n";

        Notification notification = new NotificationCompat.Builder(this,CHANNEL_ID).setContentTitle("Email Received")
                .setContentText("Email From University is Received,check and reply")
                .setSmallIcon(R.drawable.ic_launcher_background)
                .setStyle(new NotificationCompat.BigTextStyle().bigText(bigText))
                .setContentIntent(pendingIntent)
                .addAction(R.drawable.ic_launcher_background,"Reply ",pendingIntent)
                .addAction(R.drawable.ic_launcher_background,"Call ",pendingIntent)
                .setShowWhen(true).build();
        NotificationManager notificationManager = (NotificationManager)
                getSystemService(NOTIFICATION_SERVICE);
        if(Build.VERSION.SDK_INT>= Build.VERSION_CODES.O){
            NotificationChannel notificationChannel =new NotificationChannel(CHANNEL_ID,"NEWS",NotificationManager.IMPORTANCE_HIGH);
            notificationManager.createNotificationChannel(notificationChannel);
        }

        ActivityResultLauncher<String> permissionLauncher = registerForActivityResult(new ActivityResultContracts.RequestPermission(), new ActivityResultCallback<Boolean>() {
            @Override
            public void onActivityResult(Boolean o) {

            }
        });
        if(ContextCompat.checkSelfPermission(this, Manifest.permission.POST_NOTIFICATIONS)== PackageManager.PERMISSION_DENIED){
            permissionLauncher.launch(Manifest.permission.POST_NOTIFICATIONS);
        }
        btnShow.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("NotificationPermission")
            @Override
            public void onClick(View v) {
               notificationManager.notify(1,notification);
            }
        });

    }
}