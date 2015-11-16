package co.edu.eafit.yomas10.Util;

import android.content.Context;
import android.os.Bundle;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;

import co.edu.eafit.yomas10.Equipos.Equipo;
import co.edu.eafit.yomas10.Jugador.Jugador;
import co.edu.eafit.yomas10.Util.Connection.HttpBridge;
import co.edu.eafit.yomas10.Util.Connection.Receiver;

public class StaticUser{

    public static Jugador jugador;
    public static boolean ready = false;
    //public static Equipo equipo;
    //public static LinkedList<Jugador> jugadores;

    public void initialize(Context ctx){
        if (!ready){
            ready = true;
            jugador = new Jugador("Aleochoam");

            jugador.setNombre("Alejandro");
            jugador.setPosicion("Portero");
            jugador.setBio("Mido 1.80, he jugado en el Nacional y tengo 20 años");
            jugador.setCorreo("alejo8a_3@hotmail.com");

            Equipo equipo = jugador.crearEquipo("Jaguares");
            jugador.findEquipo("Jaguares").agregarJugadores(crearJugadores());

            ArrayList<Jugador> amigos = crearAmigos();
            for (int i = 0; i<amigos.size();i++ ){
                jugador.agregarAmigo(amigos.get(i));
            }
        }
    }

    public static ArrayList<Jugador> crearJugadores(){
        ArrayList jugadores = new ArrayList();

        Jugador chepe = new Jugador("joseluh");
        chepe.setNombre("Jose Luis");
        Jugador alfred = new Jugador("alfred598");
        alfred.setNombre("Luis Alfredo");
        Jugador ramos = new Jugador("Ramitos");
        ramos.setNombre("David Ramos");
        jugadores.add(chepe);
        jugadores.add(alfred);
        jugadores.add(ramos);

        return jugadores;

    }

    public static ArrayList<Jugador> crearAmigos(){
        ArrayList amigos = new ArrayList();
        Jugador yo = new Jugador("Aleochoam");
        yo.setNombre("Alejandro");
        Jugador moreno = new Jugador("jpmoreno109");
        moreno.setNombre("Juan Pablo Moreno");
        Jugador saravia = new Jugador("ssaravia");
        saravia.setNombre("Santiago Saravia");
        Jugador ramos = new Jugador("Ramitos");
        ramos.setNombre("David Ramos");
        Jugador jalvar = new Jugador("jalvar");
        jalvar.setNombre("Jose Luis");

        amigos.add(yo);
        amigos.add(moreno);
        amigos.add(saravia);
        amigos.add(ramos);
        amigos.add(jalvar);

        return amigos;
    }

    public static ArrayList<Jugador> crearIntegrantes(){
        Jugador alejandro = new Jugador("aleochoam");
        Jugador saravia = new Jugador("ssaravia");
        Jugador gaxel = new Jugador("harryGaxel");

        alejandro.setNombre("Alejandro");
        saravia.setNombre("Santiago");
        gaxel.setNombre("Felipe");

        ArrayList<Jugador> integrantes = new ArrayList<>();
        integrantes.add(alejandro);
        integrantes.add(saravia);
        integrantes.add(gaxel);

        return integrantes;
    }
}
