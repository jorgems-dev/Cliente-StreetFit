package com.example.proyectotfg;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.ImageButton;
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

public class PantallaContrataciones extends AppCompatActivity {
    private ImageButton imgBtnVerEntrenadores;
    private Toolbar toolbarContrataciones;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_pantalla_contrataciones);

        findViewById(R.id.main).post(() -> {
            ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
                Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
                v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
                return insets;
            });
        });
        Long idAtleta = getIntent().getLongExtra("id", -1);

        imgBtnVerEntrenadores = findViewById(R.id.imgBtnVerEntrenadores);

        toolbarContrataciones = findViewById(R.id.toolbarContrataciones);
        setSupportActionBar(toolbarContrataciones);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayShowTitleEnabled(false);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setHomeAsUpIndicator(R.drawable.baseline_arrow_back_24);
        }
        ApiService apiService = ApiCliente.getRetrofit().create(ApiService.class);

        RecyclerView view = findViewById(R.id.viewEntrenadores);
        view.setLayoutManager(new LinearLayoutManager(this));
        List<EntrenadoresEntity> listaEntrenadoresDisponibles = new ArrayList<>();
        EntrenadorAdapter adaptador = new EntrenadorAdapter(this, listaEntrenadoresDisponibles, entrenador -> {
            AlertDialog dialogo = new AlertDialog.Builder(PantallaContrataciones.this)
                    .setMessage("¿Quieres contratar los servicios de " + entrenador.getNombre() + "?")
                    .setCancelable(false)
                    .setPositiveButton("Sí", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            apiService.obtenerEntrenadorPorId(entrenador.getId()).enqueue(new Callback<EntrenadoresEntity>() {
                                @Override
                                public void onResponse(Call<EntrenadoresEntity> call, Response<EntrenadoresEntity> response) {
                                    if (response.isSuccessful() && response.body() != null) {
                                        EntrenadoresEntity entrenadorSeleccionado = response.body();

                                        if (idAtleta == -1) {
                                            Toast.makeText(PantallaContrataciones.this, "No se pudo coger el id del atleta", Toast.LENGTH_SHORT).show();
                                            return;
                                        }

                                        ContratosEntity contrato = new ContratosEntity(entrenadorSeleccionado, new AtletasEntity(idAtleta));
                                        apiService.realizarContratacion(contrato).enqueue(new Callback<ContratosEntity>() {
                                            @Override
                                            public void onResponse(Call<ContratosEntity> call, Response<ContratosEntity> response) {
                                                if (response.isSuccessful() && response.body() != null) {
                                                    Toast.makeText(PantallaContrataciones.this, "Has contratado a " + entrenador.getNombre(), Toast.LENGTH_SHORT).show();
                                                    finish();
                                                } else {
                                                    Toast.makeText(PantallaContrataciones.this, "No se pudo realizar la contratación", Toast.LENGTH_SHORT).show();
                                                }
                                            }
                                            @Override
                                            public void onFailure(Call<ContratosEntity> call, Throwable t) {
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

        if (idAtleta == -1) {
            Toast.makeText(PantallaContrataciones.this, "No se pudo coger el id del atleta", Toast.LENGTH_SHORT).show();
            return;
        }

        apiService.obtenerListaEntrenadoresNoContratados(idAtleta).enqueue(new Callback<List<EntrenadoresEntity>>() {
            @Override
            public void onResponse(Call<List<EntrenadoresEntity>> call, Response<List<EntrenadoresEntity>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    listaEntrenadoresDisponibles.clear();
                    listaEntrenadoresDisponibles.addAll(response.body());
                    adaptador.notifyDataSetChanged();
                } else if (response.code() == 409) {
                    listaEntrenadoresDisponibles.add(new EntrenadoresEntity("No hay entrenadores disponibles aún"));
                    Log.e("RetrofitGET", "Error en la respuesta: " + response.code());
                }
            }
            @Override
            public void onFailure(Call<List<EntrenadoresEntity>> call, Throwable t) {
                Log.e("RetrofitGET", "Fallo en la conexión: " + t.getMessage());
            }
        });

        imgBtnVerEntrenadores.setOnClickListener(v -> {
            Intent intent = new Intent(PantallaContrataciones.this, PantallaEntrenadoresContratados.class);
            intent.putExtra("id", idAtleta);
            startActivity(intent);
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