package co.edu.eafit.yomas10;

import android.support.v7.app.ActionBar;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.ListFragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.parse.Parse;
import com.parse.ParseInstallation;
import com.parse.ParsePush;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Locale;

import co.edu.eafit.yomas10.Equipos.CrearEquipoActivity;
import co.edu.eafit.yomas10.Equipos.Equipo;
import co.edu.eafit.yomas10.Equipos.EquipoActivity;
import co.edu.eafit.yomas10.Jugador.Jugador;
import co.edu.eafit.yomas10.Jugador.PerfilActivity;
import co.edu.eafit.yomas10.Partidos.Casual.CrearPartidoCasualActivity;
import co.edu.eafit.yomas10.Partidos.Partido;
import co.edu.eafit.yomas10.Partidos.Casual.PartidoCasual;
import co.edu.eafit.yomas10.Partidos.Casual.PartidoCasualActivity;
import co.edu.eafit.yomas10.Partidos.Equipos.PartidoPorEquiposActivity;
import co.edu.eafit.yomas10.Util.Connection.Http;
import co.edu.eafit.yomas10.Util.Connection.HttpBridge;
import co.edu.eafit.yomas10.Util.Connection.Receiver;
import co.edu.eafit.yomas10.Util.StaticUser;


public class MainActivity extends AppCompatActivity implements ActionBar.TabListener {

    MyPagerAdapter mAdapter;
    ViewPager viewPager;

    private static Jugador user;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        MyApplication myApp = (MyApplication)getApplicationContext();

        user = myApp.getUser();

        final ActionBar actionBar = getSupportActionBar();
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);

        mAdapter = new MyPagerAdapter(getSupportFragmentManager());

        viewPager = (ViewPager) findViewById(R.id.pager);
        viewPager.setAdapter(mAdapter);


        viewPager.setOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
                actionBar.setSelectedNavigationItem(position);
            }
        });

        for(int i = 0; i<mAdapter.getCount(); i++){
            actionBar.addTab(actionBar.newTab().setText(mAdapter.getPageTitle(i)).setTabListener(this));
        }


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
        }else if (id == R.id.action_profile){
            Intent in = new Intent(MainActivity.this, PerfilActivity.class);
            startActivity(in);
            finish();
        }else if (id == R.id.crearEquipo){
            Intent in = new Intent(MainActivity.this , CrearEquipoActivity.class);
            startActivity(in);
            finish();
        }else if (id == R.id.crearPartido){
            Intent in = new Intent(MainActivity.this, CrearPartidoCasualActivity.class);
            startActivity(in);
            finish();
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
        private static final String SECTION_NUMBER = "section_number";
        Http http;


        static ArrayListFragment newInstance(int number){
            ArrayListFragment f = new ArrayListFragment();
            Bundle args = new Bundle();
            args.putInt(SECTION_NUMBER, number);
            f.setArguments(args);

            return f;
        }


        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View v = inflater.inflate(R.layout.frag_one, container, false);
            Bundle bn = getArguments();

            switch (bn.getInt(SECTION_NUMBER)) {
                //TODO cambiar por la base de datos
                case 0:
                    ArrayList<Equipo> equipos = user.getEquipos();
                    setListAdapter(new ArrayAdapter<Equipo>(getActivity(),
                            android.R.layout.simple_list_item_1, equipos));
                    break;
                case 1:

                    ArrayList<Partido> partidos = user.getPartidos();
                    setListAdapter(new ArrayAdapter<Partido>(getActivity(),
                            android.R.layout.simple_list_item_1, partidos));
                    break;
            }
            return v;
        }


        public void onListItemClick(ListView l, View v, int position, long id){

            Bundle bn = getArguments();

            switch (bn.getInt(SECTION_NUMBER)){
                case 0:
                    Serializable equipo = (Serializable) getListView().getItemAtPosition(position);
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("EQUIPO", equipo);
                    Intent in = new Intent(getContext(), EquipoActivity.class);
                    in.putExtra("EQUIPO", equipo);
                    startActivity(in);
                    break;
                case 1:
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
                    break;
            }
        }
    }
}
//clave de Parse LO5XvpbqZL7cLu3vvKWpKZbTr9b3Cc21DU1pd7h3
