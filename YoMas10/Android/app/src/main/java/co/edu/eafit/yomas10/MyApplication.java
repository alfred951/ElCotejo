package co.edu.eafit.yomas10;

import android.app.Application;
import android.content.Context;
import android.os.Bundle;

import com.parse.Parse;
import com.parse.ParseInstallation;
import com.parse.ParsePush;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;

import co.edu.eafit.yomas10.Equipos.Equipo;
import co.edu.eafit.yomas10.Jugador.Jugador;
import co.edu.eafit.yomas10.Util.Connection.Http;
import co.edu.eafit.yomas10.Util.Connection.HttpBridge;
import co.edu.eafit.yomas10.Util.Connection.Receiver;

/**
 * Created by alejandro on 15/11/15.
 * Clase que controla la aplicacion en general, se ejecuta cuando la aplicacion es creada
 */
public class MyApplication extends Application implements Receiver{

    Jugador user;

    @Override
    public void onCreate() {
        super.onCreate();

        try {
            Parse.initialize(this);
            ParseInstallation.getCurrentInstallation().saveInBackground();
        }catch (Exception e){}

        user = new Jugador("jalvar"); //TODO cambiar por login
        ParsePush.subscribeInBackground(user.getUsername());


        HashMap<String, String> map = new HashMap<>();
        map.put("nickname", user.getUsername());
        try {
            startService(HttpBridge.startWorking(this, map, this, "Jugador"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }

    public void initialize(JSONObject jugador){
        try {
            user.setNombre(jugador.getString("nombre"));
            user.setBio(jugador.getString("bio"));
            user.setCorreo(jugador.getString("correo"));
            user.setEdad(jugador.getInt("edad"));
            user.setPosicion(jugador.getString("posicion"));

            getInfoDB();
        }catch (JSONException e){
            e.printStackTrace();
        }
    }

    public void getInfoDB(){
        getAmigosDB();
        getPartidosDB();
        getEquiposDB();
    }

    private void getAmigosDB(){
        HashMap<String, String> map = new HashMap<>();
        map.put("nickname", user.getUsername());

        try {
            HttpBridge.startWorking(this, map, this, Http.AMIGOS); //TODO
        }catch (UnsupportedEncodingException e){
            e.printStackTrace();
        }
    }

    private void getPartidosDB(){
        HashMap<String, String> map = new HashMap<>();
        //TODO map.put("nickname", user.getUsername());

        try {
            HttpBridge.startWorking(this, map, this, Http.PARTIDO); //TODO
        }catch (UnsupportedEncodingException e){
            e.printStackTrace();
        }
    }

    private void getEquiposDB(){
        HashMap<String, String> map = new HashMap<>();
        map.put("nickname", user.getUsername());

        try {
            HttpBridge.startWorking(this, map, this, Http.EQUIPO_JUGADOR); //TODO
        }catch (UnsupportedEncodingException e){
            e.printStackTrace();
        }
    }

    @Override
    public void onReceiveResult(int resultCode, Bundle resultData) {
        try {
            JSONArray jsonArray = new JSONArray(resultData.getString("GetResponse"));
            JSONObject json = jsonArray.getJSONObject(0);
            if (json.has("nombre")) { // perfil
                initialize(json);
            }else if (json.getString("amigo") != null){   //amigos
                for (int i = 0; i < jsonArray.length(); i++) {
                    Jugador amigo = new Jugador(jsonArray.getJSONObject(i).getString("amigo"));
                    user.agregarAmigo(amigo);
                }
            }else if (false) {//  partido

            }else if (json.has("idEquipo")) { // Equipo
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject equipojs = jsonArray.getJSONObject(i);

                    Equipo equipo = new Equipo(null, null);
                    equipo.getInfoDB(this, equipojs.getInt("idEquipo"));

                    equipo = equipo.getEquipo();
                    user.agregarEquipo(equipo);
                }
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }



    public Jugador getUser(){
        return user;
    }
}
