package com.example.proyectotfg;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
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

import com.example.adapters.EntrenadorAdapter;
import com.example.cliente.ApiCliente;
import com.example.pojos.AtletasEntity;
import com.example.pojos.ContratosEntity;
import com.example.pojos.EntrenadoresEntity;
import com.example.service.ApiService;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PantallaEntrenadoresContratados extends AppCompatActivity {
    private Toolbar toolbarEntrenadoresContratados;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_pantalla_entrenadores_contratados);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        toolbarEntrenadoresContratados = findViewById(R.id.toolbarEntrenadoresContratados);
        setSupportActionBar(toolbarEntrenadoresContratados);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayShowTitleEnabled(false);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setHomeAsUpIndicator(R.drawable.baseline_arrow_back_24);
        }

        ApiService apiService = ApiCliente.getRetrofit().create(ApiService.class);

        RecyclerView view = findViewById(R.id.viewEntrenadoresContratados);
        view.setLayoutManager(new LinearLayoutManager(this));
        List<EntrenadoresEntity> listaEntrenadoresContratados = new ArrayList<>();
        EntrenadorAdapter adaptador = new EntrenadorAdapter(this, listaEntrenadoresContratados, entrenador -> {
            AlertDialog dialogo = new AlertDialog.Builder(PantallaEntrenadoresContratados.this)
                    .setMessage("¿Quieres prescindir de los servicios de " + entrenador.getNombre() + "?")
                    .setCancelable(false)
                    .setPositiveButton("Sí", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            apiService.obtenerEntrenadorPorId(entrenador.getId()).enqueue(new Callback<EntrenadoresEntity>() {
                                @Override
                                public void onResponse(Call<EntrenadoresEntity> call, Response<EntrenadoresEntity> response) {
                                    if (response.isSuccessful() && response.body() != null) {
                                        EntrenadoresEntity entrenadorSeleccionado = response.body();

                                        Long idAtleta = getIntent().getLongExtra("id", -1);

                                        if (idAtleta == -1) {
                                            Toast.makeText(PantallaEntrenadoresContratados.this, "No se pudo coger el id del atleta", Toast.LENGTH_SHORT).show();
                                            return;
                                        }
                                        ContratosEntity contrato = new ContratosEntity(entrenadorSeleccionado, new AtletasEntity(idAtleta));
                                        apiService.realizarBajaContratacion(contrato).enqueue(new Callback<Void>() {
                                            @Override
                                            public void onResponse(Call<Void> call, Response<Void> response) {
                                                if (response.isSuccessful()) {
                                                    Toast.makeText(PantallaEntrenadoresContratados.this, "Has dado de baja el contrato con " + entrenador.getNombre(), Toast.LENGTH_SHORT).show();
                                                    finish();
                                                } else {
                                                    Toast.makeText(PantallaEntrenadoresContratados.this, "No se pudo realizar la baja", Toast.LENGTH_SHORT).show();
                                                }
                                            }
                                            @Override
                                            public void onFailure(Call<Void> call, Throwable t) {
                                                Log.e("API", "Fallo al conectar: " + t.getMessage());
                                            }
                                        });
                                    }
                                }
                                @Override
                                public void onFailure(Call<EntrenadoresEntity> call, Throwable t) {
                                    Log.e("API", "Fallo al conectar: " + t.getMessage());

                                }
                            });
                        }
                    })
                    .setNegativeButton("No", null)
                    .create();
            dialogo.show();

            dialogo.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(Color.BLACK);
            dialogo.getButton(AlertDialog.BUTTON_NEGATIVE).setTextColor(Color.BLACK);
        });
        view.setAdapter(adaptador);

        Long idAtleta = getIntent().getLongExtra("id", -1);

        if (idAtleta == -1) {
            Toast.makeText(PantallaEntrenadoresContratados.this, "No se pudo coger el id del atleta", Toast.LENGTH_SHORT).show();
            return;
        }

        apiService.obtenerListaEntrenadoresContratados(idAtleta).enqueue(new Callback<List<EntrenadoresEntity>>() {
            @Override
            public void onResponse(Call<List<EntrenadoresEntity>> call, Response<List<EntrenadoresEntity>> response) {
                if (response.isSuccessful() && response.body() != null) {
                   listaEntrenadoresContratados.clear();
                   listaEntrenadoresContratados.addAll(response.body());
                   adaptador.notifyDataSetChanged();
                } else if (response.code() == 409) {
                    listaEntrenadoresContratados.add(new EntrenadoresEntity("No hay entrenadores disponibles aún"));
                    Log.e("RetrofitGET", "Error en la respuesta: " + response.code());
                }
            }
            @Override
            public void onFailure(Call<List<EntrenadoresEntity>> call, Throwable t) {
                Log.e("RetrofitGET", "Fallo en la conexión: " + t.getMessage());
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