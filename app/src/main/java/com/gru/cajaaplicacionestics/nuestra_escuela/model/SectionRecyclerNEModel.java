package com.gru.cajaaplicacionestics.nuestra_escuela.model;

import com.gru.cajaaplicacionestics.model.ModelPost;

import java.util.ArrayList;

//POJO donde tengo el nombre de la seeción del recycler mas los datos a mostrar
public class SectionRecyclerNEModel
{
    private String section;
    private ArrayList<ModelPost> items;

    public SectionRecyclerNEModel(String titulo, ArrayList<ModelPost> post)
    {
        section = titulo;
        items = post;
    }

    public String getSection() {
        return section;
    }

    public ArrayList<ModelPost> getItems() {
        return items;
    }
}
