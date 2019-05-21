package com.gru.cajaaplicacionestics.view;

import android.os.Bundle;
import androidx.annotation.Nullable;
import com.google.android.material.tabs.TabLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;
import androidx.appcompat.app.AppCompatActivity;
import android.util.Log;

import com.gru.cajaaplicacionestics.R;
import com.gru.cajaaplicacionestics.auxiliares.MetodosComunes;
import com.gru.cajaaplicacionestics.auxiliares.PaginationErrorCallBack;
import com.gru.cajaaplicacionestics.view.FragmentPpade.FragmentCohortes;
import com.gru.cajaaplicacionestics.view.FragmentPpade.FragmentEquipo;
import com.gru.cajaaplicacionestics.view.FragmentPpade.FragmentPresentacion;
import com.gru.cajaaplicacionestics.view.fragment.FragmentNovedades;

public class PpadeNewActivity extends AppCompatActivity implements PaginationErrorCallBack {

    private PpadeNewActivity.SectionPagenAdapter mSectionsPagerAdapter;

    private ViewPager mViewPager;

    FragmentNovedades fragment2018, fragment2017;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_cohortes);


        MetodosComunes.showToolbar("PP-ADE",true,this);

        mSectionsPagerAdapter = new PpadeNewActivity.SectionPagenAdapter(getSupportFragmentManager());

        mViewPager = findViewById(R.id.containerViewPager);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        TabLayout tabLayout = findViewById(R.id.tabs);


        tabLayout.setupWithViewPager(mViewPager);

    }


    @Override
    public void reintentar(int arrayCount, @Nullable Boolean hayError) {
        Log.e("estoy en","reintentar");
        mSectionsPagerAdapter = new PpadeNewActivity.SectionPagenAdapter(getSupportFragmentManager());

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
                    return  abrirFragment("sexta_co","Sexta cohorte");
                case 1:
                    return  abrirFragment("quinta_co","Quinta cohorte");
                case 2:
                    return  abrirFragment("cuarta_co","Cuarta cohorte");
                case 3:
                   return abrirFragment("tercera_co","Tercera cohorte");
                case 4:
                    return abrirFragment("segunda_co","Segunda cohorte");
                case 5:
                    return abrirFragment("primera_co","Primera cohorte");
                case 6:
                    return new FragmentEquipo();
                case 7:
                    return new FragmentPresentacion();
                default:
                    return null;

            }

        }

        private Fragment abrirFragment(String seccion, String titulo)
        {
            Fragment fragment = new FragmentCohortes();
            Bundle bundle = new Bundle();
            bundle.putString("seccion",seccion);
            fragment.setArguments(bundle);
            return fragment;
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
                    return "6ª Cohorte";
                case 1:
                    return "5ª Cohorte";
                case 2:
                    return "4ª Cohorte";
                case 3:
                    return "3ª Cohorte";
                case 4:
                    return "2ª Cohorte";
                case 5:
                    return "1ª Cohorte";
                case 6:
                    return "Equipo y contacto";
                case 7:
                    return "Presentación";
            }
            return null;
        }

        @Override
        public int getCount() {
            return 8;
        }

    }


}
