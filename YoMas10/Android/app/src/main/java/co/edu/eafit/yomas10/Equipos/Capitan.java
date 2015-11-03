package co.edu.eafit.yomas10.Equipos;

import java.io.Serializable;
import java.util.ArrayList;

import co.edu.eafit.yomas10.Http;
import co.edu.eafit.yomas10.Jugador.Jugador;

/**
 * Created by Alejandro on 23/09/2015.
 */
public class Capitan extends Jugador implements Serializable{

    private Equipo equipo;
    private Http http;

    public Capitan(Jugador jugador){
        super(jugador.getUsername());
        nombre = jugador.getNombre();
        bio = jugador.getBio();
        posicion = jugador.getPosicion();
        correo = jugador.getCorreo();
        //profilePic = jugador.getProfilePic();
        equipos = jugador.getEquipos();
        amigos = jugador.getAmigos();
        canchasFavoritas = jugador.getCanchasFavoritas();
        canales = jugador.getChannels();
    }

    /**
     * Invitar un jugador a un equipo, se debe enviar la notificacion a dicha persona
     * @param jug el jugador que se quiere invitar.
     */
    public void invitarJugador(Jugador jug){

    }

    /**
     * Si se seleccionan varios jugadores a la ves
     * @param jugadores lista de jugadores a invitar.
     */
    public void invitarJugadores(ArrayList<Jugador> jugadores){
        //TODO
    }

    public void setEquipo(Equipo equipo){
        this.equipo = equipo;
    }

    @Override
    public String toString() {
        return nombre;
    }
}
