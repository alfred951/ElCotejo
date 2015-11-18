package co.edu.eafit.yomas10;

import android.content.Intent;
import android.support.v4.app.ListFragment;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;

import co.edu.eafit.yomas10.Jugador.Jugador;
import co.edu.eafit.yomas10.Partidos.Casual.PartidoCasual;
import co.edu.eafit.yomas10.Partidos.Casual.PartidoCasualActivity;
import co.edu.eafit.yomas10.Partidos.Equipos.PartidoPorEquiposActivity;
import co.edu.eafit.yomas10.Partidos.Partido;
import co.edu.eafit.yomas10.Util.Connection.Http;
import co.edu.eafit.yomas10.Util.Connection.HttpBridge;
import co.edu.eafit.yomas10.Util.Connection.Receiver;

public class PartidosFragment extends ListFragment implements Receiver {

    private Jugador user;
    private Context context;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        context = container.getContext();
        user = ((MyApplication)context.getApplicationContext()).getUser();

        View view = inflater.inflate(R.layout.fragment_partidos, container, false);

        HashMap<String, String> map = new HashMap<>();
        map.put("nickname", user.getUsername());

        try {
            context.startService(HttpBridge.startWorking(context, map, this, Http.PARTIDO_JUGADOR));
        }catch (UnsupportedEncodingException e){
            e.printStackTrace();
        }

        ArrayList<Partido> partidos = user.getPartidos();
        setListAdapter(new ArrayAdapter<Partido>(getActivity(),
                android.R.layout.simple_list_item_1, partidos));

        return view;
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        Partido partido= (Partido) getListView().getItemAtPosition(position);
        Bundle bundle1 = new Bundle();
        bundle1.putSerializable("PARTIDO", partido);
        if (partido instanceof PartidoCasual){
            Intent intent = new Intent(getContext(), PartidoCasualActivity.class);
            intent.putExtras(bundle1);
            startActivity(intent);
        }else{
            Intent intent = new Intent(getContext(), PartidoPorEquiposActivity.class);
            intent.putExtras(bundle1);
            startActivity(intent);
            //TODO partido por equipos
        }
    }

    @Override
    public void onReceiveResult(int resultCode, Bundle resultData) {
        try {
            JSONArray jsonArray = new JSONArray(resultData.getString("GetResponse"));
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject json = jsonArray.getJSONObject(i);
                int id = json.getInt("idPartido");
                String fecha = json.getString("fecha");
                String hora = json.getString("hora");
                String cancha = json.getString("cancha");
                PartidoCasual partido = new PartidoCasual(fecha,hora,cancha,new ArrayList<Jugador>(), id);

                user.agregarPartido(partido);
            }

            setListAdapter(new ArrayAdapter<Partido>(context, android.R.layout.simple_list_item_1,
                    user.getPartidos()));
        }catch (JSONException e){
            e.printStackTrace();
        }
    }
}
