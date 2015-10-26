package co.edu.eafit.yomas10.Partidos;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

import co.edu.eafit.yomas10.Jugador.Jugador;
import co.edu.eafit.yomas10.Jugador.SeleccionarAmigosActivity;
import co.edu.eafit.yomas10.MainActivity;
import co.edu.eafit.yomas10.R;

public class CrearPartidoActivity extends AppCompatActivity {

    private EditText fechaPartido, horaPartido, canchaPartido;
    private ListView jugadoresLV;
    private ArrayList<String> jugadores;
    private ArrayAdapter<String> mAdapter;
    private final static int REQUEST_AMIGOS = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crear_partido);

        fechaPartido = (EditText) findViewById(R.id.fechaPartido);
        horaPartido = (EditText) findViewById(R.id.horaPartido);
        canchaPartido = (EditText) findViewById(R.id.canchaPartido);
        jugadoresLV = (ListView) findViewById(R.id.jugadores);

        jugadores = new ArrayList<>();

        mAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, jugadores);
        //jugadoresLV.setEmptyView((TextView) findViewById(R.id.emptyText));


        Button btn = new Button(getApplicationContext());
        btn.setText("Agregar Jugadores");
        jugadoresLV.addFooterView(btn);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in = new Intent(getApplicationContext(), SeleccionarAmigosActivity.class);
                startActivityForResult(in, REQUEST_AMIGOS);

            }
        });
        jugadoresLV.setAdapter(mAdapter);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK){
            ArrayList<String> nuevosJugadores = data.getExtras().getStringArrayList("JUGADORES");

            for (int i = 0; i<nuevosJugadores.size();i++){
                mAdapter.add(nuevosJugadores.get(i));
            }

            mAdapter.notifyDataSetChanged();
        }
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
        }else if (id == R.id.crearPartido){
            Partido partido = new PartidoCasual(fechaPartido.getText().toString(),
                    horaPartido.getText().toString(), canchaPartido.getText().toString());
            MainActivity.getUser().agregarPartido(partido);
            startActivity(new Intent(this, MainActivity.class));
            finish();
        }

        return super.onOptionsItemSelected(item);
    }
}
