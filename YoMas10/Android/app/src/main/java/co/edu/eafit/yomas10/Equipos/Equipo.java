package co.edu.eafit.yomas10.Equipos;

import android.content.Context;
import android.os.Bundle;

import com.parse.ParsePush;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;

import co.edu.eafit.yomas10.Jugador.Jugador;
import co.edu.eafit.yomas10.Util.Connection.Http;
import co.edu.eafit.yomas10.Util.Connection.HttpBridge;
import co.edu.eafit.yomas10.Util.Connection.Receiver;

/**
 * Created by Alejandro on 23/09/2015.
 */
public class Equipo implements Serializable, Receiver {

    private String nombre;
    private Capitan capitan;
    private int id;
    private ArrayList<Jugador> integrantes;

    private Equipo equipo;

    /**
     * A la hora de crear un equipo, el jugador creador se asciende a capitan del equipo
     * @param nombre Nombre del equipo
     * @param creador Jugador que va a quedar como capitan
     */
    public Equipo(String nombre, Jugador creador){
        integrantes = new ArrayList<>();
        this.nombre = nombre;
        this.capitan = new Capitan(creador);
        this.capitan.setEquipo(this);
    }

    /**
     * agregar jugador al equipo. Este metodo se invoca cuando el jugador aceptó la invitacion
     * @param jug jugador a a invitar
     */
    public void agregarJugador(Jugador jug){
        if(!integrantes.contains(jug)){
            integrantes.add(jug);
        }

        //TODO: subir a la base de datos
    }

    public void agregarJugadores(ArrayList<Jugador> jugadores){
        for (int i = 0; i < jugadores.size(); i++){
            agregarJugador(jugadores.get(i));
        }

    }

    /**
     * Ir a la base de datos y conseguir el objeto equipo con la informacion actual
     * @return Objeto equipo con toda la informacion lista
     */
    public void getInfoDB(Context ctx, int i){
        HashMap<String, String> map = new HashMap<>();
        map.put("idEquipo", i+"");

        try {
            HttpBridge.startWorking(ctx, map, this, Http.EQUIPO);
        }catch (UnsupportedEncodingException e){
            e.printStackTrace();
        }
    }

    public void getIntegrantesDB(Context ctx, int i){
        HashMap<String, String> map = new HashMap<>();
        map.put("idEquipo",  i+"");

        try {
            HttpBridge.startWorking(ctx, map, this, Http.INTEGRANTES);
        }catch (UnsupportedEncodingException e){
            e.printStackTrace();
        }
    }

    @Override
    public void onReceiveResult(int resultCode, Bundle resultData) {

        try {
            JSONArray jsonArray = new JSONArray(resultData.getString("GetResponse"));
            JSONObject json = jsonArray.getJSONObject(0);

            if (json.has("nombre")){
                equipo = new Equipo(json.getString("nombre"), new Jugador(json.getString("capitan")));
            }else if (json.has("nickname")){
                for (int i = 0; i < jsonArray.length(); i++) {
                    equipo.agregarJugador(new Jugador(jsonArray.getJSONObject(i).getString("nickname")));
                }
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    public Equipo getEquipo() {
        return equipo;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Capitan getCapitan() {
        return capitan;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public ArrayList<Jugador> getIntegrantes() {
        return integrantes;
    }

    @Override
    public String toString() {
        return getNombre();
    }
}
