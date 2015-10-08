package co.edu.eafit.yomas10;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.parse.Parse;
import com.parse.ParseInstallation;
import com.parse.ParsePush;

import co.edu.eafit.yomas10.Clases.Jugador;


public class Splash extends Activity {
    private Jugador user;

    /** Duration of wait **/
    private final int SPLASH_DISPLAY_LENGTH = 2000;

    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle icicle) {
        super.onCreate(icicle);
        setContentView(R.layout.splashscreen);

        try {
            Parse.initialize(this, getString(R.string.app_id), getString(R.string.client_key));
            ParseInstallation.getCurrentInstallation().saveInBackground();
            ParsePush.subscribeInBackground(user.getUsername());
        }catch (Exception e){}

        /* New Handler to start the Menu-Activity
         * and close this Splash-Screen after some seconds.*/
        new Handler().postDelayed(new Runnable(){
            @Override
            public void run() {
                /* Create an Intent that will start the Menu-Activity. */
                Intent mainIntent = new Intent(Splash.this,MainActivity.class);
                Splash.this.startActivity(mainIntent);
                Splash.this.finish();
            }
        }, SPLASH_DISPLAY_LENGTH);
    }
}