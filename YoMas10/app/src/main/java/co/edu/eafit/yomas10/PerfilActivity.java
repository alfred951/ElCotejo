package co.edu.eafit.yomas10;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import co.edu.eafit.yomas10.Clases.Jugador;

public class PerfilActivity extends AppCompatActivity {

    private ImageView profilePic;
    private TextView name;
    private TextView username;
    private TextView posicion;
    private TextView userBio;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perfil);

        profilePic = (ImageView)findViewById(R.id.usrPic);
        name       = (TextView) findViewById(R.id.name);
        username   = (TextView) findViewById(R.id.username);
        posicion   = (TextView) findViewById(R.id.posicion);
        userBio    = (TextView) findViewById(R.id.userBio);

        //TODO: Cargar los atributos del usuario de la base de datos y mostrarlos en el perfil
        name.setText(MainActivity.jugador.getNombre());
        username.setText(MainActivity.jugador.getUsername());
        posicion.setText(MainActivity.jugador.getPosicion());
        userBio.setText(MainActivity.jugador.getBio());


        Bundle bn = this.getIntent().getExtras();
        try{
            if(bn.getString("Cambios").equals("si")){
                name.setText(bn.getString("name"));
                MainActivity.jugador.setNombre(bn.getString("name"));
                posicion.setText(bn.getString("posicion"));
                MainActivity.jugador.setPosicion(bn.getString("posicion"));
                userBio.setText(bn.getString("bio"));
                MainActivity.jugador.setBio(bn.getString("bio"));
                profilePic.setImageURI(Uri.parse(bn.getString("image")));
            }
        }catch (Exception e){}

        //TODO: guardar la imagen de perfil

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
        }

        return super.onOptionsItemSelected(item);
    }


    public void onBackPressed(){
        startActivity(new Intent(this, MainActivity.class));
    }
}
