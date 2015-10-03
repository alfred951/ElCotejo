package co.edu.eafit.yomas10;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import co.edu.eafit.yomas10.Helpers.StaticUser;

/**
 * Activity para modificar el perfil del usuario, estos datos tambien deben ser guardados en la DB
 */
public class EditarPerfilActivity extends AppCompatActivity {

    private EditText name, posicion, userBio;
    private ImageView profilePic;
    private Uri dataPic;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editar_perfil);

        name     = (EditText) findViewById(R.id.name);
        posicion = (EditText) findViewById(R.id.posicion);
        userBio  = (EditText) findViewById(R.id.userBio);
        profilePic = (ImageView) findViewById(R.id.usrPic);

        Bundle bn = this.getIntent().getExtras();

        name.setText(bn.getString("NOMBRE"));
        posicion.setText(bn.getString("POSICION"));
        userBio.setText(bn.getString("BIO"));

        profilePic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in = new Intent();
                in.setType("image/*");
                in.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(in,getString(R.string.selecImagen)), 1);
            }
        });
    }

    public void onActivityResult(int reqCode, int resCode, Intent data){
        if(resCode == RESULT_OK){
            if (reqCode == 1) {
                profilePic.setImageURI(data.getData());
                dataPic = data.getData();
                //TODO: Guardar la imagen en el sistema de archivos
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu_editar_perfil, menu);
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
        }else if (id == R.id.action_editSave){
           saveChanges();
            Toast.makeText(this, getString(R.string.editado), Toast.LENGTH_LONG).show();
            Intent in = new Intent(this, PerfilActivity.class);
            startActivity(in);
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * Guarda los cambios realizados en el activity al perfil y los sube a la DB
     */
    public void saveChanges(){
        String nameT     = name.getText().toString();
        String posicionT = posicion.getText().toString();
        String userBioT  = userBio.getText().toString();
        Uri picture      = dataPic;

        StaticUser.jugador.setNombre(nameT);
        StaticUser.jugador.setPosicion(posicionT);
        StaticUser.jugador.setBio(userBioT);
        StaticUser.jugador.setProfilePic(picture);

        //TODO: Subir los nuevos datos a la base de datos
    }
}
