package co.edu.eafit.yomas10.Partidos;

import java.io.Serializable;

import co.edu.eafit.yomas10.Equipos.Equipo;

/**
 * Created by Usuario on 10/10/2015.
 */
public abstract class Partido implements Serializable {

    protected int id;
    protected String fecha;
    protected String hora;
    protected String cancha;

    public Partido(String fecha, String hora, String cancha){
        this.fecha = fecha;
        this.hora = hora;
        this.cancha = cancha;
    }

    public Partido(String fecha, String hora, String cancha, int id){
        this.id = id;
        this.fecha = fecha;
        this.hora = hora;
        this.cancha = cancha;
    }

    public String getFecha(){
        return fecha;
    }

    public String getHora(){
        return hora;
    }

    public String getCancha() {
        return cancha;
    }

    public int getId() {
        return id;
    }
}
