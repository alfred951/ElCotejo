package co.edu.eafit.yomas10;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.parse.Parse;
import com.parse.ParseInstallation;
import com.parse.ParseObject;
import com.parse.ParsePush;
import com.parse.ParsePushBroadcastReceiver;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final Context ctx = this;

        Parse.initialize(this, getString(R.string.app_id), getString(R.string.client_key));
        ParseInstallation.getCurrentInstallation().saveInBackground();

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

    public void actSistemas(View view) {
        ParsePush.subscribeInBackground(getString(R.string.eSistemas));
    }

    public void actDiseno(View view) {
        ParsePush.subscribeInBackground(getString(R.string.eDiseno));
    }

    public void actPsicologia(View view) {
        ParsePush.subscribeInBackground(getString(R.string.ePsicologia));
    }

    public void desSistemas(View view) {
        ParsePush.unsubscribeInBackground(getString(R.string.eSistemas));
    }

    public void desDiseno(View view) {
        ParsePush.unsubscribeInBackground(getString(R.string.eDiseno));
    }

    public void desPsicologia(View view) {
        ParsePush.unsubscribeInBackground(getString(R.string.ePsicologia));
    }

    public void notifyUsers(View view){
        ParsePush push = new ParsePush();
        push.setChannel(getString(R.string.eSistemas));
        push.setMessage("Partido Manana a las 9");
        push.sendInBackground();
    }



}

//clave de Parse LO5XvpbqZL7cLu3vvKWpKZbTr9b3Cc21DU1pd7h3
