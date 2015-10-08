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
 * Created by Usuario on 06/10/2015.
 */
public class ParseReceiver extends ParsePushBroadcastReceiver {


    public void onPushReceive(Context context, Intent intent){
        Intent in = null;
        try{
            String action = intent.getAction();

            JSONObject json = new JSONObject(intent.getExtras().getString("com.parse.data"));

            Iterator itr = json.keys();
            while (itr.hasNext()){
                String key = (String) itr.next();
                if (key.equals("invJuego"))
                    in = onGameInvitation(json, context);
                else if (key.equals("invEquipo"))
                    in = onTeamInvitation(json, context);
            }
        }catch (JSONException e){
            Log.e("JSON", "no se pudo parsear el JSON");
        }

        makeNotification(context,in);
    }


    public Intent onGameInvitation(JSONObject json, Context ctx) throws JSONException{
        String msg = "", fecha = "", equipo1 = "", equipo2 = "";

        Iterator itr = json.keys();
        while (itr.hasNext()) {
            String key = (String) itr.next();
            if (key.equals("MSG")){
                msg = json.getString(key);
            }else if (key.equals("FECHA")){
                fecha = json.getString(key);
            }else if (key.equals("EQUIPO1")){
                equipo1 = json.getString(key);
            }else if (key.equals("EQUIPO2")){
                equipo1 = json.getString(key);
            }
        }

        Bundle bn = new Bundle();
        bn.putString("MSG", msg);
        bn.putString("FECHA", fecha);
        bn.putString("EQUIPO1", equipo1);
        bn.putString("EQUIPO2", equipo2);

        Intent in = new Intent(ctx, InvitacionPartidoActivity.class);
        in.putExtras(bn);
        return in;
    }

    /**
     * Si el primer key del JSON es "invEquipo" este metodo se encarga de la notificacion y del
     * activity
     */
    public Intent onTeamInvitation(JSONObject json, Context context) throws JSONException{
        String msg = "", nombre = "", capitan = "";

        Iterator itr = json.keys();
        while (itr.hasNext()){
            String key = (String)itr.next();
            if (key.equals("NOMBRE"))
                nombre = json.getString(key);
            else if (key.equals("CAPITAN")){
                capitan = json.getString(key);
            }else if (key.equals("MSG"))
                msg = json.getString(key);
        }

        Bundle bn = new Bundle();
        bn.putString("NOMBRE", nombre);
        bn.putString("CAPITAN", capitan);
        bn.putString("MSG", msg);

        Intent in = new Intent(context, InvitacionEquipoActivity.class);
        return in;

    }

    public void makeNotification(Context ctx, Intent in){
        Bitmap icon = BitmapFactory.decodeResource(ctx.getResources(), R.drawable.ic_launcher);
        PendingIntent pi = PendingIntent.getActivity(ctx, 0, in, 0);

        Notification noti = new NotificationCompat.Builder(ctx)
                .setContentTitle("YO+10")
                .setContentText(in.getExtras().getString("MSG"))
                .setSmallIcon(R.drawable.ic_launcher)
                .setLargeIcon(icon)
                .setContentIntent(pi)
                .setAutoCancel(true)
                .build();

        NotificationManager nm = (NotificationManager)ctx.getSystemService
                (Context.NOTIFICATION_SERVICE);
        nm.notify(0, noti);
    }
}
