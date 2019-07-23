package com.gru.cajaaplicacionestics.estudiar_info;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;
import android.util.Log;

import com.google.android.material.tabs.TabLayout;
import com.gru.cajaaplicacionestics.R;
import com.gru.cajaaplicacionestics.auxiliares.MetodosComunes;
import com.gru.cajaaplicacionestics.auxiliares.PaginationErrorCallBack;
import com.gru.cajaaplicacionestics.view.fragment.FragmentNovedades;

public class EstudiarActivity extends AppCompatActivity implements PaginationErrorCallBack {

    private EstudiarActivity.SectionPagenAdapter mSectionsPagerAdapter;

    private ViewPager mViewPager;

    EstudiarInfoFragment estudiarCarrera, estudiarInstitucion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_estudiar);


        MetodosComunes.showToolbar("Donde estudiar",true,this);

        mSectionsPagerAdapter = new EstudiarActivity.SectionPagenAdapter(getSupportFragmentManager());

        mViewPager = findViewById(R.id.containerViewPager);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        TabLayout tabLayout = findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(mViewPager);

    }


    @Override
    public void reintentar(int arrayCount, @Nullable Boolean hayError) {
        Log.e("estoy en","reintentar");
        mSectionsPagerAdapter = new EstudiarActivity.SectionPagenAdapter(getSupportFragmentManager());

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
                    estudiarCarrera = new EstudiarInfoFragment();
                    estudiarCarrera.setArguments(sendSeccion("carrera"));
                    return estudiarCarrera;
                case 1:
                    estudiarInstitucion = new EstudiarInfoFragment();
                    estudiarInstitucion.setArguments(sendSeccion("institucion"));
                    return estudiarInstitucion;
                default:
                    return null;

            }

        }


        private Bundle sendSeccion(String seccion) //envio el año del tab a buscar
        {
            Bundle bundle = new Bundle();
            bundle.putString("seccion",seccion);
            return  bundle;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position){
                case 0:
                    return "Carreras";
                case 1:
                    return "Instituciones";
            }
            return null;
        }

        @Override
        public int getCount() {
            return 2;
        }

    }
}
