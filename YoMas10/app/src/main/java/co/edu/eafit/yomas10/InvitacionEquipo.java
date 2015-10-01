package co.edu.eafit.yomas10;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import co.edu.eafit.yomas10.Clases.Equipo;
import co.edu.eafit.yomas10.Clases.Jugador;
import co.edu.eafit.yomas10.Helpers.NotificationCreator;

public class InvitacionEquipo extends AppCompatActivity {

    private TextView equipoTV;
    private Equipo equipo;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_invitacion_equipo);

        Bundle bn = getIntent().getExtras();

        equipoTV = (TextView) findViewById(R.id.equipo);
        equipoTV.setText(bn.getString("NOMBRE"));

        equipo = new Equipo(bn.getString("NOMBRE"), new Jugador(bn.getString("CAPITAN")));
        equipo.getInfoDB();
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
        MainActivity.jugador.agregarEquipo(equipo);
        //TODO: agregarse al equipo en la base de datos
        equipo.agregarJugador(MainActivity.jugador);
        NotificationCreator.sendParseNotification();

    }

    public void rechazarEquipo(View view){

    }
}
