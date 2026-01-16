package com.example.utils;

import android.widget.EditText;

import com.example.pojos.AtletasEntity;
import com.example.pojos.EntrenadoresEntity;

public class EntrenadoresUtils {
    /**
     * Comprobación de los campos vacios del entrenador
     * @param inpNombre
     * @param inpApellidos
     * @param inpCorreo
     * @param inpPass1
     * @param inpPass2
     * @param inpEspecialidad
     * @param inpExperiencia
     * @return
     */
    public static boolean comprobarCamposVacios(EditText inpNombre, EditText inpApellidos, EditText inpCorreo, EditText inpPass1, EditText inpPass2, EditText inpEspecialidad, EditText inpExperiencia) {
        boolean hayError = false;

        if (inpNombre != null) inpNombre.setError(null);
        if (inpApellidos != null) inpApellidos.setError(null);
        if (inpCorreo != null) inpCorreo.setError(null);
        if (inpPass1 != null) inpPass1.setError(null);
        if (inpPass2 != null) inpPass2.setError(null);
        if (inpEspecialidad != null) inpEspecialidad.setError(null);
        if (inpExperiencia != null) inpExperiencia.setError(null);

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

        if (inpEspecialidad == null || inpEspecialidad.getText() == null || inpEspecialidad.getText().toString().trim().isEmpty()) {
            if (inpEspecialidad != null) inpEspecialidad.setError("Campo obligatorio");
            hayError = true;
        }

        if (inpExperiencia == null || inpExperiencia.getText() == null || inpExperiencia.getText().toString().trim().isEmpty()) {
            if (inpExperiencia != null) inpExperiencia.setError("Campo obligatorio");
            hayError = true;
        }

        return hayError;
    }

}
