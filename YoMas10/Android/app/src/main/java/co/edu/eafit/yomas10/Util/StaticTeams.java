package co.edu.eafit.yomas10.Util;

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

    public void initializeEquipo1(){
        Jugador capitan = new Jugador("pablo23");
        
    }

    public void initializeEquipo2(){

    }


}
