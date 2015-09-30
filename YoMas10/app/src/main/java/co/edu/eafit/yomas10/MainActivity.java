package co.edu.eafit.yomas10;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.parse.Parse;
import com.parse.ParseInstallation;
import com.parse.ParsePush;

import co.edu.eafit.yomas10.Clases.Jugador;


public class MainActivity extends AppCompatActivity {

    EditText equipo;
    public static Jugador jugador;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        jugador = new Jugador("Alejandro");
        jugador.setUsername("Aleochoam");
        jugador.setPosicion("Portero");
        jugador.setBio("Mido 1.80, he jugado en el Nacional y tengo 20 años");

        try {
            Parse.initialize(this, getString(R.string.app_id), getString(R.string.client_key));
            ParseInstallation.getCurrentInstallation().saveInBackground();
        }catch (Exception e){}

        final Context ctx = this;
        equipo = (EditText) findViewById(R.id.equipo);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
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
        }else if (id == R.id.action_newNoti){
            Intent in = new Intent(MainActivity.this, CreateNotificacionActivity.class);
            startActivity(in);
        }else if (id == R.id.action_profile){
            Intent in = new Intent(MainActivity.this, PerfilActivity.class);
            startActivity(in);
        }

        return super.onOptionsItemSelected(item);
    }

    public void suscribeNewChannel(View view) {
        ParsePush.subscribeInBackground(equipo.getText().toString().toLowerCase());
        Toast.makeText(this, getString(R.string.suscripcion) + " " +equipo.getText().toString(),Toast.LENGTH_LONG).show();
    }
}

//clave de Parse LO5XvpbqZL7cLu3vvKWpKZbTr9b3Cc21DU1pd7h3
