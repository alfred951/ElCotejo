package co.edu.eafit.yomas10.Util;

import com.parse.ParsePush;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by alejandro on 22/10/15.
 */
public class ParseNotificationSender {
    private static final String INV_CASUAL_GAME = "invJuegoCasual";
    private static final String INV_TEAM_GAME = "invJuegoEquipos";
    private static final String INV_TEAM = "invEquipo";
    private static final String ACTION = "co.edu.eafit.yomas10.Util.ParseReceiver.RECEIVE";
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
    public static void sendCasualGameInvitation(String username, String fecha, String hora, String cancha,
                                                ArrayList<String> jugadores) throws JSONException {
        JSONObject json = new JSONObject();
        json.put("action", ACTION);
        json.put("TIPO", INV_CASUAL_GAME);
        json.put("MSG", GAME_MESSAGE);
        json.put("FECHA", fecha);
        json.put("HORA", hora);
        json.put("CANCHA", cancha);

        JSONArray jsonArray = new JSONArray();

        for (int i = 0; i < jugadores.size(); i++) {
            JSONObject jo = new JSONObject();
            jo.put("username", jugadores.get(i));
            jsonArray.put(jo);
        }

        json.put("INTEGRANTES", jsonArray);


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
     * @param username el usuario al que se le va a mandar la notificacion
     * @param nombreEquipo el nombre del equipo
     * @param capitan username del capitan
     */
    public static void sendTeamInvitation(String username, String nombreEquipo, String capitan,
                                          ArrayList<String> jugadores)  throws JSONException{
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
