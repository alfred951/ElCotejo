package co.edu.eafit.yomas10.Helpers;

import java.util.LinkedList;

import co.edu.eafit.yomas10.Clases.Equipo;
import co.edu.eafit.yomas10.Clases.Jugador;

public class StaticUser {

    public static Jugador jugador;
    //public static Equipo equipo;
    //public static LinkedList<Jugador> jugadores;

    public static void initialize(){
        jugador = new Jugador("Aleochoam");

        jugador.setNombre("Alejandro");
        jugador.setPosicion("Portero");
        jugador.setBio("Mido 1.80, he jugado en el Nacional y tengo 20 años");
        jugador.setCorreo("alejo8a_3@hotmail.com");

        Equipo equipo = jugador.crearEquipo("Jaguares");

    }

    public static LinkedList<Jugador> crearJugadores(){
        LinkedList jugadores = new LinkedList();
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
}
