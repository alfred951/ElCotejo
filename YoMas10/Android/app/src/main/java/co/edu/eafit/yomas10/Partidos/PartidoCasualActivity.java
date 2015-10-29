package co.edu.eafit.yomas10.Partidos;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

import co.edu.eafit.yomas10.Jugador.Jugador;
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
        participantesLV = (ListView) findViewById(R.id.jugadores);

        Bundle bn = getIntent().getExtras();
        horaPartido.setText(bn.getString("horaPartido"));
        fechaPartido.setText(bn.getString("fechaPartido"));
        cancha.setText(bn.getString("cancha"));

        ArrayList<String> jugadores = bn.getStringArrayList("jugadores");
        participantesLV.setAdapter(new ArrayAdapter<String>
                (this, android.R.layout.simple_list_item_1, jugadores));

        participantesLV.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                parent.getItemAtPosition(position)
                // STOPSHIP: 29/10/2015  
            }
        });
        //TODO setOnListItemClick

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
