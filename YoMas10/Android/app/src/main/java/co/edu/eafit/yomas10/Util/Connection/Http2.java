package co.edu.eafit.yomas10.Util.Connection;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

/**
 * Created by Usuario on 14/11/2015.
 */
public class Http2 {

    private RequestQueue requestQueue;

    private String urlTodos = "http://yomasdiez.com/index.php/api/Usuario/jugadores";
    private String urlUser = "http://yomasdiez.com/index.php/api/Usuario/jugador/nickname/";

    private JSONObject user = new JSONObject();
    private JSONArray allUsers = new JSONArray();

    public JSONArray getAllUsersMethod(Context ctx){
        requestQueue = Volley.newRequestQueue(ctx.getApplicationContext());
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(
                Request.Method.GET, urlTodos,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        allUsers = response;
                    }
                },

                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("http get", error.getMessage());
                    }
                }
        );

        requestQueue.add(jsonArrayRequest);
        return allUsers;
    }

    public JSONObject getUserMethod(String username, Context ctx) {

        requestQueue = Volley.newRequestQueue(ctx.getApplicationContext());
        JsonArrayRequest jsonArrayRequest= new JsonArrayRequest(Request.Method.GET, urlUser+username,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        for (int i = 0; i < response.length(); i++) {
                            try {
                                Log.d("json info", response.length() + "");
                                Log.d("http debuggin", response.getJSONObject(i).toString());
                                user = response.getJSONObject(i);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("http request", error.getMessage());
                    }
                }
        );
        requestQueue.add(jsonArrayRequest);
        return user;
    }

    public void postUserMethod(HashMap<String, String> map, Context ctx){
        requestQueue = Volley.newRequestQueue(ctx.getApplicationContext());
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST,
                urlTodos,
                new JSONObject(map),
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.i("http post", "succesful");
                    }
                },

                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.i("http post", "failed");
                    }
                }
        );
        requestQueue.add(jsonObjectRequest);
    }


}
