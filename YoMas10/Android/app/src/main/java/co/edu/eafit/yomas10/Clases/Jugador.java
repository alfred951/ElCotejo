package co.edu.eafit.yomas10.Clases;

import android.net.Uri;
import android.util.Log;

import com.parse.ParsePush;

import java.util.LinkedList;

import co.edu.eafit.yomas10.Helpers.StaticUser;


/**
 * Created by Alejandro on 23/09/2015.
 */
public class Jugador {

    protected String username;
    protected String nombre = null;
    protected String bio = null;
    protected String posicion = null;
    protected String correo = null;
    protected Uri profilePic = Uri.parse("android.resource://co.edu.eafit.yomas10/drawable/no_user_logo");
    protected LinkedList<Equipo> equipos = null;
    //TODO LISTA DE PARTIDOS
    protected LinkedList<Jugador> amigos = null;
    protected LinkedList<Cancha> canchasFavoritas = null;
    protected LinkedList<String> canales = null;


    /**
     * Constructor del jugador
     * @param username el usuario del jugador, debe ser unico y sera la llave primaria en la DB
     */
    public Jugador(String username){
        this.username = username;
        equipos = new LinkedList<>();
        amigos = new LinkedList<>();
        canchasFavoritas = new LinkedList<>();
        canales = new LinkedList<>();

        //TODO: sacar los datos de la base de datos
    }

    /**
     * Metodo para crear un equipo, en dicho equipo quedara como capitan
     * @param nombre nombre del equipo
     * @return el equipo nuevo
     */
    public Equipo crearEquipo(String nombre){
        Equipo equipo = new Equipo(nombre, this);

        //TODO: provisional
        LinkedList<Jugador> jugadores = StaticUser.crearJugadores();
        for (int i = 0;i< jugadores.size();i++){
            equipo.agregarJugador(jugadores.get(i));
        }
        agregarEquipo(equipo);

        return equipo;
    }

    public Equipo findEquipo(String nombre){
        for (int i = 0; i<equipos.size();i++){
            if (equipos.get(i).getNombre().equals(nombre)){
                return equipos.get(i);
            }
        }
        return null;
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

    /**
     * Como jugador, se solicita el ingreso a un equipo medianta una notificacion al capitan
     * @param equi equipo al que se quiere inscribir
     */
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

    /**
     * Agregar un equipo a la lista de los equipos donde esta registrado
     * @param equipo equipo al que se va a unir
     */
    public void agregarEquipo(Equipo equipo){
        if (!equipos.contains(equipo)){
            equipos.add(equipo);
            ParsePush.subscribeInBackground(equipo.getNombre());
        }
    }

    public String getUsername() {
        return username;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    public String getPosicion() {
        return posicion;
    }

    public void setPosicion(String posicion) {
        this.posicion = posicion;
    }

    public Uri getProfilePic(){
        return profilePic;
    }

    public void setProfilePic(Uri profilePic) {
        this.profilePic = profilePic;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public LinkedList<Equipo> getEquipos() {
        return equipos;
    }
}
