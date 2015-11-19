package co.edu.eafit.yomas10.Jugador;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;

import co.edu.eafit.yomas10.MyApplication;
import co.edu.eafit.yomas10.R;
import co.edu.eafit.yomas10.Util.Connection.Http;
import co.edu.eafit.yomas10.Util.Connection.HttpBridge;
import co.edu.eafit.yomas10.Util.Connection.Receiver;

/**
 * Activity para modificar el perfil del usuario, estos datos tambien deben ser guardados en la DB
 */
public class EditarPerfilActivity extends AppCompatActivity implements Receiver{

    private EditText name, posicion, userBio, edad;
    private TextView username, correo;
    private ImageView profilePic;
    private Uri dataPic;

    private Jugador user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editar_perfil);

        user = ((MyApplication)getApplicationContext()).getUser();

        name     = (EditText) findViewById(R.id.name);
        edad     = (EditText) findViewById(R.id.edad);
        posicion = (EditText) findViewById(R.id.posicion);
        userBio  = (EditText) findViewById(R.id.userBio);
        profilePic = (ImageView) findViewById(R.id.usrPic);

        username  = (TextView) findViewById(R.id.username);
        correo    = (TextView) findViewById(R.id.email);

        Bundle bn = this.getIntent().getExtras();

        name.setText(bn.getString("NOMBRE"));
        edad.setText(bn.getString("EDAD"));
        posicion.setText(bn.getString("POSICION"));
        userBio.setText(bn.getString("BIO"));

        username.setText(user.getUsername());
        correo.setText(user.getCorreo());

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

        if (id == R.id.action_editSave){
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
        String edadT     = edad.getText().toString();
        String posicionT = posicion.getText().toString();
        String userBioT  = userBio.getText().toString();
        Uri picture      = dataPic;

        HashMap<String, String> map = new HashMap<>();
        map.put("nickname", user.getUsername());
        map.put("nombre", nameT);
        map.put("edad", edadT);
        map.put("bio", userBioT);
        map.put("posicion", posicionT);

        try {
            startService(HttpBridge.startWorking(this, map, this, Http.JUGADOR));
        }catch (UnsupportedEncodingException e){
            e.printStackTrace();
        }

        user.setNombre(nameT);
        user.setPosicion(posicionT);
        user.setBio(userBioT);
        user.setEdad(Integer.parseInt(edadT));
        //MainActivity.getUser().setProfilePic(picture);

    }

    @Override
    public void onReceiveResult(int resultCode, Bundle resultData) {

    }
}
