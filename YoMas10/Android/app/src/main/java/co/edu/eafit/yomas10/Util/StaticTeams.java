package co.edu.eafit.yomas10.Util;

import java.util.ArrayList;

import co.edu.eafit.yomas10.Equipos.Equipo;
import co.edu.eafit.yomas10.Jugador.Jugador;

/**
 * Created by Alejandro on 24/10/2015.
 */
public class StaticTeams {

    private static Equipo equipo1;
    private static Equipo equipo2;

    public static Equipo getEquipo1(){
        initializeEquipo1();
        return equipo1;
    }

    public static Equipo getEquipo2(){
        initializeEquipo2();
        return equipo2;
    }

    public static void initializeEquipo1(){
        Jugador capitan = new Jugador("jgaviria");
        equipo1 = capitan.crearEquipo("Medellin");

        equipo1.agregarJugadores(StaticUser.crearJugadores());

    }

    public static  void initializeEquipo2(){
        Jugador capitan = new Jugador("jfmacro");
        equipo2 = capitan.crearEquipo("Nacional");

        equipo2.agregarJugadores(StaticUser.crearIntegrantes());
    }


}
