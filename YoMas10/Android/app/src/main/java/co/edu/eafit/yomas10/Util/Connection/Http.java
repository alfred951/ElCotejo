package co.edu.eafit.yomas10.Util.Connection;


import android.app.IntentService;
import android.content.Intent;
import android.os.Bundle;
import android.os.ResultReceiver;
import android.util.Log;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONObject;

import javax.net.ssl.HttpsURLConnection;

public class Http extends IntentService{

   String urlget;
   String type;
   Bundle bundle;
   static StringBuilder urlbase;
   String urlapi =  "http://www.yomasdiez.com/index.php/api/";

    public static final String JUGADOR= "Jugador";
    public static final String PARTICIPANTES= "Participantes";
    public static final String AMIGOS = "Amigos";
    public static final String PARTIDO = "Partido";
    public static final String EQUIPO = "Equipo";
    public static final String INTEGRANTES = "Integrantes";

   public Http() throws UnsupportedEncodingException {
       super(Http.class.getName());

       urlbase = new StringBuilder();
       urlget = new String();
       type = new String();
       bundle = new Bundle();

   }

   public static JSONArray makeGetRequest(String stringurl) {
       JSONArray response = new JSONArray();
       StringBuilder urlS = new StringBuilder();
       urlS.append(stringurl);
       try {
           URL url = new URL(urlS.toString());
           HttpURLConnection con = (HttpURLConnection) url.openConnection();

           // Request Header
           con.setRequestMethod("GET");
           con.setRequestProperty("Accept", "application/json");
           con.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
           con.setRequestProperty("http.agent", "");

           //Parse JSON
           if (con.getResponseCode() == HttpURLConnection.HTTP_OK) {
              String json = getJSON(con.getInputStream());
              JSONArray obj = new JSONArray(json);
              response = obj;
           }
       }
       catch (Exception e){
           Log.e("ErrorConnection", e.getMessage());
       }
       return response;
   }

    public static String getJSON(InputStream inputStream) throws IOException {
        StringBuilder json = new StringBuilder();
        BufferedReader in = new BufferedReader(
                new InputStreamReader(inputStream));

        String input;
        while ((input = in.readLine()) != null){
            json.append(input);
        }
        in.close();

        return json.toString();
    }

    public static String getGetDataString(String type, HashMap<String, String> params)
            throws UnsupportedEncodingException{
        StringBuilder result = new StringBuilder();
        boolean first = true;
        switch (type){
            case JUGADOR:
                result.append("Usuario/Jugador");
                break;
            case AMIGOS:
                result.append("Usuario/Amigos");
                break;
            case PARTIDO:
                result.append("Partido/Partido");
                break;
            case PARTICIPANTES:
                result.append("Partido/Participantes");
                break;
            case INTEGRANTES:
                result.append("Equipo/Integrantes");
                break;
            case EQUIPO:
                result.append("Equipo/Equipo");
                break;
        }
        for(Map.Entry<String, String> entry : params.entrySet()) {
            if (first){
                first = false;
                result.append("?");
            }
            else{
               result.append("&");
            }
            result.append(URLEncoder.encode(entry.getKey(), "UTF-8"));
            result.append("=");
            result.append(URLEncoder.encode(entry.getValue(), "UTF-8"));
        }
        return result.toString();
    }


    /* Metodo de la interfaz de Servicio, crea un Thread para
    trabajar con la red
     */
    @Override
    protected void onHandleIntent(Intent intent) {
        //Objeto ResultReceiver que se encarga de
        // enviar y recibir mensajes de un Thread al otro.
        final ResultReceiver receiver = intent.getParcelableExtra("mReceiver");
        urlbase.append(this.urlapi);
        urlbase.append(intent.getStringExtra("urlget"));

        try{
            JSONArray getResult = makeGetRequest(urlbase.toString());
            bundle.putString("GetResponse", getResult.toString());
            receiver.send(0, bundle);
        }catch (Exception e){
            Log.e("ErrorIntent", e.getMessage());
        }
    }
}
