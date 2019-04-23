package com.gru.cajaaplicacionestics.service;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.os.Vibrator;
import android.renderscript.BaseObj;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.gru.cajaaplicacionestics.R;
import com.gru.cajaaplicacionestics.view.notificaciones.NotificacionesActivity;

import org.json.JSONException;
import org.json.JSONObject;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

public class MyFirebaseMessagingService extends FirebaseMessagingService
{
    String type="";
    NotificationManager notificationManager;
    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);
        /*if(remoteMessage.getData().size() >0){
            type = "json";
            sendMyNotification(remoteMessage.getData().toString());
        }*/
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            configurarCanales();
        }
        if(remoteMessage.getNotification() != null){
            type = "message";
            sendMyNotification(remoteMessage.getNotification().getBody(),remoteMessage.getNotification().getTitle());
        }
        //Log.e("click",remoteMessage.getNotification().getClickAction());

        notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            configurarCanales();
        }

    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void configurarCanales(){
        /*CharSequence adminChannelName = "";
        String adminChannelDescription = "";

        NotificationChannel adminChannel;
        adminChannel = new NotificationChannel("informacion", adminChannelName, NotificationManager.IMPORTANCE_LOW);
        adminChannel.setDescription(adminChannelDescription);
        adminChannel.enableLights(true);
        adminChannel.setLightColor(Color.RED);
        adminChannel.enableVibration(true);
        if (notificationManager != null) {
            notificationManager.createNotificationChannel(adminChannel);
        }*/
        CharSequence name = "name";
        String description = "description";
        int importance = NotificationManager.IMPORTANCE_DEFAULT;
        NotificationChannel channel = new NotificationChannel("id", name, importance);
        channel.setDescription(description);
        // Register the channel with the system; you can't change the importance
        // or other notification behaviors after this
        NotificationManager notificationManager = getSystemService(NotificationManager.class);
        notificationManager.createNotificationChannel(channel);
    }


    private void sendMyNotification(String body, String titulo)
    {
        String msg="", title="";
        long notificatioId = System.currentTimeMillis();

        if(type.equals("json")){
            try{
                JSONObject jsonObject = new JSONObject(body);
                Log.e("json",jsonObject.toString());
                title = jsonObject.getString("title");
                msg = jsonObject.getString("body");
            }catch (JSONException e){
                Log.e("exc",e.toString());}
        }
        else if(type.equals("message")){
            msg = body;
            title = titulo;
        }

        String url = "http://www.igualdadycalidadcba.gov.ar/SIPEC-CBA/capacitacion-v2/capacitacion.php?opt=estatal";
        Intent intent = new Intent(this, NotificacionesActivity.class);
        Uri uri = Uri.parse(url);
        //Intent intent = new Intent(Intent.ACTION_VIEW,uri);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, (int) (Math.random() * 100), intent, 0);

        int currentapiVersion = android.os.Build.VERSION.SDK_INT;
        if (currentapiVersion >= android.os.Build.VERSION_CODES.LOLLIPOP){
            currentapiVersion = R.drawable.logo;
        } else{
            currentapiVersion = R.drawable.logo;
        }

        Uri soundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        Notification.Builder notificationBuilder = new Notification.Builder(this)
                .setContentTitle(title)
                .setContentText(msg)
                .setStyle(new Notification.BigTextStyle().bigText(msg))
                .setSmallIcon(R.drawable.logo_blanco)
                .setAutoCancel(true)
                .setPriority(Notification.PRIORITY_HIGH)
                .setDefaults(Notification.FLAG_AUTO_CANCEL | Notification.DEFAULT_LIGHTS | Notification.DEFAULT_VIBRATE | Notification.DEFAULT_SOUND)
                .setSound(soundUri)
                .setSmallIcon(currentapiVersion)
                .setContentIntent(pendingIntent);
        //NotificationManager manager = (NotificationManager)getSystemService(Context.NOTIFICATION_SERVICE);
        NotificationManagerCompat manager = NotificationManagerCompat.from(this);
        manager.notify((int) notificatioId, notificationBuilder.build());
    }

    @Override
    public void onNewToken(String token) {
        super.onNewToken(token);
        Log.e("new token", token);
    }
}
