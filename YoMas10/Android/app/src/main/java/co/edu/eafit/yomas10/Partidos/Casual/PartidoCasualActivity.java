package co.edu.eafit.yomas10.Partidos.Casual;

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
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;

import co.edu.eafit.yomas10.Jugador.Jugador;
import co.edu.eafit.yomas10.Jugador.PerfilExterno;
import co.edu.eafit.yomas10.R;
import co.edu.eafit.yomas10.Util.Connection.Http;
import co.edu.eafit.yomas10.Util.Connection.HttpBridge;
import co.edu.eafit.yomas10.Util.Connection.Receiver;

public class PartidoCasualActivity extends AppCompatActivity implements Receiver{

    private TextView horaPartido, fechaPartido, cancha;
    private ListView participantesLV;
    private ArrayAdapter<Jugador> adapter;
    private PartidoCasual partido;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_partido_casual);

        horaPartido = (TextView) findViewById(R.id.horaPartido);
        fechaPartido = (TextView) findViewById(R.id.fechaPartido);
        cancha = (TextView) findViewById(R.id.canchaPartido);
        participantesLV = (ListView) findViewById(R.id.integrantes);

        partido = (PartidoCasual) getIntent().getExtras().getSerializable("PARTIDO");

        buscarInfo();

        horaPartido.setText(partido.getHora());
        fechaPartido.setText(partido.getFecha());
        cancha.setText(partido.getCancha());

        adapter = new ArrayAdapter<Jugador>(this,
                android.R.layout.simple_list_item_1, partido.getIntegrantes());

        participantesLV.setAdapter(adapter);

        participantesLV.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Jugador jugador = (Jugador) parent.getItemAtPosition(position);
                Bundle bn = new Bundle();
                bn.putSerializable("JUGADOR", jugador);

                Intent in = new Intent(getApplicationContext(), PerfilExterno.class);
                in.putExtras(bn);
                startActivity(in);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_partido_casual, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }

    public void buscarInfo(){
        HashMap<String, String> map = new HashMap<>();
        map.put("idPartido", partido.getId() +"");

        try{
            startService(HttpBridge.startWorking(this, map, this, Http.PARTICIPANTES));
        }catch (UnsupportedEncodingException e){
            e.printStackTrace();
        }
    }

    @Override
    public void onReceiveResult(int resultCode, Bundle resultData) {
        try {
            JSONArray jsonArray = new JSONArray(resultData.getString("GetResponse"));
            Log.d("Participantes", jsonArray.toString());
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject json = jsonArray.getJSONObject(i);
                Jugador integrante = new Jugador(json.getString("nickname"));
                adapter.add(integrante);
            }
            adapter.notifyDataSetChanged();

        }catch (JSONException e){
            e.printStackTrace();
        }
    }
}
