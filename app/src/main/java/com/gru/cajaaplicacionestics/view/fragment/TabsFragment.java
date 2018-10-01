package com.gru.cajaaplicacionestics.view.fragment;

import android.os.Bundle;
import androidx.annotation.Nullable;
import com.google.android.material.tabs.TabLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.gru.cajaaplicacionestics.R;

public class TabsFragment extends Fragment
{
    ViewPager pager;
    TabLayout tabLayout;
    public TabsFragment(){}

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_nepestana,container,false);
        pager = view.findViewById(R.id.containerViewPager);
        tabLayout = view.findViewById(R.id.tabs);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        TabsFragment.SectionPagenAdapter adapter = new SectionPagenAdapter(getChildFragmentManager());
        pager.setAdapter(adapter);
        tabLayout.setupWithViewPager(pager);
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
                    NEFragment fragment = new NEFragment();
                    fragment.setArguments(sendYear("verEscuchar"));
                    return fragment;
                case 1:
                    NEFragment fragment1 = new NEFragment();
                    fragment1.setArguments(sendYear("leer"));
                    return fragment1;
                case 2:
                    NEFragment fragment2 = new NEFragment();
                    fragment2.setArguments(sendYear("hacer"));
                    return fragment2;
                default:
                    return null;

            }

        }


        private Bundle sendYear(String seccion) //envio el año del tab a buscar
        {
            Bundle bundle = new Bundle();
            bundle.putString("seccion",seccion);
            return  bundle;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position){
                case 0:
                    return "Ver y Escuchar";
                case 1:
                    return "Leer";
                case 2:
                    return "Hacer";
            }
            return null;
        }

        @Override
        public int getCount() {
            return 3;
        }

    }
}
