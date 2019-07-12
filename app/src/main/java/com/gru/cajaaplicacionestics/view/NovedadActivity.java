package com.gru.cajaaplicacionestics.view;

import androidx.annotation.Nullable;
import com.google.android.material.tabs.TabLayout;
import androidx.appcompat.app.AppCompatActivity;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;
import android.os.Bundle;
import android.util.Log;

import com.gru.cajaaplicacionestics.R;
import com.gru.cajaaplicacionestics.auxiliares.MetodosComunes;
import com.gru.cajaaplicacionestics.auxiliares.PaginationErrorCallBack;
import com.gru.cajaaplicacionestics.estudiar_info.EstudiarInfo;
import com.gru.cajaaplicacionestics.view.fragment.FragmentNovedades;

public class NovedadActivity extends AppCompatActivity implements PaginationErrorCallBack {

    private NovedadActivity.SectionPagenAdapter mSectionsPagerAdapter;

    private ViewPager mViewPager;

    FragmentNovedades fragment2018, fragment2017;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_novedad);


        MetodosComunes.showToolbar("Novedades",true,this);

        mSectionsPagerAdapter = new NovedadActivity.SectionPagenAdapter(getSupportFragmentManager());

        mViewPager = findViewById(R.id.containerViewPager);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        TabLayout tabLayout = findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(mViewPager);

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
                    fragment2018.setArguments(sendYear("2019"));
                    return fragment2018;
                case 1:
                    fragment2017 = new FragmentNovedades();
                    fragment2017.setArguments(sendYear("2018"));
                    return fragment2017;
                    /*Fragment frag = new EstudiarInfo();
                    return frag;*/
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
                    return "Año 2019";
                case 1:
                    return "Año 2018";
            }
            return null;
        }

        @Override
        public int getCount() {
            return 2;
        }

    }


}
