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

import org.json.JSONException;

import java.util.ArrayList;

import co.edu.eafit.yomas10.Jugador.Jugador;
import co.edu.eafit.yomas10.Jugador.SeleccionarAmigosActivity;
import co.edu.eafit.yomas10.MainActivity;
import co.edu.eafit.yomas10.R;
import co.edu.eafit.yomas10.Util.ParseNotificationSender;

public class CrearEquipoActivity extends AppCompatActivity {

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
                jugadores.add(MainActivity.getUser().findAmigo(jugador));
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
            finish();
        }

        return super.onOptionsItemSelected(item);
    }

    public void crearEquipo(){
        if (nombreEquipo.getText().toString().equals("")){
            Toast.makeText(this, "El nombre del equipo es invalido", Toast.LENGTH_LONG).show();
            return;
        }
        else {
            Jugador user = MainActivity.getUser();
            Equipo equipo = user.crearEquipo(nombreEquipo.getText().toString());
            equipo.agregarJugadores(jugadores);

            for (Jugador jugador: jugadores) {
                try{
                    ParseNotificationSender.sendTeamInvitation(jugador.getUsername(),
                            nombreEquipo.getText().toString(), user.getUsername(), jugadores);
                }catch (JSONException e){
                    Log.e("PARSE NOTIFICATION", "Error enviado la notificacion");
                }
            }
            Toast.makeText(this, "Se han invitado a los jugadores", Toast.LENGTH_LONG).show();
            user.agregarEquipo(equipo);
        }


    }
}
