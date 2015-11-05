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

import co.edu.eafit.yomas10.Jugador.Jugador;
import co.edu.eafit.yomas10.Jugador.PerfilExterno;
import co.edu.eafit.yomas10.R;

public class PartidoCasualActivity extends AppCompatActivity {

    private TextView horaPartido, fechaPartido, cancha;
    private ListView participantesLV;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_partido_casual);

        horaPartido = (TextView) findViewById(R.id.horaPartido);
        fechaPartido = (TextView) findViewById(R.id.fechaPartido);
        cancha = (TextView) findViewById(R.id.canchaPartido);
        participantesLV = (ListView) findViewById(R.id.integrantes);

        PartidoCasual partido = (PartidoCasual) getIntent().getExtras().getSerializable("PARTIDO");
        horaPartido.setText(partido.getHora());
        fechaPartido.setText(partido.getFecha());
        cancha.setText(partido.getCancha());

        participantesLV.setAdapter(new ArrayAdapter<>
                (this, android.R.layout.simple_list_item_1, partido.getIntegrantes()));

        participantesLV.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Jugador jugador = (Jugador) parent.getItemAtPosition(position);
                //Jugador amigo = (Jugador) parent.getItemAtPosition(position); //cambiar
                Bundle bn = new Bundle();
                bn.putSerializable("JUGADOR", jugador);
//                bn.putString("USERNAME", amigo.toString());
//                bn.putString("NOMBRE", amigo.getNombre());
//                bn.putString("POSICION", amigo.getPosicion());
//                bn.putString("BIO", amigo.getBio());
//                bn.putString("CORREO", amigo.getCorreo());


                Intent in = new Intent(getApplicationContext(), PerfilExterno.class);
                in.putExtras(bn);
                startActivity(in);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_partido_casual, menu);
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
}
