package co.edu.eafit.yomas10;


import android.accounts.NetworkErrorException;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import org.json.JSONException;
import org.json.JSONObject;

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

    private boolean makePostRequest(String urlParams) throws Exception{

        URL url = new URL(this.url.toString());
        HttpURLConnection con = (HttpURLConnection)url.openConnection();

        // Request Header
        con.setRequestMethod("POST");
        con.setRequestProperty("http.agent", "");

        //Send post request
        con.setDoOutput(true);
        DataOutputStream dos = new DataOutputStream(con.getOutputStream());
        dos.writeBytes(urlParams);
        dos.flush();
        dos.close();

        int responseCode = con.getResponseCode();

        // Return true if request was done successfully
        return responseCode == 200 || responseCode == 201;
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
}
