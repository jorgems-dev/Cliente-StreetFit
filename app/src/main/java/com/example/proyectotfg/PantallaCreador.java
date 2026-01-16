package com.example.proyectotfg;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.utils.NavegacionUtils;

public class PantallaCreador extends AppCompatActivity {
    private Toolbar toolbarCreador;
    private Button btnRegistrarAtleta, btnRegistrarEntrenador;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_pantalla_creador);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        btnRegistrarAtleta = findViewById(R.id.btnRegistrarAtleta);
        btnRegistrarEntrenador = findViewById(R.id.btnRegistrarEntrenador);
        toolbarCreador = findViewById(R.id.toolbarPRA);
        setSupportActionBar(toolbarCreador);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayShowTitleEnabled(false);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setHomeAsUpIndicator(R.drawable.baseline_arrow_back_24);
        }

        btnRegistrarAtleta.setOnClickListener(v ->{
            NavegacionUtils.iniciarActividad(this, PantallaRegistroAtletas.class);
        });

        btnRegistrarEntrenador.setOnClickListener(v ->{
            NavegacionUtils.iniciarActividad(this, PantallaRegistroEntrenadores.class);
        });
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}