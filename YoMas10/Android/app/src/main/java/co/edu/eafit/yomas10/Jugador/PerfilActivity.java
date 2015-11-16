package co.edu.eafit.yomas10.Jugador;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.JsonWriter;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;

import co.edu.eafit.yomas10.Jugador.EditarPerfilActivity;
import co.edu.eafit.yomas10.MainActivity;
import co.edu.eafit.yomas10.MyApplication;
import co.edu.eafit.yomas10.R;
import co.edu.eafit.yomas10.Util.Connection.HttpBridge;
import co.edu.eafit.yomas10.Util.Connection.Receiver;

public class PerfilActivity extends AppCompatActivity implements Receiver{

    Jugador jugador;
    private ImageView profilePic;
    private TextView name;
    private TextView username;
    private TextView posicion;
    private TextView userBio;
    private TextView correo;

    private ArrayList<String> nombreEquipos;
    private final Context ctx = this;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perfil);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        profilePic = (ImageView)findViewById(R.id.usrPic);
        name       = (TextView) findViewById(R.id.name);
        username   = (TextView) findViewById(R.id.username);
        posicion   = (TextView) findViewById(R.id.posicion);
        userBio    = (TextView) findViewById(R.id.userBio);
        correo     = (TextView) findViewById(R.id.email);

        //jugador = (Jugador) getIntent().getExtras().getSerializable("JUGADOR");
        jugador = ((MyApplication)getApplicationContext()).getUser();
        HashMap<String, String> map = new HashMap<>();
        map.put("nickname", jugador.getUsername());
        try{
            startService(HttpBridge.startWorking(this, map, this, "Jugador"));
        }
        catch (UnsupportedEncodingException e){
            e.printStackTrace();
        }
    }

    public void onActivityResult(int reqCode, int resCode, Intent data){
        if(resCode == RESULT_OK){
            if (reqCode == 1) {
                profilePic.setImageURI(data.getData());
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_perfil, menu);
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
        }else if (id == R.id.action_edit) {
            Bundle bn = new Bundle();
            bn.putString("NOMBRE", name.getText().toString());
            bn.putString("POSICION", posicion.getText().toString());
            bn.putString("BIO", userBio.getText().toString());

            Intent in = new Intent(this, EditarPerfilActivity.class);
            in.putExtras(bn);
            startActivity(in);
            finish();
        }else if (id == R.id.action_friends) {
            startActivity(new Intent(this, AmigosNActivity.class));
        }

        return super.onOptionsItemSelected(item);
    }


    public void onBackPressed(){
        startActivity(new Intent(this, MainActivity.class));
    }

    @Override
    public void onReceiveResult(int resultCode, Bundle resultData) {
        switch (resultCode){
            case 0:
                try{
                    JSONObject json = new JSONObject(resultData.getString("GetResponse"));

                    try{
                        jugador.setNombre(json.getString("nombre"));
                        jugador.setBio(json.getString("bio"));
                        jugador.setCorreo(json.getString("correo"));
                        jugador.setPosicion(json.getString("posicion"));
                    }
                    catch (JSONException e){
                        e.printStackTrace();
                    }

                    name.setText(jugador.getNombre());
                    username.setText(jugador.getUsername());
                    posicion.setText(jugador.getPosicion());
                    userBio.setText(jugador.getBio());
                    correo.setText(jugador.getCorreo());
                }
                catch(JSONException e){
                    e.printStackTrace();
                }

        }
    }
}

