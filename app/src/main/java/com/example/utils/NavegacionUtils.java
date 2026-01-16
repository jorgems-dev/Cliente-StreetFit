package com.example.utils;

import android.content.Context;
import android.content.Intent;

public class NavegacionUtils {
    /**
     * Para generar una actividad.
     * @param context
     * @param clase
     */
    public static void iniciarActividad(Context context, Class<?> clase) {
        Intent intent = new Intent(context, clase);
        context.startActivity(intent);

    }
}
