package com.gru.cajaaplicacionestics.auxiliares;

import android.support.annotation.Nullable;

import com.android.volley.VolleyError;

import org.json.JSONException;

/**
 * Created by guill on 25/02/2018.
 */

public interface PaginationErrorCallBack {
    void reintentar(int arrayCount,@Nullable Boolean hayError);
}
