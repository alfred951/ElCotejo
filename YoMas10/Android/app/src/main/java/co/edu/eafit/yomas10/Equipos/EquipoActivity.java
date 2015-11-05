package co.edu.eafit.yomas10.Equipos;

import android.content.Context;
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

/**
 * Activity con la informacion del equipo que lo haya llamado
 */
public class EquipoActivity extends AppCompatActivity {

    private Context ctx = this;
    private TextView capitan;
    private ListView listaJugadores;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_equipo);

        capitan = (TextView) findViewById(R.id.capitan);
        listaJugadores = (ListView) findViewById(R.id.listaJugadores);

        //TODO: Sacar la info del la DB

        final Equipo equipo = (Equipo) getIntent().getSerializableExtra("EQUIPO");
        setTitle(equipo.getNombre());

        capitan.setText(equipo.getCapitan().getNombre());
        capitan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in = new Intent(getApplicationContext(), PerfilExterno.class);
                in.putExtra("JUGADOR", equipo.getCapitan());
                startActivity(in);
            }
        });

        ArrayAdapter<Jugador> adapter = new ArrayAdapter<>
                (this, android.R.layout.simple_list_item_1, equipo.getIntegrantes());

        listaJugadores.setAdapter(adapter);

        listaJugadores.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Jugador jugador = (Jugador) parent.getAdapter().getItem(position);

                Bundle bn = new Bundle();
                bn.putSerializable("JUGADOR", jugador);

                Intent in = new Intent(ctx, PerfilExterno.class);
                in.putExtras(bn);
                startActivity(in);
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_equipo, menu);
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
