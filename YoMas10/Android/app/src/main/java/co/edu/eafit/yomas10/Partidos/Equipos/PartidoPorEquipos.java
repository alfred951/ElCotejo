package co.edu.eafit.yomas10.Partidos.Equipos;

import java.io.Serializable;

import co.edu.eafit.yomas10.Equipos.Equipo;
import co.edu.eafit.yomas10.Partidos.Partido;


public class PartidoPorEquipos extends Partido {

    private Equipo organizador;
    private Equipo contrincante;

    public PartidoPorEquipos(String fecha, String hora, String cancha,
                             Equipo organizador, Equipo contrincante){

        super(fecha, hora, cancha);
        this.organizador = organizador;
        this.contrincante = contrincante;
    }

    public PartidoPorEquipos(String fecha, String hora, String cancha,
                             Equipo organizador, Equipo contrincante, int id){

        super(fecha, hora, cancha, id);
        this.organizador = organizador;
        this.contrincante = contrincante;
    }

    public Equipo getOrganizador() {
        return organizador;
    }

    public Equipo getContrincante() {
        return contrincante;
    }

    @Override
    public String toString() {
        return organizador.getNombre() + "vs " + contrincante.getNombre();
    }
}
