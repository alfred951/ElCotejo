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
import com.parse.ParsePush;

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
        ParsePush.subscribeInBackground("Sistemas");
    }

    public void actDiseno(View view) {
        ParsePush.subscribeInBackground("Diseno");
    }

    public void actPsicologia(View view) {
        ParsePush.subscribeInBackground("Psicologia");
    }

    public void desSistemas(View view) {
        ParsePush.unsubscribeInBackground("Sistemas");
    }

    public void desDiseno(View view) {
        ParsePush.unsubscribeInBackground("Diseno");
    }

    public void desPsicologia(View view) {
        ParsePush.unsubscribeInBackground("Psicologia");
    }

}
