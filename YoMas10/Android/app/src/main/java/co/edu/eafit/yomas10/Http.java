package co.edu.eafit.yomas10;


import android.accounts.NetworkErrorException;
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

   String url = "www.yomasdiez.com/index.php/api/Usuario/Jugadores";

   private String makeGetRequest(String uname) throws IOException, JSONException, NetworkErrorException{

       StringBuffer urlS = new StringBuffer();
       urlS.append(this.url);
       urlS.append(uname);
       URL url = new URL(urlS.toString());
       HttpURLConnection con = (HttpURLConnection)url.openConnection();
       String response;

       // Request Header
       con.setRequestMethod("GET");
       con.setRequestProperty("Accept", "application/json");
       con.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
       con.setRequestProperty("http.agent", "");

       int responseCode = con.getResponseCode();

       // Parse JSON
       if(responseCode == 200 || responseCode == 201){
           String json = getJSON(con.getInputStream());
           JSONObject obj = new JSONObject(json);
           response = obj.getString("user_t");
       }
       else{
           NetworkErrorException e = new NetworkErrorException("Could not get JSON");
           throw e;
       }

       return response;
    }
    public String makePostRequest(HashMap<String, String> postDataParams, String stringURL) throws Exception{

        URL url;
        String response = "";
        try {
            url = new URL(stringURL);

            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setReadTimeout(15000);
            conn.setConnectTimeout(15000);
            conn.setRequestMethod("POST");
            conn.setDoInput(true);
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

    private String getJSON(InputStream inputStream) throws IOException {
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
            if (first)
                first = false;
            else
                result.append("&");
            result.append(URLEncoder.encode(entry.getKey(), "UTF-8"));
            result.append("=");
            result.append(URLEncoder.encode(entry.getValue(), "UTF-8"));
        }

        return result.toString();
    }
}
