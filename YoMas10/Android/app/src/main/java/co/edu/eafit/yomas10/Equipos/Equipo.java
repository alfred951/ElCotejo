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
import co.edu.eafit.yomas10.MyApplication;
import co.edu.eafit.yomas10.Util.Connection.Http;
import co.edu.eafit.yomas10.Util.Connection.HttpBridge;
import co.edu.eafit.yomas10.Util.Connection.Receiver;

/**
 * Created by Alejandro on 23/09/2015.
 */
public class Equipo implements Serializable, Receiver {

    private int id;
    private String nombre;
    private Capitan capitan;
    private ArrayList<Jugador> integrantes = new ArrayList<>();


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

    public Equipo(String nombre, Jugador creador, int id){
        integrantes = new ArrayList<>();
        this.id = id;
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

    public void cambiarCapitan(Jugador nuevo){
        Capitan capitan = new Capitan(nuevo);
        capitan.setEquipo(this);
        this.capitan = capitan;
    }


    @Override
    public void onReceiveResult(int resultCode, Bundle resultData) {

        try {
            JSONArray jsonArray = new JSONArray(resultData.getString("GetResponse"));
            JSONObject json = jsonArray.getJSONObject(0);

            if (json.has("nombre")){
                nombre = json.getString("nombre");
                Jugador capitan = new Jugador(json.getString("capitan"));
                this.capitan = new Capitan(capitan);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

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
        return getNombre() + getId() ;
    }
}
