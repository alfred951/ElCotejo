package co.edu.eafit.yomas10.Partidos.Casual;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.HashMap;

import co.edu.eafit.yomas10.Util.Connection.Http;
import co.edu.eafit.yomas10.Jugador.Jugador;
import co.edu.eafit.yomas10.Jugador.PerfilExterno;
import co.edu.eafit.yomas10.MainActivity;
import co.edu.eafit.yomas10.R;

public class InvitacionPartidoCasualActivity extends AppCompatActivity {

    private TextView horaPartido, fechaPartido, cancha;
    private ListView participantesLV;

    private PartidoCasual partido;

    public Http http;
    String urljugador = "www.yomasdiez.com/index.php/api/Usuario/Jugadores";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_invitacion_partido_casual);

        horaPartido = (TextView) findViewById(R.id.horaPartido);
        fechaPartido = (TextView) findViewById(R.id.fechaPartido);
        cancha = (TextView) findViewById(R.id.canchaPartido);
        participantesLV = (ListView) findViewById(R.id.integrantes);

        partido = (PartidoCasual) getIntent().getExtras().getSerializable("PARTIDO");
        horaPartido.setText(partido.getHora());
        fechaPartido.setText(partido.getFecha());
        cancha.setText(partido.getCancha());

        participantesLV.setAdapter(new ArrayAdapter<>
                (this, android.R.layout.simple_list_item_1, partido.getIntegrantes()));

        participantesLV.setOnItemClickListener(new AdapterView.OnItemClickListener() {
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
        getMenuInflater().inflate(R.menu.menu_invitacion_partido_casual, menu);
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

    public void aceptarPartido(View view){

        try {
            //TODO: Cambiar Todo esto
            Toast.makeText(this, "Te has inscrito al partido", Toast.LENGTH_SHORT)
                    .show();
            MainActivity.getUser().agregarPartido(partido);
            HashMap<String, String> jugador = new HashMap<>();
            jugador.put("nickname", "aleochoam");
            jugador.put("nombre","Santiago Saravia");
            jugador.put("posicion","Arquero");
            jugador.put("bio","Lo que sea");
            this.http.makePostRequest(jugador, urljugador);
            //TODO: agregarse al equipo en la base de datos
            //equipo.agregarJugador(StaticUser.jugador);
            //TODO: Notificar la aceptacion
        }
        catch(Exception e) {
        }
        Intent in = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(in);
        finish();

    }

    public void rechazarPartido(View view){
        //TODO notificar el rechazo
        Intent in = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(in);
        finish();
    }
}
