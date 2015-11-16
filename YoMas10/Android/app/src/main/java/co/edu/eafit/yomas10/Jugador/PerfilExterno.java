package co.edu.eafit.yomas10.Jugador;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;

import co.edu.eafit.yomas10.R;
import co.edu.eafit.yomas10.Util.Connection.HttpBridge;
import co.edu.eafit.yomas10.Util.Connection.Receiver;

/**
 * Perfil de una persona diferente al usuario mismo
 */

//TODO
public class PerfilExterno extends AppCompatActivity implements Receiver{

    Jugador jugador;
    private ImageView profilePic;
    private TextView name;
    private TextView username;
    private TextView posicion;
    private TextView userBio;
    private TextView correo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perfil_externo);

        profilePic = (ImageView)findViewById(R.id.usrPic);
        name       = (TextView) findViewById(R.id.name);
        username   = (TextView) findViewById(R.id.username);
        posicion   = (TextView) findViewById(R.id.posicion);
        userBio    = (TextView) findViewById(R.id.userBio);
        correo     = (TextView) findViewById(R.id.email);

        //TODO sacar los atributos con el username
        jugador = (Jugador) getIntent().getExtras().getSerializable("JUGADOR");

        HashMap<String, String> map = new HashMap<>();
        map.put("nickname", jugador.getUsername());
        try {
            startService(HttpBridge.startWorking(this, map, this));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        //profilePic.setImageURI(bn.getString("USERNAME"));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_perfil_externo, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    @Override
    public void onReceiveResult(int resultCode, Bundle resultData) {
        switch (resultCode){
            case 0:
                try {
                    JSONObject json = new JSONObject(resultData.getString("GetResponse"));

                    try {
                        jugador.setNombre(json.getString("nombre"));
                        jugador.setBio(json.getString("bio"));
                        jugador.setCorreo(json.getString("correo"));
                        jugador.setPosicion(json.getString("posicion"));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }


                    name.setText(jugador.getNombre());
                    username.setText(jugador.getUsername());
                    posicion.setText(jugador.getPosicion());
                    userBio.setText(jugador.getBio());
                    correo.setText(jugador.getCorreo());
                } catch (JSONException e) {
                    e.printStackTrace();
                }
        }
    }
}
