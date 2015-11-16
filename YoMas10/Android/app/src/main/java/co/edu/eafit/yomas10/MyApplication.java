package co.edu.eafit.yomas10;

import android.app.Application;
import android.os.Bundle;

import com.parse.Parse;
import com.parse.ParseInstallation;
import com.parse.ParsePush;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;

import co.edu.eafit.yomas10.Jugador.Jugador;
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

        user = new Jugador("Aleochoam");
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
        }catch (JSONException e){
            e.printStackTrace();
        }
    }

    @Override
    public void onReceiveResult(int resultCode, Bundle resultData) {
        try {
            JSONObject jugador = new JSONObject(resultData.getString("GetResponse"));
            initialize(jugador);

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public Jugador getUser(){
        return user;
    }
}
