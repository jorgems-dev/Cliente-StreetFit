package com.example.proyectotfg;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.adapters.RutinasAdapter;
import com.example.cliente.ApiCliente;
import com.example.pojos.RutinasEntity;
import com.example.service.ApiService;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PantallaVerRutinas extends AppCompatActivity {
    private Toolbar toolbarVerRutinas;
    private ActivityResultLauncher<Intent> editarRutinaLauncher;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_pantalla_ver_rutinas);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        toolbarVerRutinas = findViewById(R.id.toolbarVerRutinas);
        setSupportActionBar(toolbarVerRutinas);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayShowTitleEnabled(false);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setHomeAsUpIndicator(R.drawable.baseline_arrow_back_24);
        }

        ApiService apiService = ApiCliente.getRetrofit().create(ApiService.class);
        String correo = getIntent().getStringExtra("correo");

        RecyclerView view = findViewById(R.id.viewVerRutinas);
        view.setLayoutManager(new LinearLayoutManager(this));
        List<RutinasEntity> listaRutinas = new ArrayList<>();
        RutinasAdapter adaptador = new RutinasAdapter(this, listaRutinas, rutina ->{
            Intent intent = new Intent(PantallaVerRutinas.this, PantallaDetallesRutina.class);
            intent.putExtra("id", rutina.getId());
            editarRutinaLauncher.launch(intent);
        });
        view.setAdapter(adaptador);

        if (correo == null || correo.isEmpty()) {
            Log.e("RetrofitGET", "Correo: " + correo);
            finish();
            return;
        }

        editarRutinaLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), result ->{
                    if (result.getResultCode() == RESULT_OK && result.getData() != null) {
                        RutinasEntity rutinaActualizada = (RutinasEntity) result.getData()
                                .getSerializableExtra("rutinaActualizada");
                        if (rutinaActualizada != null) {
                            for (int i = 0; i < listaRutinas.size(); i++) {
                                if (listaRutinas.get(i).getId().equals(rutinaActualizada.getId())) {
                                    listaRutinas.set(i, rutinaActualizada);
                                    adaptador.notifyItemChanged(i);
                                    break;
                                }
                            }
                        }
                        Long idEliminada = result.getData().getLongExtra("rutinaEliminadaId", -1);
                        if (idEliminada != -1) {
                            for (int i = 0; i < listaRutinas.size(); i++) {
                                if (listaRutinas.get(i).getId().equals(idEliminada)) {
                                    listaRutinas.remove(i);
                                    adaptador.notifyItemRemoved(i);
                                    break;
                                }
                            }
                        }
                    }
        });

        apiService.obtenerRutinasCreador(correo).enqueue(new Callback<List<RutinasEntity>>() {
            @Override
            public void onResponse(Call<List<RutinasEntity>> call, Response<List<RutinasEntity>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    listaRutinas.clear();
                    listaRutinas.addAll(response.body());
                    adaptador.notifyDataSetChanged();
                } else if (response.code() == 409) {
                    listaRutinas.add(new RutinasEntity("No hay rutinas disponibles aún"));
                    Log.e("RetrofitGET", "Error en la respuesta: " + response.code());
                }
            }

            @Override
            public void onFailure(Call<List<RutinasEntity>> call, Throwable t) {
                Log.e("RetrofitGET", "Fallo en la conexión: " + t.getMessage());

            }
        });
    }
    /**
     *
     * @param item The menu item that was selected.
     *
     * @return item
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}