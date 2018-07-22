package com.gru.cajaaplicacionestics.view.semana_tic;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomSheetBehavior;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;

import com.gru.cajaaplicacionestics.R;
import com.gru.cajaaplicacionestics.auxiliares.PaginationErrorCallBack;
import com.gru.cajaaplicacionestics.view.fragment.FragmentNovedades;
import com.gru.cajaaplicacionestics.view.fragment.FragmentST;

public class Agenda_ST extends AppCompatActivity implements PaginationErrorCallBack
{
    private Agenda_ST.SectionPagenAdapter mSectionsPagerAdapter;
    private ViewPager mViewPager;
    private FloatingActionButton fab;
    private ImageButton btn_bajar;
    private BottomSheetBehavior bsb;
    FragmentST fragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agenda_st);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        //toolbar.setTitle("Agenda");

        mSectionsPagerAdapter = new Agenda_ST.SectionPagenAdapter(getSupportFragmentManager());

        mViewPager = findViewById(R.id.containerViewPager);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        TabLayout tabLayout = findViewById(R.id.tabs);


        tabLayout.setupWithViewPager(mViewPager);

        fab         = findViewById(R.id.fab);
        btn_bajar    = findViewById(R.id.cerrarMenuBottonSeet);
        LinearLayout bts = findViewById(R.id.bottomSheet);
        bsb = BottomSheetBehavior.from(bts);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bsb.setState(BottomSheetBehavior.STATE_EXPANDED);
                fab.hide();
            }
        });

        btn_bajar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                collapsedBottonSheetAndShowFab();
            }
        });

    }

    private void collapsedBottonSheetAndShowFab()
    {
        bsb.setState(BottomSheetBehavior.STATE_COLLAPSED);
        fab.show();
    }

    @Override
    public void reintentar(int arrayCount, @Nullable Boolean hayError) {
        Log.e("estoy en","reintentar");
       // mSectionsPagerAdapter = new NovedadActivity.SectionPagenAdapter(getSupportFragmentManager());

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
                    fragment = new FragmentST();
                    fragment.setArguments(sendDia("13"));
                    return fragment;
                case 1:
                    fragment = new FragmentST();
                    fragment.setArguments(sendDia("14"));
                    return fragment;
                case 2:
                    fragment = new FragmentST();
                    fragment.setArguments(sendDia("15"));
                    return fragment;
                case 3:
                    fragment = new FragmentST();
                    fragment.setArguments(sendDia("16"));
                    return fragment;
                case 4:
                    fragment = new FragmentST();
                    fragment.setArguments(sendDia("17"));
                    return fragment;
                default:
                    return null;

            }

        }


        private Bundle sendDia(String dia) //envio el año del tab a buscar
        {
            Bundle bundle = new Bundle();
            bundle.putString("dia",dia);
            return  bundle;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position){
                case 0:
                    return "13 de agosto";
                case 1:
                    return "14 de agosto";
                case 2:
                    return "15 de agosto";
                case 3:
                    return "16 de agosto";
                case 4:
                    return "17 de agosto";
            }
            return null;
        }

        @Override
        public int getCount() {
            return 5;
        }

    }
}
