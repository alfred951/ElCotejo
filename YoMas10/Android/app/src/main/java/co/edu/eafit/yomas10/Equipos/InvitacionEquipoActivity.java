package co.edu.eafit.yomas10.Equipos;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import co.edu.eafit.yomas10.Equipos.Equipo;
import co.edu.eafit.yomas10.Jugador.Jugador;
import co.edu.eafit.yomas10.MainActivity;
import co.edu.eafit.yomas10.R;

/**
 * Activity que se muestra cuando se recibe una notificacion para unirse a un equipo
 */
public class InvitacionEquipoActivity extends AppCompatActivity {

    private TextView equipo, capitan;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_invitacion_equipo);

        Bundle bn = getIntent().getExtras();

        equipo = (TextView) findViewById(R.id.equipo);
        capitan = (TextView) findViewById(R.id.capitan);

        equipo.setText(bn.getString("NOMBRE"));
        capitan.setText(bn.getString("CAPITAN"));


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

        //TODO: Cambiar Todo esto
        Toast.makeText(this, "Te has inscrito a " + equipo.getText().toString(),Toast.LENGTH_SHORT )
                .show();

        Equipo equipo = new Equipo(this.equipo.getText().toString(),
                new Jugador(this.capitan.getText().toString()));
        MainActivity.getUser().agregarEquipo(equipo);
        //TODO: agregarse al equipo en la base de datos
        //equipo.agregarJugador(StaticUser.jugador);
        //TODO: Notificar la aceptacion
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
