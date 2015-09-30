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

public class EditarPerfilActivity extends AppCompatActivity {

    private EditText name, posicion, userBio;
    private ImageView profilePic;
    private String dataPic;

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
                dataPic = data.getData().toString();
                //TODO: Guardar la imagen en el sistema de archivos
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
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
            Bundle bn = saveChanges();
            Toast.makeText(this, getString(R.string.editado), Toast.LENGTH_LONG).show();

            Intent in = new Intent(this, PerfilActivity.class);
            in.putExtras(bn);
            startActivity(in);
        }

        return super.onOptionsItemSelected(item);
    }


    public Bundle saveChanges(){
        String nameT     = name.getText().toString();
        String posicionT = posicion.getText().toString();
        String userBioT  = userBio.getText().toString();

        MainActivity.jugador.setNombre(nameT);
        MainActivity.jugador.setPosicion(posicionT);
        MainActivity.jugador.setBio(userBioT);

        //TODO: Subir los nuevos datos a la base de datos

        Bundle bn = new Bundle();
        bn.putString("Cambios", "si");
        bn.putString("name", nameT);
        bn.putString("posicion", posicionT);
        bn.putString("bio", userBioT);
        bn.putString("image", dataPic);

        return bn;

    }
}
