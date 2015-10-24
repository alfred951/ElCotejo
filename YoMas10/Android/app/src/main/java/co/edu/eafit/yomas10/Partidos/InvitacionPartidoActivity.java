package co.edu.eafit.yomas10.Partidos;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import co.edu.eafit.yomas10.R;

/**
 * Activity con la informacion del partido que la llamo
 */
public class InvitacionPartidoActivity extends AppCompatActivity {
    //TODo meter el lugar

    private TextView fecha, equipo1, equipo2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_invitacion_partido);

        fecha = (TextView) findViewById(R.id.fecha);
        equipo1 = (TextView) findViewById(R.id.equipo1);
        equipo2 = (TextView) findViewById(R.id.equipo2);

        fecha.setText(getIntent().getExtras().getString("FECHA"));
        equipo1.setText(getIntent().getExtras().getString("EQUIPO1"));
        equipo2.setText(getIntent().getExtras().getString("EQUIPO2"));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_partido, menu);
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
        //TODO
    }

    public void rechazarPartido(View viw){
        //TODO
    }
}
