package co.edu.eafit.yomas10;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.NotificationCompat;
import android.util.Log;

import com.parse.ParsePushBroadcastReceiver;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Iterator;

/**
 * Created by Alejandro on 17/09/2015.
 */
public class ParseReceiver extends ParsePushBroadcastReceiver {

    private final String TAG = "Parse Notification";
    private String msg;
    private String date;
    private String nombre;

    public void onPushReceive(Context ctx, Intent intent){
        Log.i(TAG, "PUSH RECEIVED!!");
        try{
            String action = intent.getAction();
            String channel = intent.getExtras().getString("com.parse.Channel");
            JSONObject json = new JSONObject(intent.getExtras().getString("com.parse.Data"));

            Iterator itr = json.keys();
            while(itr.hasNext()){
                String key = (String) itr.next();
                if (key.equals("string")){
                    msg = json.getString(key);
                }else if (key.equals("date")){
                    date = json.getString(key);
                }else if (key.equals("nombre")){
                    nombre = json.getString(key);
                }
                //aca van otros if con la info del partido
            }

        }catch (JSONException e){
            Log.e(TAG, "JSONException: " + e.getMessage());
        }

        Bitmap icon = BitmapFactory.decodeResource(ctx.getResources(), R.drawable.push_ico2);

        Intent lauchActivity = new Intent(ctx, PartidoActivity.class);

        Bundle bn = new Bundle();
        bn.putString("NOMBRE", nombre);
        bn.putString("FECHA", date);

        lauchActivity.putExtras(bn);
        PendingIntent pi = PendingIntent.getActivity(ctx, (int)System.currentTimeMillis(), lauchActivity, 0);

        Notification not = new NotificationCompat.Builder(ctx)
                .setContentTitle("PUSH RECEIVED")
                .setContentText(msg)
                .setSmallIcon(R.drawable.push_ico2)
                .setLargeIcon(icon)
                .setContentIntent(pi)
                .setAutoCancel(true)
                .build();

        NotificationManager nm= (NotificationManager) ctx.getSystemService(Context.NOTIFICATION_SERVICE);
        nm.notify(0, not);
    }
}
