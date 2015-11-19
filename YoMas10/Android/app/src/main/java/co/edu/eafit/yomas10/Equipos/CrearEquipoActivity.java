package co.edu.eafit.yomas10.Equipos;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;

import co.edu.eafit.yomas10.Jugador.Jugador;
import co.edu.eafit.yomas10.Jugador.SeleccionarAmigosActivity;
import co.edu.eafit.yomas10.MainActivity;
import co.edu.eafit.yomas10.MyApplication;
import co.edu.eafit.yomas10.R;
import co.edu.eafit.yomas10.Util.Connection.Http;
import co.edu.eafit.yomas10.Util.Connection.HttpBridge;
import co.edu.eafit.yomas10.Util.Connection.Receiver;
import co.edu.eafit.yomas10.Util.ParseNotificationSender;

public class CrearEquipoActivity extends AppCompatActivity implements Receiver {

    private Equipo equipo;
    private final static int REQUEST_AMIGOS = 1;
    private ArrayList<Jugador> jugadores;
    private ArrayList<String> nuevosJugadores;
    private ArrayAdapter<Jugador> mAdapter;

    private EditText nombreEquipo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crear_equipo);

        nombreEquipo = (EditText) findViewById(R.id.nombreEquipo);

        ListView integrantesLV = (ListView) findViewById(R.id.listaJugadores);
        jugadores = new ArrayList<>();
        jugadores.add(((MyApplication)getApplicationContext()).getUser());
        mAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, jugadores);

        Button btn = new Button(getApplicationContext());
        btn.setText("Agregar jugador");
        integrantesLV.addFooterView(btn);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in = new Intent(getApplicationContext(), SeleccionarAmigosActivity.class);
                startActivityForResult(in, REQUEST_AMIGOS);
            }
        });

        integrantesLV.setAdapter(mAdapter);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK){
            nuevosJugadores = data.getExtras().getStringArrayList("JUGADORES");

            for (String jugador: nuevosJugadores){
                jugadores.add(((MyApplication)getApplicationContext()).getUser().findAmigo(jugador));
            }
            mAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_crear_equipo, menu);
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

        if (id == R.id.action_crear){
            crearEquipo();
            startActivity(new Intent(this, MainActivity.class));
            //finish();
        }

        return super.onOptionsItemSelected(item);
    }

    public void crearEquipo(){
        if (nombreEquipo.getText().toString().equals("")){
            Toast.makeText(this, "El nombre del equipo es invalido", Toast.LENGTH_LONG).show();
            return;
        }
        else {
            Jugador user = ((MyApplication)getApplicationContext()).getUser();
            equipo = user.crearEquipo(nombreEquipo.getText().toString());
            equipo.agregarJugadores(jugadores);

            for (Jugador jugador: jugadores) {
                try{
                    ParseNotificationSender.sendTeamInvitation(jugador.getUsername(),
                            nombreEquipo.getText().toString(), user.getUsername(), jugadores, equipo.getId());
                }catch (JSONException e){
                    Log.e("PARSE NOTIFICATION", "Error enviado la notificacion");
                }
            }
            updateDBEquipo(equipo);
            //updateDBJugadores(equipo);
            Toast.makeText(this, "Se han invitado a los jugadores", Toast.LENGTH_LONG).show();
            user.agregarEquipo(equipo);
        }


    }

    public void updateDBEquipo(Equipo equipo){
        HashMap<String, String> map = new HashMap<>();
        map.put("nombre", equipo.getNombre());
        map.put("capitan", equipo.getCapitan().getUsername());

        try {
            startService(HttpBridge.startWorking(this, map, this, Http.EQUIPO));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }

    public void updateDBJugadores(Equipo equipo){
        for (int i = 0; i < equipo.getIntegrantes().size(); i++) {
            HashMap map = new HashMap();
            map.put("idEquipo", this.equipo.getId() +"");
            map.put("nickname", this.equipo.getIntegrantes().get(i).getUsername());

            try {
                startService(HttpBridge.startWorking(this, map, this, Http.INTEGRANTES));
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void onReceiveResult(int resultCode, Bundle resultData) {
        try {
            JSONObject json = (new JSONArray(resultData.getString("GetResponse"))).getJSONObject(0);
            if (json.has("capitan")){
                int id = json.getInt("idEquipo");
                equipo.setId(id);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
