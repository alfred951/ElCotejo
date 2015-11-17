package co.edu.eafit.yomas10.Equipos;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;

import co.edu.eafit.yomas10.Jugador.Jugador;
import co.edu.eafit.yomas10.Jugador.PerfilExterno;
import co.edu.eafit.yomas10.R;
import co.edu.eafit.yomas10.Util.Connection.Http;
import co.edu.eafit.yomas10.Util.Connection.HttpBridge;
import co.edu.eafit.yomas10.Util.Connection.Receiver;

/**
 * Activity con la informacion del equipo que lo haya llamado
 */
public class EquipoActivity extends AppCompatActivity implements Receiver{

    private Context ctx = this;
    private TextView capitan;
    private ListView listaJugadores;
    private ArrayAdapter<Jugador> adapter;

    private Equipo equipo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_equipo);

        capitan = (TextView) findViewById(R.id.capitan);
        listaJugadores = (ListView) findViewById(R.id.listaJugadores);

        equipo = (Equipo) getIntent().getSerializableExtra("EQUIPO");

        buscarJugadores();
        setTitle(equipo.getNombre());

        capitan.setText(equipo.getCapitan().getNombre());
        capitan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in = new Intent(getApplicationContext(), PerfilExterno.class);
                in.putExtra("JUGADOR", equipo.getCapitan());
                startActivity(in);
            }
        });

        adapter = new ArrayAdapter<>
                (this, android.R.layout.simple_list_item_1, equipo.getIntegrantes());

        listaJugadores.setAdapter(adapter);

        listaJugadores.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Jugador jugador = (Jugador) parent.getAdapter().getItem(position);

                Bundle bn = new Bundle();
                bn.putSerializable("JUGADOR", jugador);

                Intent in = new Intent(ctx, PerfilExterno.class);
                in.putExtras(bn);
                startActivity(in);
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_equipo, menu);
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

    public void buscarJugadores(){
        HashMap<String, String> map = new HashMap<>();
        map.put("IdEquipo", equipo.getId() + "");
        try {
            startService(HttpBridge.startWorking(this, map, this, Http.INTEGRANTES));
        }catch (UnsupportedEncodingException e){
            e.printStackTrace();
        }
    }

    @Override
    public void onReceiveResult(int resultCode, Bundle resultData) {
        try {
            JSONArray jugadoresJS = new JSONArray(resultData.getString("GetResponse"));
            //capitan.setText();
            ArrayList<Jugador> jugadores = new ArrayList<>();
            for (int i = 0; i < jugadoresJS.length(); i++) {
                Jugador jugador = new Jugador(jugadoresJS.getJSONObject(i).getString("username"));
                adapter.add(jugador);
                adapter.notifyDataSetChanged();
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
