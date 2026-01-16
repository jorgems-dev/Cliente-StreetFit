package com.example.proyectotfg;

import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.adapters.AtletasAdapter;
import com.example.cliente.ApiCliente;
import com.example.pojos.AtletasEntity;
import com.example.pojos.RutinasAsignadasEntity;
import com.example.service.ApiService;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PantallaAtletasDisponibles extends AppCompatActivity {
    private Toolbar toolbarContrataciones;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_pantalla_atletas_disponibles);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        toolbarContrataciones = findViewById(R.id.toolbarEntrenadoresContratados);
        setSupportActionBar(toolbarContrataciones);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayShowTitleEnabled(false);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setHomeAsUpIndicator(R.drawable.baseline_arrow_back_24);
        }

        Long idEntrenador = getIntent().getLongExtra("id", -1);

        ApiService apiService = ApiCliente.getRetrofit().create(ApiService.class);
        RecyclerView view = findViewById(R.id.viewAtletas);
        view.setLayoutManager(new LinearLayoutManager(this));
        List<AtletasEntity> listaAtletasDisponibles = new ArrayList<>();
        AtletasAdapter adaptador = new AtletasAdapter(this, listaAtletasDisponibles, atleta -> {
            AlertDialog dialogo = new AlertDialog.Builder(PantallaAtletasDisponibles.this)
                    .setMessage("¿Quieres asignar una rutina a " + atleta.getNombre() + "?")
                    .setCancelable(false)
                    .setPositiveButton("Sí", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                        }
                    })
                    .setNegativeButton("No", null)
                    .create();
        });
        view.setAdapter(adaptador);


        if (idEntrenador == -1) {
            Toast.makeText(PantallaAtletasDisponibles.this, "No se pudo coger el id del entrenador", Toast.LENGTH_SHORT).show();
            return;
        }

        apiService.obtenerListaAtletasDisponibles(idEntrenador).enqueue(new Callback<List<AtletasEntity>>() {
            @Override
            public void onResponse(Call<List<AtletasEntity>> call, Response<List<AtletasEntity>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    listaAtletasDisponibles.clear();
                    listaAtletasDisponibles.addAll(response.body());
                    adaptador.notifyDataSetChanged();
                } else if (response.code() == 409) {
                    listaAtletasDisponibles.add(new AtletasEntity("No hay atletas disponibles aún"));
                    Log.e("RetrofitGET", "Error en la respuesta: " + response.code());
                }
            }

            @Override
            public void onFailure(Call<List<AtletasEntity>> call, Throwable t) {
                Log.e("API", "Fallo al conectar: " + t.getMessage());
            }
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