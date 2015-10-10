package co.edu.eafit.yomas10;

import android.support.v7.app.ActionBar;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.ListFragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.parse.Parse;
import com.parse.ParseInstallation;
import com.parse.ParsePush;

import java.util.ArrayList;
import java.util.Locale;

import co.edu.eafit.yomas10.Clases.Equipo;
import co.edu.eafit.yomas10.Clases.Jugador;
import co.edu.eafit.yomas10.Clases.Partido;
import co.edu.eafit.yomas10.Helpers.StaticUser;


public class MainActivity extends AppCompatActivity implements ActionBar.TabListener {

    MyPagerAdapter mAdapter;
    ViewPager viewPager;
    static Context ctx;

    private static Jugador user;

    public static Jugador getUser(){ return user; }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        try {
            Parse.initialize(this, getString(R.string.app_id), getString(R.string.client_key));
            ParseInstallation.getCurrentInstallation().saveInBackground();
            ParsePush.subscribeInBackground(user.getUsername());
        }catch (Exception e){}

        StaticUser.initialize();
        user = StaticUser.jugador;

        ctx = this;
        final ActionBar actionBar = getSupportActionBar();
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);

        mAdapter = new MyPagerAdapter(getSupportFragmentManager());

        viewPager = (ViewPager) findViewById(R.id.pager);
        viewPager.setAdapter(mAdapter);

        viewPager.setOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener(){
            @Override
        public void onPageSelected(int position){
                actionBar.setSelectedNavigationItem(position);
            }
        });

        for(int i = 0; i<mAdapter.getCount(); i++){
            actionBar.addTab(actionBar.newTab().setText(mAdapter.getPageTitle(i)).setTabListener(this));
        }
        /*lista = (ListView) findViewById(R.id.pager);
        ArrayAdapter<String> adaptador = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1, arregloCadenas);
        lista.setAdapter(adaptador); */


        //canal = (EditText) findViewById(R.id.equipo);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
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
        }else if (id == R.id.action_newNoti){
            Intent in = new Intent(MainActivity.this, CreateNotificacionActivity.class);
            startActivity(in);
        }else if (id == R.id.action_profile){
            Intent in = new Intent(MainActivity.this, PerfilActivity.class);
            startActivity(in);
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onTabSelected(ActionBar.Tab tab, android.support.v4.app.FragmentTransaction ft) {
        viewPager.setCurrentItem(tab.getPosition());
    }

    @Override
    public void onTabUnselected(ActionBar.Tab tab, android.support.v4.app.FragmentTransaction ft) {}

    @Override
    public void onTabReselected(ActionBar.Tab tab, android.support.v4.app.FragmentTransaction ft) {}


    class MyPagerAdapter extends FragmentPagerAdapter {

        public MyPagerAdapter(FragmentManager fm) {
            super(fm);
        }


        public Fragment getItem(int position) {
            return ArrayListFragment.newInstance(position);
        }

        @Override
        public int getCount() {
            return 2;
        }

        public CharSequence getPageTitle(int position){
            Locale l = Locale.getDefault();
            switch (position){
                case 0:
                    return "Equipos";
                case 1:
                    return "Partidos";
            }
            return null;
        }
    }

    public static class ArrayListFragment extends ListFragment {
        private static final String SECTION_NAME = "section_number";


        static ArrayListFragment newInstance(int name){
            ArrayListFragment f = new ArrayListFragment();

            Bundle args = new Bundle();
            args.putInt(SECTION_NAME, name);
            f.setArguments(args);

            return f;
        }
/*
        @Override
        public void onCreate(Bundle savedInstanceState){
            super.onCreate(savedInstanceState);
            nNum = getArguments() != null ? getArguments().getInt("num") : 1;
            setHasOptionsMenu(true);
        }
*/
        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View v = inflater.inflate(R.layout.frag_one, container, false);

            Bundle bn = getArguments();

            switch (bn.getInt(SECTION_NAME)){
                //TODO cambiar por la base de datos
                case 0:
                    ArrayList<Equipo> equipos = user.getEquipos();
                    ArrayList<String> nombreEquipos = new ArrayList<>();


                    for (int i = 0; i<equipos.size(); i++){
                        nombreEquipos.add(equipos.get(i).getNombre());
                    }
                    setListAdapter(new ArrayAdapter<String>(getActivity(),
                            android.R.layout.simple_list_item_1, nombreEquipos));
                    break;
                case 1:

                    setListAdapter(new ArrayAdapter<Partido>(getActivity(),
                            android.R.layout.simple_list_item_1, user.getPartidos()));
                    break;
            }

            ListView listView = (ListView)v.findViewById(android.R.id.list);

            /*View tv = v.findViewById(R.id.text);
            ((TextView)tv).setText("Fragment #" + nNum);

            */return v;
        }


        public void onListItemClick(ListView l, View v, int position, long id){
            Log.i("FragmentList", "ItemClicked: " + id);
            Intent in = new Intent(ctx, EquipoActivity.class);
            in.putExtra("NOMBRE", getListView().getItemIdAtPosition(position));

            startActivity(in);
        }
    }
}



//clave de Parse LO5XvpbqZL7cLu3vvKWpKZbTr9b3Cc21DU1pd7h3
