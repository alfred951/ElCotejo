package co.edu.eafit.yomas10.Jugador;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import com.parse.ParsePush;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;

import co.edu.eafit.yomas10.Equipos.Equipo;
import co.edu.eafit.yomas10.Partidos.Cancha;
import co.edu.eafit.yomas10.Partidos.Partido;
import co.edu.eafit.yomas10.Util.Connection.Http;
import co.edu.eafit.yomas10.Util.Connection.HttpBridge;
import co.edu.eafit.yomas10.Util.Connection.Receiver;


/**
 * Created by Alejandro on 23/09/2015.
 */
public class Jugador implements Serializable {

    protected String username;
    protected String nombre = null;
    protected String bio = null;
    protected String posicion = null;
    protected String correo = null;
    protected int edad = 0;
    //protected Uri profilePic = Uri.parse("android.resource://co.edu.eafit.yomas10/drawable/no_user_logo");
    protected ArrayList<Equipo> equipos = null;
    protected ArrayList<Partido> partidos = null;
    protected ArrayList<Jugador> amigos = null;


    /**
     * Constructor del jugador
     * @param username el usuario del jugador, debe ser unico y sera la llave primaria en la DB
     */
    public Jugador(String username){
        this.username = username;
        equipos = new ArrayList<>();
        partidos = new ArrayList<>();
        amigos = new ArrayList<>();

    }

    /**
     * Metodo para crear un equipo, en dicho equipo quedara como capitan
     * @param nombre nombre del equipo
     * @return el equipo nuevo
     */
    public Equipo crearEquipo(String nombre){
        Equipo equipo = new Equipo(nombre, this);
        agregarEquipo(equipo);
        return equipo;
    }

    public Equipo crearEquipo(String nombre, int id){
        Equipo equipo = new Equipo(nombre, this, id);
        agregarEquipo(equipo);
        return equipo;
    }

    public Equipo findEquipo(String nombre){
        for (int i = 0; i<equipos.size();i++){
            if (equipos.get(i).getNombre().equals(nombre)){
                return equipos.get(i);
            }
        }
        return null;
    }

    public Jugador findAmigo(String username){
        for (Jugador jugador: amigos) {
            if (jugador.getUsername().equals(username))
                return jugador;
        }
        return null;
    }

    public void agregarAmigo(Jugador jug){
        if (!amigos.contains(jug)) {
            amigos.add(jug);
        }
    }

    /**
     * Agregar un equipo a la lista de los equipos donde esta registrado
     * @param equipo equipo al que se va a unir
     */
    public void agregarEquipo(Equipo equipo){
        if (!equipos.contains(equipo)){
            equipos.add(equipo);
            ParsePush.subscribeInBackground(equipo.getNombre());
        }
    }

    public void agregarPartido(Partido partido){
        if(!partidos.contains(partido)){
            partidos.add(partido);
            ///ParsePush.subscribeInBackground(null) TODO cambiar por el id del partido
        }
    }

    @Override
    public String toString() {
        return getUsername();
    }

    public String getUsername() { return username; }


    public String getNombre() { return nombre; }

    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getBio() { return bio; }

    public void setBio(String bio) { this.bio = bio; }

    public String getPosicion() { return posicion; }

    public void setPosicion(String posicion) { this.posicion = posicion; }

    public int getEdad() {
        return edad;
    }

    public void setEdad(int edad) {
        this.edad = edad;
    }

    //public Uri getProfilePic(){ return profilePic; }

    //public void setProfilePic(Uri profilePic) { this.profilePic = profilePic; }

    public String getCorreo() { return correo; }

    public void setCorreo(String correo) { this.correo = correo; }

    public ArrayList<Equipo> getEquipos() { return equipos; }

    public ArrayList<Partido> getPartidos(){ return partidos; }

    public ArrayList<Jugador> getAmigos() { return amigos; }

}
