package com.gru.cajaaplicacionestics.estudiar_info;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;

import com.google.android.material.tabs.TabLayout;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.gru.cajaaplicacionestics.R;
import com.gru.cajaaplicacionestics.auxiliares.MetodosComunes;
import com.gru.cajaaplicacionestics.auxiliares.PaginationErrorCallBack;
import com.gru.cajaaplicacionestics.view.fragment.FragmentNovedades;

import java.util.ArrayList;

public class EstudiarActivity extends AppCompatActivity implements PaginationErrorCallBack {

    private EstudiarActivity.SectionPagenAdapter mSectionsPagerAdapter;

    private ViewPager mViewPager;

    EstudiarInfoFragment estudiarCarrera, estudiarInstitucion;

    ArrayList<String> chkSeleccionado = new ArrayList<>();

    private String query_filter="";


    private boolean estoy_lista_instit = true; //indica si estoy en la lista de instituciones o en la de carreras

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
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.search_menu_donde_estudiar, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Bundle bundle = new Bundle();
        if(item.getItemId()==R.id.action_search_estudiar){

        }
        else if(item.getItemId() == R.id.nav_filter_estudiar)
        {
            //startActivity(new Intent(MenuActivity.this,ProfileActivity.class));
            abrirDialog();
        }
        return super.onOptionsItemSelected(item);
    }

    private void abrirDialog()
    {
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        LayoutInflater inflater = getLayoutInflater();

        final View view = inflater.inflate(R.layout.dialog_filtro_estudiar,null);

        builder.setView(view);

        Button no =  view.findViewById(R.id.dialogNo);
        Button si =  view.findViewById(R.id.dialogSi);

        final CheckBox cba,san,dean,capilla,falda,carlos,alta,jesus,oncativo,oliva,villa,bell,leones,riot,rioc;

        cba = view.findViewById(R.id.cordoba);
        san = view.findViewById(R.id.san_francisco);
        dean = view.findViewById(R.id.dean_funes);
        capilla = view.findViewById(R.id.capilla);
        falda = view.findViewById(R.id.la_falda);
        carlos = view.findViewById(R.id.carlos);
        alta = view.findViewById(R.id.alta);
        jesus = view.findViewById(R.id.jesus);
        oncativo = view.findViewById(R.id.oncativo);
        oliva = view.findViewById(R.id.oliva);
        villa = view.findViewById(R.id.villa);
        bell = view.findViewById(R.id.bell);
        leones = view.findViewById(R.id.leone);
        riot = view.findViewById(R.id.rio_t);
        rioc = view.findViewById(R.id.rio_c);



        final AlertDialog alertDialog = builder.create();
        alertDialog.show();
        alertDialog.setCancelable(false);
        no.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();
            }
        });

        si.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                armarConsulta(cba,"cordoba");
                armarConsulta(san,"san francisco");
                armarConsulta(dean,"dean funes");
                armarConsulta(capilla,"capilla");
                armarConsulta(falda,"la falda");
                armarConsulta(carlos,"carlos paz");
                armarConsulta(alta,"alta gracia");
                armarConsulta(jesus,"jesus mar");
                armarConsulta(oncativo,"oncativo");
                armarConsulta(oliva,"oliva");
                armarConsulta(villa,"villa mar");
                armarConsulta(bell,"bell ville");
                armarConsulta(leones,"leones");
                armarConsulta(riot,"rio tercero");
                armarConsulta(rioc,"rio cuarto");
                Log.e("consulta", query_filter);
                actualizarFragment();
                alertDialog.dismiss();

            }
        });



    }

    //concatena los nombres de los filtros seleccionados
    private void armarConsulta(CheckBox chk, String nombre)
    {
        if(chk.isChecked()){
            query_filter += nombre + ",";
        }
    }

    private void actualizarFragment()
    {
        /*Bundle bundle = new Bundle();
        bundle.putString("query",query_filter);
        bundle.putString("seccion","institucion");
        EstudiarInfoFragment n_estudiarInstitucion = new EstudiarInfoFragment();
        n_estudiarInstitucion.setArguments(bundle);
        estoy_lista_instit = true;*/

        //mSectionsPagerAdapter.notifyDataSetChanged();
        //getSupportFragmentManager().beginTransaction().detach(estudiarInstitucion).attach(n_estudiarInstitucion).commit();
        estudiarInstitucion.actualizarFragment(query_filter);
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
                    estudiarInstitucion = new EstudiarInfoFragment();
                    estudiarInstitucion.setArguments(sendSeccion("institucion"));
                    estoy_lista_instit = true;
                    return estudiarInstitucion;
                case 1:
                    estudiarCarrera = new EstudiarInfoFragment();
                    estudiarCarrera.setArguments(sendSeccion("carrera"));
                    estoy_lista_instit = false;
                    return estudiarCarrera;
                default:
                    return null;

            }

        }


        private Bundle sendSeccion(String seccion) //envio el año del tab a buscar
        {
            Bundle bundle = new Bundle();
            bundle.putString("seccion",seccion);
            bundle.putString("query",query_filter);
            return  bundle;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position){
                case 0:
                    return "Instituciones";
                case 1:
                    return "Carreras";
            }
            return null;
        }

        @Override
        public int getCount() {
            return 2;
        }

    }
}
