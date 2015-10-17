package co.edu.eafit.yomas10;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import co.edu.eafit.yomas10.Clases.Equipo;
import co.edu.eafit.yomas10.Clases.Jugador;

public class PerfilActivity extends AppCompatActivity {

    private ImageView profilePic;
    private TextView name;
    private TextView username;
    private TextView posicion;
    private TextView userBio;
    private TextView correo;

    private ArrayList<String> nombreEquipos;
    private final Context ctx = this;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perfil);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        profilePic = (ImageView)findViewById(R.id.usrPic);
        name       = (TextView) findViewById(R.id.name);
        username   = (TextView) findViewById(R.id.username);
        posicion   = (TextView) findViewById(R.id.posicion);
        userBio    = (TextView) findViewById(R.id.userBio);
        correo     = (TextView) findViewById(R.id.email);

        //TODO: Cargar los atributos del usuario de la base de datos y mostrarlos en el perfil
        name.setText(MainActivity.getUser().getNombre());
        username.setText(MainActivity.getUser().getUsername());
        posicion.setText(MainActivity.getUser().getPosicion());
        userBio.setText(MainActivity.getUser().getBio());
        correo.setText(MainActivity.getUser().getCorreo());
        profilePic.setImageURI(MainActivity.getUser().getProfilePic());
    }

    public void onActivityResult(int reqCode, int resCode, Intent data){
        if(resCode == RESULT_OK){
            if (reqCode == 1) {
                profilePic.setImageURI(data.getData());
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_perfil, menu);
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
        }else if (id == R.id.action_edit) {
            Bundle bn = new Bundle();
            bn.putString("NOMBRE", name.getText().toString());
            bn.putString("POSICION", posicion.getText().toString());
            bn.putString("BIO", userBio.getText().toString());

            Intent in = new Intent(this, EditarPerfilActivity.class);
            in.putExtras(bn);
            startActivity(in);
            finish();
        }else if (id == R.id.action_friends) {
            startActivity(new Intent(this, AmigosActivity.class));
        }

        return super.onOptionsItemSelected(item);
    }


    public void onBackPressed(){
        startActivity(new Intent(this, MainActivity.class));
    }
}
