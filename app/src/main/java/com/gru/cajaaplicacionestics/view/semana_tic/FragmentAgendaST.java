package com.gru.cajaaplicacionestics.view.semana_tic;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.tabs.TabLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.LinearLayout;

import com.gru.cajaaplicacionestics.R;
import com.gru.cajaaplicacionestics.view.fragment.FragmentST;

public class FragmentAgendaST extends Fragment
{
    private SectionPagenAdapter mSectionsPagerAdapter;
    private ViewPager mViewPager;
    private FloatingActionButton fab;
    private ImageButton btn_bajar;
    private BottomSheetBehavior bsb;
    FragmentST fragment;
    TabLayout tabLayout;

    public FragmentAgendaST(){}

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.activity_agenda_st,container,false);

       mSectionsPagerAdapter = new FragmentAgendaST.SectionPagenAdapter(getChildFragmentManager());


        mViewPager = view.findViewById(R.id.containerViewPager);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        tabLayout = view.findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(mViewPager);



        fab         = view.findViewById(R.id.fab);
        btn_bajar    = view.findViewById(R.id.cerrarMenuBottonSeet);

        LinearLayout bts = view.findViewById(R.id.bottomSheet);
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

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        tabLayout.setupWithViewPager(mViewPager);
    }

    private void collapsedBottonSheetAndShowFab()
    {
        bsb.setState(BottomSheetBehavior.STATE_COLLAPSED);
        fab.show();
    }


    class SectionPagenAdapter extends FragmentStatePagerAdapter
    {
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
                    Log.e("estoy en el: ","tab 13");
                    return fragment;
                case 1:
                    fragment = new FragmentST();
                    fragment.setArguments(sendDia("14"));
                    Log.e("estoy en el: ","tab 14");
                    return fragment;
                case 2:
                    fragment = new FragmentST();
                    fragment.setArguments(sendDia("15"));
                    Log.e("estoy en el: ","tab 15");
                    return fragment;
                case 3:
                    fragment = new FragmentST();
                    fragment.setArguments(sendDia("16"));
                    Log.e("estoy en el: ","tab 16");
                    return fragment;
                case 4:
                    fragment = new FragmentST();
                    fragment.setArguments(sendDia("17"));
                    Log.e("estoy en el: ","tab 17");
                    return fragment;
                default:
                    return fragment;

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
