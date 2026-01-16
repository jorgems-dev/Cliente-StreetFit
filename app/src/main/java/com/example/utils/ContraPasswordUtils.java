package com.example.utils;

import android.text.InputType;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;

import com.example.pojos.AtletasEntity;


public class ContraPasswordUtils {

    /**
     * Ver contraseña de la pantalla del login.
     * @param checkBox
     * @param editText
     */
    public static void verPassword(CheckBox checkBox, EditText editText) {
        checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (checkBox.isChecked()) {
                    editText.setInputType(InputType.TYPE_CLASS_TEXT);
                } else {
                    editText.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                }
                editText.setSelection(editText.getText().length());
            }
        });
    }

    /**
     * Ver ambas contraseñas de la pantalla de registro.
     * @param checkBox
     * @param editText1
     * @param editText2
     */
    public static void verPasswordDoble(CheckBox checkBox, EditText editText1, EditText editText2) {
        checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (checkBox.isChecked()) {
                    editText1.setInputType(InputType.TYPE_CLASS_TEXT);
                    editText2.setInputType(InputType.TYPE_CLASS_TEXT);
                } else {
                    editText1.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                    editText2.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                }
                editText1.setSelection(editText1.getText().length());
                editText2.setSelection(editText2.getText().length());

            }
        });
    }

    /**
     * Comprobación de que las contraseñas son iguales.
     * @param pass1
     * @param pass2
     * @return
     */
    public static Boolean matchPassword(EditText pass1, EditText pass2) {
        Boolean hayError = false;
        String inpPass1 = pass1.getText().toString();
        String inpPass2 = pass2.getText().toString();

        if (!inpPass1.equals(inpPass2)) {
            pass1.setError("Las contraseñas no coinciden");
            pass2.setError("Las contraseñas no coinciden");
            hayError = true;
        }
        return hayError;
    }
}
