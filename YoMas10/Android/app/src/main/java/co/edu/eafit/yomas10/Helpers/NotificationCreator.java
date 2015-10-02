package co.edu.eafit.yomas10.Helpers;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.NotificationCompat;

import org.json.JSONObject;

import java.util.LinkedList;

import co.edu.eafit.yomas10.R;

/**
 * Created by Usuario on 01/10/2015.
 */

/**
 * Clase para crear notificaciones, y crear notificaciones en parse
 */
public class NotificationCreator {

    /**
     * Crea una notificacion que se mostrará al usuario
     *
     * @param ctx El contexto de donde se quiere mandar la notificacion.
     * @param msg El mensaje que se mostrara al usuario en la notificacion.
     * @param lauchActivity El intent del activity al que se quiere llegar
     *
     */
    public static void crearNotificacion(Context ctx, String msg, Intent lauchActivity){

        Bitmap icon = BitmapFactory.decodeResource(ctx.getResources(), R.drawable.ic_launcher);

        PendingIntent pi = PendingIntent.getActivity(ctx, (int)System.currentTimeMillis(), lauchActivity, 0);
        Notification not = new NotificationCompat.Builder(ctx)
                .setContentTitle("Yo+10")
                .setContentText(msg)
                .setSmallIcon(R.drawable.ic_launcher)
                .setLargeIcon(icon)
                .setContentIntent(pi)
                .setAutoCancel(true)
                .build();

        NotificationManager nm= (NotificationManager) ctx.getSystemService(Context.NOTIFICATION_SERVICE);
        nm.notify(0, not);
    }
    /*
    public static JSONObject createJSON(LinkedList<String>){
        return null;
    }
    */
    public static void sendParseNotification(){

    }
}
