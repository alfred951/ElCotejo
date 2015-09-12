package co.edu.eafit.yomas10;

import com.parse.ParseInstallation;
import com.parse.ParsePush;

import java.util.List;

/**
 * Created by Usuario on 11/09/2015.
 */
public class Jugador {

    private String nombre;
    private List<String> channels;

    public Jugador(){
        nombre = "Alejandro";
        channels = ParseInstallation.getCurrentInstallation().getList("channels");
        ParsePush.subscribeInBackground("sistemas");

    }


}
