package co.edu.eafit.yomas10.Partidos;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import java.util.ArrayList;

import co.edu.eafit.yomas10.Jugador.Jugador;
import co.edu.eafit.yomas10.R;

public class CrearPartidoActivity extends AppCompatActivity {

    private EditText fechaPartido, horaPartido;
    private ListView jugadoresLV;
    private ArrayList<Jugador> jugadores;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crear_partido);

        fechaPartido = (EditText) findViewById(R.id.fechaPartido);
        horaPartido = (EditText) findViewById(R.id.horaPartido);
        jugadoresLV = (ListView) findViewById(R.id.jugadores);

        ArrayAdapter mAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, jugadores);
        jugadoresLV.setAdapter(mAdapter);

        Button btn = new Button(getApplicationContext());
        btn.setText("Agregar Jugador");
        jugadoresLV.addFooterView(btn);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                

            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_crear_partido, menu);
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
