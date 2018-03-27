package com.gru.cajaaplicacionestics.view;

import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.Tracker;
import com.gru.cajaaplicacionestics.R;
import com.gru.cajaaplicacionestics.auxiliares.AnalitycsAplication;
import com.gru.cajaaplicacionestics.auxiliares.PaginationErrorCallBack;
import com.gru.cajaaplicacionestics.view.fragment.FragmentNovedades;

public class NovedadActivity extends AppCompatActivity implements PaginationErrorCallBack {

    private NovedadActivity.SectionPagenAdapter mSectionsPagerAdapter;
    private Tracker mTracker;

    private ViewPager mViewPager;

    FragmentNovedades fragment2018, fragment2017;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_novedad);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitle("Novedades");

        mSectionsPagerAdapter = new NovedadActivity.SectionPagenAdapter(getSupportFragmentManager());

        mViewPager=(ViewPager)findViewById(R.id.containerViewPager);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        TabLayout tabLayout = (TabLayout)findViewById(R.id.tabs);



        tabLayout.setupWithViewPager(mViewPager);

        AnalitycsAplication aplication = (AnalitycsAplication) getApplication();
        mTracker = aplication.getDefaultTracker();

    }


    @Override
    public void reintentar(int arrayCount, @Nullable Boolean hayError) {
        Log.e("estoy en","reintentar");
        mSectionsPagerAdapter = new NovedadActivity.SectionPagenAdapter(getSupportFragmentManager());

    }

    public class SectionPagenAdapter extends FragmentPagerAdapter {
        public SectionPagenAdapter(FragmentManager fm)
        {
            super(fm);
        }
        @Override
        public Fragment getItem(int position) {
            switch (position){
                case 0:
                    fragment2018 = new FragmentNovedades();
                    fragment2018.setArguments(sendYear("2018"));
                    return fragment2018;
                case 1:
                    fragment2017 = new FragmentNovedades();
                    fragment2017.setArguments(sendYear("2017"));
                    return fragment2017;
                default:
                    return null;

            }

        }


        private Bundle sendYear(String año) //envio el año del tab a buscar
        {
            Bundle bundle = new Bundle();
            bundle.putString("año",año);
            return  bundle;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position){
                case 0:
                    return "Año 2018";
                case 1:
                    return "Año 2017";
            }
            return null;
        }

        @Override
        public int getCount() {
            return 2;
        }

    }

    @Override
    protected void onResume() {
        super.onResume();
        mTracker.setScreenName("Novedades");
        mTracker.send(new HitBuilders.ScreenViewBuilder().build());
    }
}
