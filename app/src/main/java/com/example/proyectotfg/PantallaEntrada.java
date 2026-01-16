package com.example.proyectotfg;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import com.example.manager.AutentificacionManager;
import com.example.utils.ContraPasswordUtils;
import com.example.utils.ManagerUtils;
import com.example.utils.NavegacionUtils;

public class PantallaEntrada extends AppCompatActivity {
    private EditText inpCorreo, inpPassword;
    private CheckBox isCheck;
    private Button btnIniciarSesion, btnRegistrar;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_pantalla_entrada);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        inpCorreo = findViewById(R.id.inpCorreo);
        inpPassword = findViewById(R.id.inpPassword);
        isCheck = findViewById(R.id.isCheck);
        btnIniciarSesion = findViewById(R.id.btnIniciarSesion);
        btnRegistrar = findViewById(R.id.btnRegistrar);

        ContraPasswordUtils.verPassword(isCheck, inpPassword);

        btnIniciarSesion.setOnClickListener(v ->{
            String correo = inpCorreo.getText() != null ? inpCorreo.getText().toString().trim() : "";
            String contraPassword = inpPassword.getText() != null ? inpPassword.getText().toString().trim() : "";

            if (ManagerUtils.loginVacio(inpCorreo, inpPassword)) {
                return;
            }

            AutentificacionManager manager = new AutentificacionManager(this);
            manager.login(correo, contraPassword);

        });

        btnRegistrar.setOnClickListener(v -> {
            NavegacionUtils.iniciarActividad(this, PantallaCreador.class);
        });

    }

}