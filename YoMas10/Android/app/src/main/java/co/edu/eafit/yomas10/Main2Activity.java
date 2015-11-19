package co.edu.eafit.yomas10;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import java.util.ArrayList;
import java.util.List;

import co.edu.eafit.yomas10.Equipos.CrearEquipoActivity;
import co.edu.eafit.yomas10.Jugador.Jugador;
import co.edu.eafit.yomas10.Jugador.PerfilActivity;
import co.edu.eafit.yomas10.Partidos.Casual.CrearPartidoCasualActivity;

public class Main2Activity extends AppCompatActivity{

    static final int NUM_ITEMS = 2;

    private MyAdapter pagerAdapter;
    private ViewPager viewPager;
    private Jugador user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        final ActionBar actionBar = getSupportActionBar();
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);

        user = ((MyApplication)getApplicationContext()).getUser();

        viewPager = (ViewPager) findViewById(R.id.pager);
        setupViewPager(viewPager);

        viewPager.setOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
                actionBar.setSelectedNavigationItem(position);
            }
        });

        ActionBar.TabListener tabListener = new ActionBar.TabListener() {
            @Override
            public void onTabSelected(ActionBar.Tab tab, android.support.v4.app.FragmentTransaction ft) {
                viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(ActionBar.Tab tab, android.support.v4.app.FragmentTransaction ft) {

            }

            @Override
            public void onTabReselected(ActionBar.Tab tab, android.support.v4.app.FragmentTransaction ft) {

            }
        };

        for(int i = 0; i<viewPager.getAdapter().getCount(); i++){
            actionBar.addTab(actionBar.newTab().setText(viewPager.getAdapter().getPageTitle(i)).setTabListener(tabListener));
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main2, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_profile){
        Intent in = new Intent(Main2Activity.this, PerfilActivity.class);
        startActivity(in);
        finish();
    }else if (id == R.id.crearEquipo){
        Intent in = new Intent(Main2Activity.this , CrearEquipoActivity.class);
        startActivity(in);
        finish();
    }else if (id == R.id.crearPartido){
        Intent in = new Intent(Main2Activity.this, CrearPartidoCasualActivity.class);
        startActivity(in);
        finish();
    }

        return super.onOptionsItemSelected(item);
    }

    private void setupViewPager(ViewPager viewPager){
        MyAdapter adapter = new MyAdapter(getSupportFragmentManager());
        adapter.addFragment(new EquiposFragment(), "Equipos");
        adapter.addFragment(new PartidosFragment(), "Partidos");
        viewPager.setAdapter(adapter);
    }



    public static class MyAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public MyAdapter(FragmentManager fm){
            super(fm);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        public void addFragment(Fragment fragment, String title){
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        public CharSequence getPageTitle(int position){
            return mFragmentTitleList.get(position);
        }
    }



}
