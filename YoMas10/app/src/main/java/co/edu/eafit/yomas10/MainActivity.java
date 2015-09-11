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

    private Button sendInv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final Context ctx = this;

        Parse.initialize(this, "pugXkDo33e0MsbWMTsL5EChTBx3jKyWoDYI6ZE40", "rxxO31IwOiUHan1udkZjFwHgEIOJPI9zvPWGxm52");
        ParseInstallation.getCurrentInstallation().saveInBackground();

        ParsePush.subscribeInBackground("Canal1");

        sendInv = (Button) findViewById(R.id.ntfyBt);
        sendInv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ParsePush push = new ParsePush();
                push.setChannel("Canal1");
                push.setMessage("Test desde la app");
                push.sendInBackground();
                Toast.makeText(ctx, "Se ha mandado la notificacion", Toast.LENGTH_SHORT).show();

            }
        });
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
}
