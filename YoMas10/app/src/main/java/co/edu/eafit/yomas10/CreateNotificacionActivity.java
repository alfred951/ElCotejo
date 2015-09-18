package co.edu.eafit.yomas10;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.parse.ParsePush;

import org.json.JSONException;
import org.json.JSONObject;

public class CreateNotificacionActivity extends AppCompatActivity {

    private EditText msg;
    private EditText nombre;
    private EditText fecha;
    private EditText canal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_notificacion);

        nombre = (EditText) findViewById(R.id.nombrePartido);
        fecha = (EditText) findViewById(R.id.date);
        msg = (EditText) findViewById(R.id.msg);
        canal = (EditText) findViewById(R.id.canal);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_create_notificacion, menu);
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

    public void sendNotification(View view){
        JSONObject json = new JSONObject();
        try{
            json.put("msg", msg.getText().toString());
            json.put("date", fecha.getText().toString());
            json.put("nombre", nombre.getText().toString());
        }catch (JSONException e){
            Toast.makeText(this, "No se pudo mandar la notificación", Toast.LENGTH_LONG).show();
        }


        ParsePush push = new ParsePush();
        push.setChannel(canal.getText().toString());
        push.setData(json);
        push.sendInBackground();

        Toast.makeText(this, getString(R.string.notificacion), Toast.LENGTH_LONG).show();
        finish();
    }
}
