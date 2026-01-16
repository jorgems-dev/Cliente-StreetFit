package com.example.utils;

import android.util.Log;
import android.widget.EditText;

import retrofit2.Response;

public class RutinasUtils {
    /**
     * Comprueba que los campos no estén vacíos y que los numéricos sean válidos
     *
     * @param inpNombreEjercicio
     * @param inpGrupoMusuclar
     * @param inpSeriesEjercicio
     * @param inpRepeticionesEjercicio
     * @param inpPesoEjercicio
     * @return true si hay error, false si todo está correcto
     */
    public static boolean comprobarCamposEjercicioVacios(EditText inpNombreEjercicio, EditText inpGrupoMusuclar, EditText inpSeriesEjercicio, EditText inpRepeticionesEjercicio, EditText inpPesoEjercicio) {
        boolean hayError = false;

        if (inpNombreEjercicio != null) inpNombreEjercicio.setError(null);
        if (inpGrupoMusuclar != null) inpGrupoMusuclar.setError(null);
        if (inpSeriesEjercicio != null) inpSeriesEjercicio.setError(null);
        if (inpRepeticionesEjercicio != null) inpRepeticionesEjercicio.setError(null);
        if (inpPesoEjercicio != null) inpPesoEjercicio.setError(null);

        if (inpNombreEjercicio == null || inpNombreEjercicio.getText() == null || inpNombreEjercicio.getText().toString().trim().isEmpty()) {
            if (inpNombreEjercicio != null) inpNombreEjercicio.setError("Campo obligatorio");
            hayError = true;
        }

        if (inpGrupoMusuclar == null || inpGrupoMusuclar.getText() == null || inpGrupoMusuclar.getText().toString().trim().isEmpty()) {
            if (inpGrupoMusuclar != null) inpGrupoMusuclar.setError("Campo obligatorio");
            hayError = true;
        }

        String seriesText = inpSeriesEjercicio != null ? inpSeriesEjercicio.getText().toString().trim() : "";
        if (seriesText.isEmpty()) {
            if (inpSeriesEjercicio != null) inpSeriesEjercicio.setError("Campo obligatorio");
            hayError = true;
        } else {
            try {
                Integer.parseInt(seriesText);
            } catch (NumberFormatException e) {
                if (inpSeriesEjercicio != null) inpSeriesEjercicio.setError("Debe ser un número");
                hayError = true;
            }
        }

        String repeticionesText = inpRepeticionesEjercicio != null ? inpRepeticionesEjercicio.getText().toString().trim() : "";
        if (repeticionesText.isEmpty()) {
            if (inpRepeticionesEjercicio != null) inpRepeticionesEjercicio.setError("Campo obligatorio");
            hayError = true;
        } else {
            try {
                Integer.parseInt(repeticionesText);
            } catch (NumberFormatException e) {
                if (inpRepeticionesEjercicio != null) inpRepeticionesEjercicio.setError("Debe ser un número");
                hayError = true;
            }
        }

        String pesoText = inpPesoEjercicio != null ? inpPesoEjercicio.getText().toString().trim() : "";
        if (pesoText.isEmpty()) {
            if (inpPesoEjercicio != null) inpPesoEjercicio.setError("Campo obligatorio");
            hayError = true;
        } else {
            try {
                Float.parseFloat(pesoText);
            } catch (NumberFormatException e) {
                if (inpPesoEjercicio != null) inpPesoEjercicio.setError("Debe ser un número");
                hayError = true;
            }
        }

        return hayError;
    }
    /**
     * Comprobacion de que un correo ya existe.
     * @param response
     * @param inpNombre
     */
    public static void comprobarIdentificacionNombreExiste(Response<?> response, EditText inpNombre) {
        try {
            String error = response.errorBody().string();

            if (error.contains("nombre")) {
                inpNombre.setError("Nombre de rutina existentente");
            }

        } catch (Exception e) {
            Log.e("API", "Fallo en error code: " + e.getMessage(), e);
        }
    }
}
