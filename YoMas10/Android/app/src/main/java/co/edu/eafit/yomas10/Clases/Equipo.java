package co.edu.eafit.yomas10.Clases;

import java.util.LinkedList;

/**
 * Created by Alejandro on 23/09/2015.
 */
public class Equipo {

    private String nombre;
    private Capitan capitan;
    private LinkedList<Jugador> integrantes;

    /**
     * A la hora de crear un equipo, el jugador creador se asciende a capitan del equipo
     * @param nombre Nombre del equipo
     * @param creador Jugador que va a quedar como capitan
     */
    public Equipo(String nombre, Jugador creador){
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

    /**
     * Ir a la base de datos y conseguir el objeto equipo con la informacion actual
     * @return Objeto equipo con toda la informacion lista
     */
    public Equipo getInfoDB(){
        //TODO
        return null;
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

    public LinkedList<Jugador> getIntegrantes() {
        return integrantes;
    }
}
