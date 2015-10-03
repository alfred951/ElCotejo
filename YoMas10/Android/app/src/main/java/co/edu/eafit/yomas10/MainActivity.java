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

import java.util.LinkedList;

import co.edu.eafit.yomas10.Clases.Equipo;
import co.edu.eafit.yomas10.Clases.Jugador;
import co.edu.eafit.yomas10.Helpers.StaticUser;


public class MainActivity extends AppCompatActivity {

    EditText canal;
    private Jugador user;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        StaticUser.initialize();

        final Context ctx = this;
        canal = (EditText) findViewById(R.id.equipo);

        try {
            Parse.initialize(this, getString(R.string.app_id), getString(R.string.client_key));
            ParseInstallation.getCurrentInstallation().saveInBackground();
            ParsePush.subscribeInBackground(user.getUsername());
        }catch (Exception e){}
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
        ParsePush.subscribeInBackground(canal.getText().toString().toLowerCase());
        Toast.makeText(this, getString(R.string.suscripcion) + " " +canal.getText().toString(),Toast.LENGTH_LONG).show();
    }
}

//clave de Parse LO5XvpbqZL7cLu3vvKWpKZbTr9b3Cc21DU1pd7h3
