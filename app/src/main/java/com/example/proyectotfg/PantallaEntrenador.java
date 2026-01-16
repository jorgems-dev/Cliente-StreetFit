package com.example.proyectotfg;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class PantallaEntrenador extends AppCompatActivity {
    private TextView textoBienvenida;
    private Button btnVerAtletas,btnCrearRutina, btnVerRutinas;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_pantalla_entrenador);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        textoBienvenida = findViewById(R.id.textBienvenidaEntrenador);
        btnVerAtletas = findViewById(R.id.btnVerAtletas);
        btnCrearRutina = findViewById(R.id.btnCrearRutinaEntrenadores);
        btnVerRutinas = findViewById(R.id.btnVerRutinasEntrenadores);
        String nombre = getIntent().getStringExtra("nombre");
        textoBienvenida.setText(textoBienvenida.getText() + nombre);

        btnVerAtletas.setOnClickListener(v ->{
            Intent intent = new Intent(PantallaEntrenador.this, PantallaAtletasDisponibles.class);
            intent.putExtra("id", getIntent().getLongExtra("id", -1));
            intent.putExtra("correo", getIntent().getStringExtra("correo"));
            startActivity(intent);

        });

        btnCrearRutina.setOnClickListener(v ->{
            Intent intent = new Intent(PantallaEntrenador.this, PantallaCrearRutinas.class);
            intent.putExtra("id", getIntent().getLongExtra("id", -1));
            intent.putExtra("correo", getIntent().getStringExtra("correo"));
            startActivity(intent);
        });
        btnVerRutinas.setOnClickListener(v ->{
            Intent intent = new Intent(PantallaEntrenador.this, PantallaVerRutinas.class);
            intent.putExtra("id", getIntent().getLongExtra("id", -1));
            intent.putExtra("correo", getIntent().getStringExtra("correo"));
            startActivity(intent);
        });
    }
}