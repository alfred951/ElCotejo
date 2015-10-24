package co.edu.eafit.yomas10.Partidos;

import co.edu.eafit.yomas10.Equipos.Equipo;


public class PartidoPorEquipos extends Partido {

    private Equipo organizador;
    private Equipo contrincante;

    public PartidoPorEquipos(String fecha, String hora, String cancha,
                             Equipo organizador, Equipo contrincante){

        super(fecha, hora, cancha);
        this.organizador = organizador;
        this.contrincante = contrincante;
    }

    public Equipo getOrganizador() {
        return organizador;
    }

    public Equipo getContrincante() {
        return contrincante;
    }
}
