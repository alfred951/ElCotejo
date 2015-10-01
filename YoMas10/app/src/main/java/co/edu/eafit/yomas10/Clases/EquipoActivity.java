package co.edu.eafit.yomas10.Clases;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import co.edu.eafit.yomas10.MainActivity;
import co.edu.eafit.yomas10.R;

public class EquipoActivity extends AppCompatActivity {

    private ListView listaJugadores;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_equipo);

        ArrayAdapter<Jugador> adapter = new ArrayAdapter<Jugador>
                (this, android.R.layout.simple_list_item_1, MainActivity.jugadores);

        listaJugadores = (ListView) findViewById(R.id.listaJugadores);
        listaJugadores.setAdapter(adapter);
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
