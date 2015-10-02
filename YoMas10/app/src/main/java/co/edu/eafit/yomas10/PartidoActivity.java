package co.edu.eafit.yomas10;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import org.w3c.dom.Text;

import co.edu.eafit.yomas10.Clases.Equipo;

/**
 * Activity con la informacion del partido que la llamo
 */
public class PartidoActivity extends AppCompatActivity {

    private TextView nPartido;
    private TextView nDia;
    

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_partido);

        nPartido = (TextView) findViewById(R.id.nPartido);
        nDia = (TextView) findViewById(R.id.nDia);

        Bundle bn = this.getIntent().getExtras();
        nPartido.setText(bn.getString("NOMBRE"));
        nDia.setText(bn.getString("FECHA"));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_partido, menu);
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

    public void aceptarPartido(View view){
        //TODO
    }

    public void rechazarPartido(View viw){
        //TODO
    }
}
