package co.edu.eafit.yomas10.Jugador;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.ActionMode;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;

import co.edu.eafit.yomas10.Equipos.CrearEquipoActivity;
import co.edu.eafit.yomas10.MainActivity;
import co.edu.eafit.yomas10.Partidos.CrearPartidoActivity;
import co.edu.eafit.yomas10.R;

public class SeleccionarAmigosActivity extends AppCompatActivity {

    private ArrayList<Jugador> amigos;
    //private ArrayList<String> usernameAmigos = new ArrayList<>();
    private SelectionAdapter mAdapter;
    private ListView amigosLV;
    private TextView noElementsTV;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_amigos);

        Toast.makeText(this, "Manten undido para seleccionar", Toast.LENGTH_LONG).show();

        amigos = MainActivity.getUser().getAmigos();
//        for (int i = 0; i<amigos.size();i++){
//            usernameAmigos.add(amigos.get(i).getUsername());
//        }

        mAdapter = new SelectionAdapter(this, android.R.layout.simple_list_item_1, amigos);

        amigosLV = (ListView) findViewById(R.id.listaAmigos);
        noElementsTV = (TextView) findViewById(R.id.emptyText);

        amigosLV.setEmptyView(noElementsTV);

        amigosLV.setAdapter(mAdapter);
        amigosLV.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE_MODAL);

        amigosLV.setMultiChoiceModeListener(new AbsListView.MultiChoiceModeListener() {

            private int nr = 0;

            @Override
            public void onItemCheckedStateChanged(ActionMode mode, int position, long id, boolean checked) {
                if (checked) {
                    nr++;
                    mAdapter.setNewSelection(position, checked);
                } else {
                    nr--;
                    mAdapter.removeSelection(position);
                }
                mode.setTitle(nr + " seleccionados");
            }

            @Override
            public boolean onCreateActionMode(ActionMode mode, Menu menu) {
                nr = 0;
                MenuInflater inflater = getMenuInflater();
                inflater.inflate(R.menu.contextual_menu_seleccionar_amigos, menu);
                return true;
            }

            @Override
            public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
                return false;
            }

            @Override
            public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
                ArrayList<Jugador> jugadores = getCheckedPlayers();
                ArrayList<String> usernameJugadores = new ArrayList<String>();

                for (int i = 0; i < jugadores.size(); i++) {
                    usernameJugadores.add(jugadores.get(i).getUsername());
                }

                Bundle bn = new Bundle();
                bn.putStringArrayList("JUGADORES", usernameJugadores);

                switch (item.getItemId()) {
                    case R.id.check:
                        Intent intent = new Intent(getApplicationContext(), CrearEquipoActivity.class);
                        intent.putExtras(bn);
                        setResult(RESULT_OK, intent);
                        finish();
                        break;
                }
                return false;
            }

            @Override
            public void onDestroyActionMode(ActionMode mode) {
                mAdapter.clearSelection();
            }
        });

     /*   amigosLV.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent in = new Intent(getApplicationContext(), PerfilExterno.class);
                in.putExtra("USERNAME", (String) parent.getAdapter().getItem(position));
                startActivity(in);
            }
        });*/

        amigosLV.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                amigosLV.setItemChecked(position, !mAdapter.isPositionChecked(position));
                return false;
            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_amigos, menu);
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

    //TODO cambiar por username
    public ArrayList<Jugador> getCheckedPlayers(){
        ArrayList<Jugador> jugadores = new ArrayList<>();
        Set<Integer> checked = mAdapter.getCurrentCheckedPosition();

        Iterator<Integer> it = checked.iterator();
        while(it.hasNext()){
            Integer numPlayer = it.next();
            Jugador jugador = mAdapter.getItem(numPlayer);
            jugadores.add(jugador);
        }
        return jugadores;
    }

    private class SelectionAdapter extends ArrayAdapter<Jugador> {
        private HashMap<Integer, Boolean> mSelection = new HashMap<>();

        public SelectionAdapter
                (Context context, int resource, ArrayList<Jugador> objects){
            super(context, resource, objects);
        }

        public void setNewSelection(int position, boolean value){
            mSelection.put(position, value);
            notifyDataSetChanged();
        }

        public boolean isPositionChecked(int position){
            Boolean result = mSelection.get(position);
            return result == null ? false : result;
        }

        public Set<Integer> getCurrentCheckedPosition(){
            return mSelection.keySet();
        }

        public void removeSelection(int position){
            mSelection.remove(position);
            notifyDataSetChanged();
        }

        public void clearSelection(){
            mSelection = new HashMap<Integer, Boolean>();
            notifyDataSetChanged();
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent){
            View v = super.getView(position, convertView, parent);
            //v.setBackgroundColor(getResources().getColor(android.R.color.background_light));

            if (mSelection.get(position) != null){
                v.setBackgroundColor(getResources().getColor(android.R.color.holo_blue_light));
            }
            return v;
        }
    }
}
