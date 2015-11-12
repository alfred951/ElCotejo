package co.edu.eafit.yomas10;


import android.accounts.NetworkErrorException;
import android.app.IntentService;
import android.content.Intent;
import android.os.Bundle;
import android.os.ResultReceiver;
import android.util.Log;

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
import org.json.JSONException;
import org.json.JSONObject;

import javax.net.ssl.HttpsURLConnection;

public class Http{

   public static String makeGetRequest(HashMap<String, String> map) {
       String response = "";
       StringBuffer urlS = new StringBuffer();
       urlS.append("http://www.yomasdiez.com/index.php/api/Usuario/Jugador");
       try {
           Log.d("Hola", "1");
           URL url = new URL(urlS.toString());
           urlS.append(getGetDataString(map));
           HttpURLConnection con = (HttpURLConnection) url.openConnection();

           Log.d("Hola", "2");
           // Request Header
           con.setRequestMethod("GET");
           con.setRequestProperty("Accept", "application/json");
           con.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
           con.setRequestProperty("http.agent", "");

           Log.d("Hola", "3");
           int responseCode = con.getResponseCode();

           Log.d("Hola", "4");
           // Parse JSON
           if (responseCode == 200 || responseCode == 201) {
               String json = getJSON(con.getInputStream());
               JSONObject obj = new JSONObject(json);
               response = obj.getString("user_t");
           }
           Log.d("Hola", "5");
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
                BufferedReader br=new BufferedReader(new InputStreamReader(conn.getInputStream()));
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

    private static String getJSON(InputStream inputStream) throws IOException {
        StringBuffer json = new StringBuffer();
        BufferedReader in = new BufferedReader(
                new InputStreamReader(inputStream));

        String input;
        while ((input = in.readLine()) != null){
            json.append(input);
        }
        in.close();

        return json.toString();
    }

    private String getPostDataString(HashMap<String, String> params) throws UnsupportedEncodingException{
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

    private static String getGetDataString(HashMap<String, String> params) throws UnsupportedEncodingException{
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
}
