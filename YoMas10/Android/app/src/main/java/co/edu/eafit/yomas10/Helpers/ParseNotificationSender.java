package co.edu.eafit.yomas10.Helpers;

import android.view.View;
import android.widget.Toast;

import com.parse.ParsePush;

import org.json.JSONException;
import org.json.JSONObject;

import co.edu.eafit.yomas10.Clases.Jugador;
import co.edu.eafit.yomas10.R;

/**
 * Created by alejandro on 22/10/15.
 */
public class ParseNotificationSender {
    private static final String INV_GAME = "invJuego";
    private static final String INV_TEAM = "invEquipo";
    private static final String ACTION = "co.edu.eafit.yomas10.ParseReceiver.RECEIVE";
    private static final String GAME_MESSAGE = "Has sido invitado a un partido!";
    private static final String TEAM_MESSAGE = "Has sido invitado a un equipo!";


    /**
     * El metodo importante
     * Crea un JSON, lo inicializa con la informacion que se desea.
     * Se crea una notificacion Parse
     * Se setea el canal de la notificacion
     * Se setea la informacion de la notificacion, en este caso, el JSON
     * Se manda la notificacion
     * @String username el ususername del destinatario
     */
    public static void sendGameInvitation(String username) throws JSONException {
        JSONObject json = new JSONObject();
        json.put("action", ACTION);
        json.put("TIPO", INV_GAME);
        json.put("msg", GAME_MESSAGE);
        //TODO json.put("date", fecha.getText().toString());

        ParsePush push = new ParsePush();
        push.setChannel(username);
        push.setData(json);
        push.sendInBackground();
    }

    /**
     * El metodo importante
     * Crea un JSON, lo inicializa con la informacion que se desea.
     * Se crea una notificacion Parse
     * Se setea el canal de la notificacion
     * Se setea la informacion de la notificacion, en este caso, el JSON
     * Se manda la notificacion
     * @String username el usuario al que se le va a mandar la notificacion
     */
    public static void sendTeamInvitation(String username, String nombreEquipo, String capitan)
            throws JSONException{
        JSONObject json = new JSONObject();
        json.put("action", ACTION);
        json.put("TIPO", INV_TEAM);
        json.put("MSG", TEAM_MESSAGE);
        json.put("NOMBRE", nombreEquipo);
        json.put("CAPITAN", capitan);

        ParsePush push = new ParsePush();
        push.setChannel(username);
        push.setData(json);
        push.sendInBackground();
    }
}
