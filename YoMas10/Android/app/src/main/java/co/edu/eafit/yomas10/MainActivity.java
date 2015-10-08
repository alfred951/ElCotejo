package co.edu.eafit.yomas10;

import android.app.ActionBar;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.support.v4.app.Fragment;

import com.parse.Parse;
import com.parse.ParseInstallation;
import com.parse.ParsePush;

import java.util.List;
import java.util.Vector;

import co.edu.eafit.yomas10.Clases.Jugador;
import co.edu.eafit.yomas10.Helpers.StaticUser;


public class MainActivity extends FragmentActivity {

    //ListView lista;

    //private String arregloCadenas[] = {"Sistemas", "Psicologia"};
    ViewPager viewPager;
    public List<String> fragments = new Vector<>();
    static MyPagerAdapter myPagerAdapter;
    final Context ctx = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //final ActionBar actionBar = getActionBar();
        //actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);




        fragments.add(Frag1.class.getName());
        fragments.add(Frag2.class.getName());

        viewPager = (ViewPager) findViewById(R.id.pager);
        myPagerAdapter = new MyPagerAdapter(getSupportFragmentManager());
        viewPager.setAdapter(myPagerAdapter);

        /*lista = (ListView) findViewById(R.id.pager);
        ArrayAdapter<String> adaptador = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1, arregloCadenas);
        lista.setAdapter(adaptador); */
        StaticUser.initialize();

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

    /*public void suscribeNewChannel(View view) {
        ParsePush.subscribeInBackground(canal.getText().toString().toLowerCase());
        Toast.makeText(this, getString(R.string.suscripcion) + " " +canal.getText().toString(),Toast.LENGTH_LONG).show();
    } */

    class MyPagerAdapter extends FragmentPagerAdapter {

        public List<String> fragmentsA;

        public MyPagerAdapter(FragmentManager fm) {
            super(fm);
            fragmentsA = fragments;
        }

        @Override
        public Fragment getItem(int position) {
            switch (position) {
                case 0:
                    return Fragment.instantiate(ctx, Frag1.class.getName());
                case 1:
                    return Fragment.instantiate(ctx, Frag2.class.getName());
                default:
                    return null;
            }
        }

        @Override
        public int getCount() {
            return 2;
        }
    }

    public static class Frag1 extends Fragment {

        public Frag1(){}

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
            return inflater.inflate(R.layout.frag_one, container, false);
        }
    }

    public static class Frag2 extends Fragment {

        public Frag2(){}

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
            return inflater.inflate(R.layout.frag_two, container, false);
        }

    }
}



//clave de Parse LO5XvpbqZL7cLu3vvKWpKZbTr9b3Cc21DU1pd7h3
