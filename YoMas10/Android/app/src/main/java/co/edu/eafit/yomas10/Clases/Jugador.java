package co.edu.eafit.yomas10.Clases;

import android.net.Uri;
import java.util.LinkedList;


/**
 * Created by Alejandro on 23/09/2015.
 */
public class Jugador {

    protected String username;
    protected String nombre;
    protected String bio;
    protected String posicion;
    protected String correo;
    protected Uri profilePic = Uri.parse("android.resource://co.edu.eafit.yomas10/drawable/no_user_logo");
    protected LinkedList<Equipo> equipos;
    protected LinkedList<Jugador> amigos;
    protected LinkedList<Cancha> canchasFavoritas;
    protected LinkedList<String> canales;


    /**
     * Constructor del jugador
     * @param username el usuario del jugador, debe ser unico y sera la llave primaria en la DB
     */
    public Jugador(String username){
        this.username = username;
        //TODO: sacar los datos de la base de datos
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
     * Metodo para crear un equipo, en dicho equipo quedara como capitan
     * @param nombre nombre del equipo
     * @return el equipo nuevo
     */
    public Equipo crearEquipo(String nombre){
        Equipo equipo = new Equipo(nombre, this);
        agregarEquipo(equipo);

        return equipo;
    }

    /**
     * Agregar un equipo a la lista de los equipos donde esta registrado
     * @param equipo equipo al que se va a unir
     */
    public void agregarEquipo(Equipo equipo){
        if (!equipos.contains(equipo)){
            equipos.add(equipo);
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
