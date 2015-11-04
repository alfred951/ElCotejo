package co.edu.eafit.yomas10.Util;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.RingtoneManager;
import android.os.Bundle;
import android.support.v7.app.NotificationCompat;
import android.util.Log;

import com.parse.ParsePushBroadcastReceiver;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Iterator;

import co.edu.eafit.yomas10.Equipos.Equipo;
import co.edu.eafit.yomas10.Equipos.InvitacionEquipoActivity;
import co.edu.eafit.yomas10.Jugador.Jugador;
import co.edu.eafit.yomas10.MainActivity;
import co.edu.eafit.yomas10.Partidos.Casual.PartidoCasual;
import co.edu.eafit.yomas10.Partidos.Casual.InvitacionPartidoCasualActivity;
import co.edu.eafit.yomas10.Partidos.Equipos.PartidoPorEquipos;
import co.edu.eafit.yomas10.Partidos.Equipos.InvitacionPartidoEquiposActivity;
import co.edu.eafit.yomas10.R;

/**
 * Created by Usuario on 06/10/2015.
 */
public class ParseReceiver extends ParsePushBroadcastReceiver {


    public void onPushReceive(Context context, Intent intent){
        Log.i("Parse Notification", "PUSH RECEIVED!!");

        Intent in = new Intent(context, MainActivity.class);
        try{
            String action = intent.getAction();


            JSONObject json = new JSONObject(intent.getExtras().getString("com.parse.Data"));

            Log.d("PUSH", json.getString("TIPO"));
            if (json.getString("TIPO").equals("invJuegoEquipos"))
                in = onTeamInvitation(json, context);
            else if (json.getString("TIPO").equals("invJuegoCasual"))
                in = onCasualGameInvitation(json, context);
            else if (json.getString("TIPO").equals("invEquipo"))
                in = onTeamInvitation(json, context);
        }catch (JSONException e){
            Log.e("JSON", "no se pudo parsear el JSON");
        }

        makeNotification(context,in);
    }


    public Intent onCasualGameInvitation(JSONObject json, Context ctx) throws JSONException{
        String msg = "", fecha = "", hora = "", cancha = "";
        ArrayList<Jugador> jugadores = new ArrayList<>();

        JSONArray integrantesJSON = json.getJSONArray("INTEGRANTES");

        msg = json.getString("MSG");
        fecha = json.getString("FECHA");
        hora = json.getString("HORA");
        cancha = json.getString("CANCHA");

        for (int i = 0; i < integrantesJSON.length(); i++) {
            //TODO sacar los datos de la db
            JSONObject jugador = integrantesJSON.getJSONObject(i);
            String username = jugador.getString("username");
            jugadores.add(new Jugador(username));
        }

        PartidoCasual partido = new PartidoCasual(fecha, hora, cancha, jugadores);
        Bundle bn = new Bundle();
        bn.putString("MSG", msg);
        bn.putSerializable("PARTIDO", partido);

        Intent in = new Intent(ctx, InvitacionPartidoCasualActivity.class);
        in.putExtras(bn);
        return in;
    }

    public Intent onTeamGameInvitation(JSONObject json, Context ctx) throws JSONException{
        String msg = "", fecha = "", hora = "", cancha = "";


        msg = json.getString("MSG");
        fecha = json.getString("FECHA");
        hora = json.getString("HORA");
        cancha = json.getString("CANCHA");

        JSONArray equipo1JSON = json.getJSONArray("EQUIPO1");
        Jugador capitanE1 = new Jugador(json.getString("CAPITANEQUPO1"));
        ArrayList<Jugador> jugadoresE1 = new ArrayList<>();

        for (int i = 0; i < equipo1JSON.length(); i++) {
            JSONObject jugador = equipo1JSON.getJSONObject(i);
            String username = jugador.getString("username");
            jugadoresE1.add(new Jugador(username));
        }

        Equipo equipo1 = new Equipo(json.getString("NOMBREEQUIPO1"), capitanE1);
        equipo1.agregarJugadores(jugadoresE1);


        JSONArray equipo2JSON = json.getJSONArray("EQUIPO2");
        Jugador capitanE2 = new Jugador(json.getString("CAPITANEQUPO2"));
        ArrayList<Jugador> jugadoresE2 = new ArrayList<>();

        for (int i = 0; i < equipo2JSON.length(); i++) {
            JSONObject jugador = equipo2JSON.getJSONObject(i);
            String username = jugador.getString("username");
            jugadoresE2.add(new Jugador(username));
        }


        Equipo equipo2 = new Equipo(json.getString("NOMBREEQUIPO2"), capitanE2);
        equipo1.agregarJugadores(jugadoresE2);


        PartidoPorEquipos partido = new PartidoPorEquipos(fecha, hora, cancha, equipo1, equipo2);

        Bundle bn = new Bundle();
        bn.putString("MSG", msg);
        bn.putSerializable("PARTIDO", partido);
        Intent in = new Intent(ctx, InvitacionPartidoEquiposActivity.class);
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
        in.putExtras(bn);
        return in;

    }

    public void makeNotification(Context ctx, Intent in){
        Bitmap icon = BitmapFactory.decodeResource(ctx.getResources(), R.drawable.ic_launcher);
        PendingIntent pi = PendingIntent.getActivity(ctx, (int) System.currentTimeMillis(), in, 0);

        Notification noti = new NotificationCompat.Builder(ctx)
                .setContentTitle("YO+10")
                .setContentText(in.getExtras().getString("MSG"))
                .setSmallIcon(R.drawable.ic_launcher)
                .setLargeIcon(icon)
                .setContentIntent(pi)
                .setAutoCancel(true)
                .setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION))
                .setVibrate((new Notification()).vibrate)
                .build();


        NotificationManager nm = (NotificationManager)ctx.getSystemService
                (Context.NOTIFICATION_SERVICE);
        nm.notify(0, noti);
    }
}
