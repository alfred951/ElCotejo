package co.edu.eafit.yomas10;


import android.accounts.NetworkErrorException;
import android.app.IntentService;
import android.content.Intent;
import android.os.Bundle;
import android.os.ResultReceiver;
import android.util.Log;

import com.parse.Parse;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import javax.net.ssl.HttpsURLConnection;

public class Http extends IntentService{

   String urlget;
   String type;
   Bundle bundle;
   static StringBuilder urlbase;
   String urlapi =  "http://www.yomasdiez.com/index.php/api/Usuario/Jugador";

   public Http() throws UnsupportedEncodingException {
       super(Http.class.getName());

       urlbase = new StringBuilder();
       urlget = new String();
       type = new String();
       bundle = new Bundle();

   }

   public static String makeGetRequest(String stringurl) {
       String response = "";
       StringBuilder urlS = new StringBuilder();
       urlS.append(stringurl);
       try {
           Log.d("Hola", "1");
           URL url = new URL(urlS.toString());
           HttpURLConnection con = (HttpURLConnection) url.openConnection();

           Log.d("Hola", "2");
           // Request Header
           con.setRequestMethod("GET");
           con.setRequestProperty("Accept", "application/json");
           con.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
           con.setRequestProperty("http.agent", "");

           Log.d("Hola", "3");
           Log.d("Hola", "4");
           //Parse JSON
           if (con.getResponseCode() == HttpURLConnection.HTTP_OK) {
              String json = getJSON(con.getInputStream());
              JSONArray obj = new JSONArray(json);
              for(int i=0; i < obj.length(); ++i) {
                  JSONObject jsonobject = obj.getJSONObject(i);
                  String nickname       = jsonobject.getString("nickname");
                  String nombre    = jsonobject.getString("nombre");
                  String clave  = jsonobject.getString("clave");
                  String imagen = null;
                  String bio = jsonobject.getString("bio");
                  String correo = jsonobject.getString("correo");
                  String posicion = jsonobject.getString("posicion");
                  String timestamp = jsonobject.getString("timestamp");
              }
           }
           Log.d("Hola", "8");
       }
       catch (Exception e){
           Log.e("ErrorConnection", e.getMessage());
       }
       return response;
   }

    public String makePostRequest(HashMap<String, String> postDataParams, String stringURL) throws Exception{

        URL url;
        String response = "";
        try {
            url = new URL(stringURL);

            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setRequestProperty("http.agent", "");
            conn.setDoOutput(true);

            OutputStream os = conn.getOutputStream();
            BufferedWriter writer = new BufferedWriter(
                    new OutputStreamWriter(os, "UTF-8"));
            writer.write(getPostDataString(postDataParams));

            writer.flush();
            writer.close();
            os.close();
            int responseCode=conn.getResponseCode();

            if (responseCode == HttpsURLConnection.HTTP_OK) {
                String line;
                BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                while ((line=br.readLine()) != null) {
                    response+=line;
                }
            }
            else {
                response="";

            }
        } catch (Exception e) {
            e.printStackTrace();
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

    public String getPostDataString(HashMap<String, String> params) throws UnsupportedEncodingException{
        StringBuilder result = new StringBuilder();
        boolean first = true;
        for(Map.Entry<String, String> entry : params.entrySet()){
            if (first) {
                first = false;
            }
            else {
                result.append("&");
            }
            result.append(URLEncoder.encode(entry.getKey(), "UTF-8"));
            result.append("=");
            result.append(URLEncoder.encode(entry.getValue(), "UTF-8"));
        }

        return result.toString();
    }

    public static String getGetDataString(HashMap<String, String> params) throws UnsupportedEncodingException{
        StringBuilder result = new StringBuilder();
        boolean first = true;
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
        //
        final ResultReceiver receiver = intent.getParcelableExtra("mReceiver");
        /*Se llena este String con la primera parte
         del URL (Hasta el controlador)
         "http://www.yomasdiez.com/index.php/api/Usuario/Jugador"
        */
        urlbase.append(this.urlapi);
        /*Se recibe el resto del URL de la consulta con el formato del GET
          Y se le hace agrega al link del controlador
          "?nickname=Aleochoam&edad=19"
        */
        urlbase.append(intent.getStringExtra("urlget"));
        /*
            Se recibe el tipo de Http Request que se va a hacer
         */
        type = intent.getStringExtra("type");

        /*
            Dependiendo del tipo del Http Request que llegue
            Se hace la operacion respectiva.
         */
        try{
            if(type.equals("GET")){
                String getResult = makeGetRequest(urlbase.toString());
                bundle.putString("GetResponse", getResult);
                /*
                    El método send devuelve al thread principal el
                    resultado del Get en un bundle.
                 */
                receiver.send(0, bundle);
            }
        }catch (Exception e){
            Log.e("ErrorIntent", e.getMessage());
        }
    }
}
