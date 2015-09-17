package co.edu.eafit.yomas10;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.parse.Parse;
import com.parse.ParseInstallation;
import com.parse.ParseObject;
import com.parse.ParsePush;
import com.parse.ParsePushBroadcastReceiver;


public class MainActivity extends AppCompatActivity {

    EditText equipo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final Context ctx = this;

        Parse.initialize(this, getString(R.string.app_id), getString(R.string.client_key));
        ParseInstallation.getCurrentInstallation().saveInBackground();

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
        }

        return super.onOptionsItemSelected(item);
    }



    public void notifyUsers(View view){
        ParsePush push = new ParsePush();
        push.setChannel(equipo.getText().toString());
        push.setMessage("Notificacion Recibida");
        push.sendInBackground();
    }

    public void suscribeNewChannel(View view){
        ParsePush.subscribeInBackground(equipo.getText().toString());
    }



}

//clave de Parse LO5XvpbqZL7cLu3vvKWpKZbTr9b3Cc21DU1pd7h3
