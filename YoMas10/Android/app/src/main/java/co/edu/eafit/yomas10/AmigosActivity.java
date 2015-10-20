package co.edu.eafit.yomas10;

import android.app.ListActivity;
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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;

import co.edu.eafit.yomas10.Clases.Jugador;

public class AmigosActivity extends AppCompatActivity{

    private ArrayList<Jugador> amigos;
    private ArrayList<String> nombreAmigos = new ArrayList<>();
    private SelectionAdapter mAdapter;
    private ListView amigosLV;
    private TextView noElementsTV;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_amigos);



        amigos = MainActivity.getUser().getAmigos();
        for (int i = 0; i<amigos.size();i++){
            nombreAmigos.add(amigos.get(i).getNombre());
        }

        mAdapter = new SelectionAdapter(this, android.R.layout.simple_list_item_1, nombreAmigos);

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
                inflater.inflate(R.menu.contextual_menu, menu);
                return true;
            }

            @Override
            public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
                return false;
            }

            @Override
            public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.action_invitar:
                          Intent intent = new Intent(getApplicationContext(), CrearEquipoActivity.class);
                          startActivity(intent);

                }
                return false;
            }

            @Override
            public void onDestroyActionMode(ActionMode mode) {
                mAdapter.clearSelection();
            }
        });

        amigosLV.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent in = new Intent(getApplicationContext(), PerfilExterno.class);
                in.putExtra("USERNAME", (String) parent.getAdapter().getItem(position));
                startActivity(in);
            }
        });

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

    private class SelectionAdapter extends ArrayAdapter<String>{
        private HashMap<Integer, Boolean> mSelection = new HashMap<>();

        public SelectionAdapter
                (Context context, int resource, ArrayList<String> objects){
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

        /*public Set<Jugador> getCurrentCheckedPlayers(){
            Set set = mSelection
        }*/

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
