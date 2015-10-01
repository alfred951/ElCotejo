package co.edu.eafit.yomas10.Clases;

import android.net.Uri;
import java.util.LinkedList;


/**
 * Created by Alejandro on 23/09/2015.
 */
public class Jugador {

    private String username;
    private String nombre;
    private String bio;
    private String posicion;
    private String correo;
    private Uri profilePic = Uri.parse("android.resource://co.edu.eafit.yomas10/drawable/no_user_logo");
    private LinkedList<Equipo> equipos;
    private LinkedList<Jugador> amigos;
    private LinkedList<Cancha> canchasFavoritas;
    private LinkedList<String> canales;

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
        agregarEquipo(equipo);

        return equipo;
    }

    public void agregarEquipo(Equipo equipo){
        if (!equipos.contains(equipo)){
            equipos.add(equipo);
        }
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
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

    public Uri getProfilePic(){ return profilePic; }

    public void setProfilePic(Uri profilePic) { this.profilePic = profilePic; }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }



}
