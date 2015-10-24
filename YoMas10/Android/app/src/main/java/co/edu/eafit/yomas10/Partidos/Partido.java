package co.edu.eafit.yomas10.Partidos;

import co.edu.eafit.yomas10.Equipos.Equipo;

/**
 * Created by Usuario on 10/10/2015.
 */
public class Partido {

    private Equipo equipo1;
    private Equipo equipo2;
    private String nombre;
    private String fecha;
    private String hora;
    private Cancha cancha;

    public Partido(Equipo equipo1, Equipo equipo2, String nombre){
        this.equipo1 = equipo1;
        this.equipo2 = equipo2;
        this.nombre = nombre;
    }

    public Equipo getEquipo1(){
        return equipo1;
    }

    public Equipo getEquipo2(){
        return equipo2;
    }

    public String getNombre(){
        return nombre;
    }

    public String getFecha(){
        return fecha;
    }

    public String getHora(){
        return hora;
    }

    public void setEquipo1(Equipo eq1){
        this.equipo1 = eq1;
    }

    public void setEquipo2(Equipo eq2){
        this.equipo2 = eq2;
    }

    public void setNombre(String nombre){
        this.nombre = nombre;
    }

    public void setFecha(String fecha){
        this.fecha = fecha;
    }

    public void setHora(String hora){
        this.hora = hora;
    }
}
