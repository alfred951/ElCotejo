package co.edu.eafit.yomas10;

import android.support.v4.app.ListFragment;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import java.util.ArrayList;

import co.edu.eafit.yomas10.Jugador.Jugador;
import co.edu.eafit.yomas10.Partidos.Partido;
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

        ArrayList<Partido> partidos = user.getPartidos();
        setListAdapter(new ArrayAdapter<Partido>(getActivity(),
                android.R.layout.simple_list_item_1, partidos));

        return view;
    }

    @Override
    public void onReceiveResult(int resultCode, Bundle resultData) {
        //TODO
    }
}
