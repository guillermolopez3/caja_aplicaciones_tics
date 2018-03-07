package com.gru.cajaaplicacionestics.auxiliares;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

/**
 * Created by guill on 23/02/2018.
 */

public abstract class PaginationScrollListener extends RecyclerView.OnScrollListener
{
    LinearLayoutManager layoutManager;

    public PaginationScrollListener(LinearLayoutManager layoutManager) {
        this.layoutManager = layoutManager;
    }

    @Override
    public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
        super.onScrolled(recyclerView, dx, dy);

        int visibleItemCount = layoutManager.getChildCount(); //cuantos post veo en la pantalla en ese momento
        int totalItemCount = layoutManager.getItemCount(); // cantidad de items cargados en la lista
        int firstVisibleItemPosition = layoutManager.findFirstVisibleItemPosition(); //que posicion del la lista es la que esta arriba de todos y lo veo

        //Log.e("visible, total,first",visibleItemCount + "-" + totalItemCount + "-" + firstVisibleItemPosition);

        if (!isLoading() && !isLastPage()) //si no esta el footerloading y no estamos en la última pagina de la paginacion
        {
            if ((visibleItemCount + firstVisibleItemPosition) >= totalItemCount
                    && firstVisibleItemPosition >= 0) {
                loadMoreItems();
            }
        }
    }

    protected abstract void loadMoreItems();

    public abstract int getTotalPageCount();

    public abstract boolean isLastPage();

    public abstract boolean isLoading();
}
