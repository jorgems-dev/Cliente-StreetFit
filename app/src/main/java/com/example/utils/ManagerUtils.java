package com.example.utils;

import android.widget.EditText;

public class ManagerUtils {
    public static boolean loginVacio(EditText inpCorreo, EditText inpContraPassword) {
        boolean hayError = false;

        if (inpCorreo != null)
            inpCorreo.setError(null);

        if (inpContraPassword != null)
            inpContraPassword.setError(null);

        if (inpCorreo == null || inpCorreo.getText() == null || inpCorreo.getText().toString().trim().isEmpty()) {
            if (inpCorreo != null) inpCorreo.setError("Tienes que introducir el correo");
            hayError = true;
        }

        if (inpContraPassword == null || inpContraPassword.getText() == null || inpContraPassword.getText().toString().trim().isEmpty()) {
            if (inpContraPassword != null) inpContraPassword.setError("Tienes que introducir la contraseña");
            hayError = true;
        }

        return hayError;
    }

}
