package com.example.utils;

import android.util.Log;
import android.widget.EditText;

import retrofit2.Response;

public class CorreoUtils {
    /**
     * Comprobacion de que un correo ya existe.
     * @param response
     * @param inpCorreo
     */
    public static void comprobarIdentificacionCorreoExiste(Response<?> response, EditText inpCorreo) {
        try {
            String error = response.errorBody().string();

            if (error.contains("correo")) {
                inpCorreo.setError("Correo existentente");
            }

        } catch (Exception e) {
            Log.e("API", "Fallo en error code: " + e.getMessage(), e);
        }
    }
}
