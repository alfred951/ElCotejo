package co.edu.eafit.yomas10.Jugador;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

import co.edu.eafit.yomas10.MainActivity;
import co.edu.eafit.yomas10.R;

public class AmigosNActivity extends AppCompatActivity {

    private ArrayList<Jugador> amigos;
    private ArrayAdapter<Jugador> mAdapter;
    private ListView amigosLV;
    private TextView noElementsTV;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_amigos_n);

        amigos = MainActivity.getUser().getAmigos();
        mAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, amigos);

        amigosLV = (ListView) findViewById(R.id.listaAmigos);
        noElementsTV = (TextView) findViewById(R.id.emptyText);

        amigosLV.setEmptyView(noElementsTV);

        amigosLV.setAdapter(mAdapter);

        amigosLV.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Jugador amigo = (Jugador) parent.getItemAtPosition(position);
                Bundle bn = new Bundle();
                bn.putSerializable("Jugador", amigo);
//                bn.putString("USERNAME", amigo.toString());
//                bn.putString("NOMBRE", amigo.getNombre());
//                bn.putString("POSICION", amigo.getPosicion());
//                bn.putString("BIO", amigo.getBio());
//                bn.putString("CORREO", amigo.getCorreo());

                Intent in = new Intent(getApplicationContext(), PerfilExterno.class);
                in.putExtras(bn);
                startActivity(in);
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_amigos_n, menu);
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
