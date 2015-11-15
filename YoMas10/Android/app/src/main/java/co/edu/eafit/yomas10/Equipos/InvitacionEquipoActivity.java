package co.edu.eafit.yomas10.Equipos;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.HashMap;

import co.edu.eafit.yomas10.Util.Http;
import co.edu.eafit.yomas10.Jugador.Jugador;
import co.edu.eafit.yomas10.Jugador.PerfilExterno;
import co.edu.eafit.yomas10.MainActivity;
import co.edu.eafit.yomas10.R;

/**
 * Activity que se muestra cuando se recibe una notificacion para unirse a un equipo
 */
public class InvitacionEquipoActivity extends AppCompatActivity {

    private TextView equipoTV, capitanTV;
    private ListView jugadoresLV;
    private Equipo equipo;

    public Http http;
    String urljugador = "www.yomasdiez.com/index.php/api/Usuario/Jugador";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_invitacion_equipo);

        equipoTV = (TextView) findViewById(R.id.equipo);
        capitanTV = (TextView) findViewById(R.id.capitan);
        jugadoresLV = (ListView) findViewById(R.id.jugadores);

        equipo = (Equipo) getIntent().getExtras().getSerializable("EQUIPO");

        equipoTV.setText(equipo.getNombre());
        capitanTV.setText(equipo.getCapitan().getNombre());
        jugadoresLV.setAdapter(new ArrayAdapter<Jugador>(this, android.R.layout.simple_list_item_1,
                equipo.getIntegrantes()));

        jugadoresLV.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Jugador jugador = (Jugador) parent.getItemAtPosition(position);
                Bundle bn = new Bundle();
                bn.putSerializable("JUGADOR", jugador);

                Intent in = new Intent(getApplicationContext(), PerfilExterno.class);
                in.putExtras(bn);
                startActivity(in);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_invitacion_equipo, menu);
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

    public void aceptarEquipo(View view){

        try {
            //TODO: Cambiar Todo esto
            Toast.makeText(this, "Te has inscrito a " + equipoTV.getText().toString(), Toast.LENGTH_SHORT)
                    .show();
            Equipo equipo = new Equipo(this.equipoTV.getText().toString(),
                    new Jugador(this.capitanTV.getText().toString()));
            MainActivity.getUser().agregarEquipo(equipo);
            HashMap<String, String> jugador = new HashMap<>();
            jugador.put("nickname", "Aleochoam");
            //Log.d("try", this.http.makeGetRequest(jugador));
            //TODO: agregarse al equipo en la base de datos
            //equipo.agregarJugador(StaticUser.jugador);
            //TODO: Notificar la aceptacion
        }
        catch(Exception e) {
            Log.e("Error", e.getMessage());
        }
        Intent in = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(in);
        finish();

    }

    public void rechazarEquipo(View view){
        //TODO notificar el rechazo
        Intent in = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(in);
        finish();
    }
}
