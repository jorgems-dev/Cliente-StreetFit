package com.example.utils;

import android.widget.EditText;

import com.example.pojos.AtletasEntity;

public class AtletasUtils {

    /**
     * Comprobación de los campos vacios del atleta.
     * @param inpNombre
     * @param inpApellidos
     * @param inpCorreo
     * @param inpPass1
     * @param inpPass2
     * @return
     */
    public static boolean comprobarCamposVacios(EditText inpNombre, EditText inpApellidos,
                                                EditText inpCorreo, EditText inpPass1, EditText inpPass2) {
        boolean hayError = false;

        if (inpNombre != null) inpNombre.setError(null);
        if (inpApellidos != null) inpApellidos.setError(null);
        if (inpCorreo != null) inpCorreo.setError(null);
        if (inpPass1 != null) inpPass1.setError(null);
        if (inpPass2 != null) inpPass2.setError(null);

        if (inpNombre == null || inpNombre.getText() == null || inpNombre.getText().toString().trim().isEmpty()) {
            if (inpNombre != null) inpNombre.setError("Campo obligatorio");
            hayError = true;
        }

        if (inpApellidos == null || inpApellidos.getText() == null || inpApellidos.getText().toString().trim().isEmpty()) {
            if (inpApellidos != null) inpApellidos.setError("Campo obligatorio");
            hayError = true;
        }

        if (inpCorreo == null || inpCorreo.getText() == null || inpCorreo.getText().toString().trim().isEmpty()) {
            if (inpCorreo != null) inpCorreo.setError("Campo obligatorio");
            hayError = true;
        }

        if (inpPass1 == null || inpPass1.getText() == null || inpPass1.getText().toString().trim().isEmpty()) {
            if (inpPass1 != null) inpPass1.setError("Campo obligatorio");
            hayError = true;
        }

        if (inpPass2 == null || inpPass2.getText() == null || inpPass2.getText().toString().trim().isEmpty()) {
            if (inpPass2 != null) inpPass2.setError("Campo obligatorio");
            hayError = true;
        }

        return hayError;
    }

}
