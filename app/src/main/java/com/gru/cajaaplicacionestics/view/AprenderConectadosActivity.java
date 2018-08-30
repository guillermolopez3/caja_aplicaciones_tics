package com.gru.cajaaplicacionestics.view;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.gru.cajaaplicacionestics.R;
import com.gru.cajaaplicacionestics.auxiliares.MetodosComunes;
import com.gru.cajaaplicacionestics.auxiliares.PaginationErrorCallBack;
import com.gru.cajaaplicacionestics.view.fragment.FragmentAprenConec;

public class AprenderConectadosActivity extends AppCompatActivity implements PaginationErrorCallBack
{
    private SectionAdapter adapter;
    private ViewPager pager;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_aprender_conectados);

        MetodosComunes.showToolbar("Aprender Conectados",true,this);

        adapter = new SectionAdapter(getSupportFragmentManager());
        pager = findViewById(R.id.containerViewPager);
        pager.setAdapter(adapter);

        TabLayout tabLayout = findViewById(R.id.tabs);


        tabLayout.setupWithViewPager(pager);
        MetodosComunes.abrirActivityFab(this);
    }

    @Override
    public void reintentar(int arrayCount, @Nullable Boolean hayError) {

    }

    class SectionAdapter extends FragmentPagerAdapter
    {

        public SectionAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            switch (position){
                case 0:
                    Fragment primaria = new FragmentAprenConec();
                    primaria.setArguments(sendYear(1));
                    return primaria;
                case 1:
                    Fragment secundaria = new FragmentAprenConec();
                    secundaria.setArguments(sendYear(2));
                    return secundaria;
                default:
                    return null;

            }
        }

        private Bundle sendYear(int id) //envio el año del tab a buscar
        {
            Bundle bundle = new Bundle();
            bundle.putInt("id",id);
            return  bundle;
        }

        @Nullable
        @Override
        public CharSequence getPageTitle(int position) {
            switch (position){
                case 0:
                    return "Primaria";
                case 1:
                    return "Secundaria";
            }
            return null;
        }

        @Override
        public int getCount() {
            return 2;
        }
    }
}
