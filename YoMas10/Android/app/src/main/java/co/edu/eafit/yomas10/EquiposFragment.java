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

import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;

import co.edu.eafit.yomas10.Equipos.Equipo;
import co.edu.eafit.yomas10.Equipos.EquipoActivity;
import co.edu.eafit.yomas10.Jugador.Jugador;
import co.edu.eafit.yomas10.Util.Connection.Http;
import co.edu.eafit.yomas10.Util.Connection.HttpBridge;
import co.edu.eafit.yomas10.Util.Connection.Receiver;


public class EquiposFragment extends ListFragment implements Receiver{

    private Jugador user;
    private Context context;

    private ArrayAdapter<Equipo> adapter;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        context = container.getContext();
        user = ((MyApplication)context.getApplicationContext()).getUser();

        View view = inflater.inflate(R.layout.fragment_equipos, container, false);

        HashMap<String, String> map = new HashMap<>();
        map.put("nickname", user.getUsername());

        try {
            context.startService(HttpBridge.startWorking(context.getApplicationContext(), map, this, Http.EQUIPO_JUGADOR));
        }catch (UnsupportedEncodingException e){
            e.printStackTrace();
        }

        return view;
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        Serializable equipo = (Serializable) getListView().getItemAtPosition(position);
        Bundle bundle = new Bundle();
        bundle.putSerializable("EQUIPO", equipo);
        Intent in = new Intent(getContext(), EquipoActivity.class);
        in.putExtra("EQUIPO", equipo);
        startActivity(in);
    }

    @Override
    public void onReceiveResult(int resultCode, Bundle resultData) {
        try {
            JSONArray jsonArray= new JSONArray(resultData.getString("GetResponse"));
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject equipoJS = jsonArray.getJSONObject(i);
                Jugador capitan = new Jugador(equipoJS.getString("capitan"));
                Equipo equipo = capitan.crearEquipo(equipoJS.getString("nombre"), equipoJS.getInt("idEquipo"));
                user.agregarEquipo(equipo);

            }
            //adapter.notifyDataSetChanged();

            setListAdapter(new ArrayAdapter<Equipo>(context, android.R.layout.simple_list_item_1,user.getEquipos()));
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }
}
