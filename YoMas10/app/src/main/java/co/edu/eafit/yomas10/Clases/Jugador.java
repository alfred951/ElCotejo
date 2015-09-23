package co.edu.eafit.yomas10.Clases;

import java.util.LinkedList;


/**
 * Created by Alejandro on 23/09/2015.
 */
public class Jugador {

    private String username;
    private String nombre;
    private String bio;
    private LinkedList<Jugador> amigos;
    private LinkedList<Cancha> canchasFavoritas;
    private LinkedList<String> canales;

    public Jugador(){
        //sacar los datos de la base de datos
    }

    public LinkedList getChannels(){
        return canales;
    }

    public void agregarAmigo(Jugador jug){
        if (!amigos.contains(jug)) {
            amigos.add(jug);
        }
    }

    public void agregarCancha(Cancha cancha) {
        if(!canchasFavoritas.contains(cancha)) {
            canchasFavoritas.add(cancha);
        }
    }

    public void solicitarIngreso(Equipo equi){
        //TODO
    }

    public void suscribirseCanal(String canal){
        if (!canales.contains(canal)){
            canales.add(canal);
        }
    }

    public void removerCanal(String canal){
        if (canales.contains(canal)){
            canales.remove(canal);
        }
    }

    public Equipo crearEquipo(String nombre){
        Equipo equipo = new Equipo(nombre, this);
        return equipo;
    }
}
