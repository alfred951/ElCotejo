package co.edu.eafit.yomas10.Clases;

import java.util.LinkedList;

/**
 * Created by Alejandro on 23/09/2015.
 */
public class Equipo {

    private String nombre;
    private Capitan capitan;
    private LinkedList<Jugador> integrantes;

    public Equipo(String nombre, Jugador creador){
        this.nombre = nombre;

        this.capitan = (Capitan) creador;

        this.capitan.setEquipo(this);
    }

    public void agregarJugador(Jugador jug){
        if(!integrantes.contains(jug)){
            integrantes.add(jug);
        }
    }




}
