package com.example.proyectotfg;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.cliente.ApiCliente;
import com.example.pojos.RutinasEntity;
import com.example.service.ApiService;
import com.example.utils.RutinasUtils;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PantallaDetallesRutina extends AppCompatActivity {
    private Toolbar toolbarDetallesRutinas;
    private EditText inpDetalleNombreEjercicio, inpDetalleGrupoMuscular, inpDetalleSeriesEjercicio, inpDetalleRepeticionesEjercicio, inpDetallePesoEjercicio;
    private Button btnGuardarCambios, btnBorrarRutina;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_pantalla_detalles_rutina);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        inpDetalleNombreEjercicio = findViewById(R.id.inpDetalleNombreEjercicio);
        inpDetalleGrupoMuscular = findViewById(R.id.inpDetalleGrupoMusuclar);
        inpDetalleSeriesEjercicio = findViewById(R.id.inpDetalleSeriesEjercicio);
        inpDetalleRepeticionesEjercicio = findViewById(R.id.inpDetalleRepeticionesEjercicio);
        inpDetallePesoEjercicio = findViewById(R.id.inpDetallePesoEjercicio);
        btnGuardarCambios = findViewById(R.id.btnGuardarCambios);
        btnBorrarRutina = findViewById(R.id.btnBorrarRutina);

        toolbarDetallesRutinas = findViewById(R.id.toolbarDetallesRutinas);
        setSupportActionBar(toolbarDetallesRutinas);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayShowTitleEnabled(false);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setHomeAsUpIndicator(R.drawable.baseline_arrow_back_24);
        }

        long idRutina = getIntent().getLongExtra("id", -1);

        if (idRutina == -1) {
            Toast.makeText(this, "No se pudo cargar la rutina", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }
        ApiService apiService = ApiCliente.getRetrofit().create(ApiService.class);

        apiService.obtenerRutinasPorId(idRutina).enqueue(new Callback<RutinasEntity>() {
            @Override
            public void onResponse(Call<RutinasEntity> call, Response<RutinasEntity> response) {
                if (response.isSuccessful() && response.body() != null) {
                    RutinasEntity rutina = response.body();

                    inpDetalleNombreEjercicio.setText(rutina.getNombre());
                    inpDetalleGrupoMuscular.setText(rutina.getGrupoMuscular());
                    inpDetalleSeriesEjercicio.setText(String.valueOf(rutina.getSeries()));
                    inpDetalleRepeticionesEjercicio.setText(String.valueOf(rutina.getRepeticiones()));
                    inpDetallePesoEjercicio.setText(String.valueOf(rutina.getPeso()));
                } else if (response.code() == 409) {
                    Toast.makeText(PantallaDetallesRutina.this, "No se pudo cargar la rutina", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<RutinasEntity> call, Throwable t) {
                Log.e("RetrofitGET", "Fallo en la conexión: " + t.getMessage());

            }
        });
        btnGuardarCambios.setOnClickListener(v -> {
            if (RutinasUtils.comprobarCamposEjercicioVacios(inpDetalleNombreEjercicio, inpDetalleGrupoMuscular, inpDetalleSeriesEjercicio, inpDetalleRepeticionesEjercicio, inpDetallePesoEjercicio)){
                return;
            }
            apiService.actulizarRutina(idRutina, new RutinasEntity(inpDetalleNombreEjercicio.getText().toString(),
                    inpDetalleGrupoMuscular.getText().toString(),
                    Integer.parseInt(inpDetalleSeriesEjercicio.getText().toString()),
                    Integer.parseInt(inpDetalleRepeticionesEjercicio.getText().toString()),
                    Float.parseFloat(inpDetallePesoEjercicio.getText().toString()))).enqueue(new Callback<RutinasEntity>() {
                @Override
                public void onResponse(Call<RutinasEntity> call, Response<RutinasEntity> response) {
                    if (response.isSuccessful() && response.body() != null) {
                        Intent intent = new Intent();
                        intent.putExtra("rutinaActualizada", response.body());
                        setResult(RESULT_OK, intent);
                        Toast.makeText(PantallaDetallesRutina.this, "Rutina actualizada correctamente", Toast.LENGTH_SHORT).show();
                        finish();
                    } else {
                        RutinasUtils.comprobarIdentificacionNombreExiste(response, inpDetalleNombreEjercicio);
                    }
                }
                @Override
                public void onFailure(Call<RutinasEntity> call, Throwable t) {
                    Log.e("RetrofitGET", "Fallo en la conexión: " + t.getMessage());
                }
            });
        });

        btnBorrarRutina.setOnClickListener(v ->{
            AlertDialog dialogo = new AlertDialog.Builder(this)
                    .setTitle("Eliminar rutina")
                    .setMessage("¿Estás seguro que quieres eliminar esta rutina?")
                    .setPositiveButton("Sí", (dialog, which) -> {
                        apiService.eliminarRutina(idRutina).enqueue(new Callback<Void>() {
                            @Override
                            public void onResponse(Call<Void> call, Response<Void> response) {
                                if (response.isSuccessful()) {
                                    Toast.makeText(PantallaDetallesRutina.this, "Rutina eliminada", Toast.LENGTH_SHORT).show();
                                    setResult(RESULT_OK, new Intent().putExtra("rutinaEliminadaId", idRutina));

                                    finish();
                                } else {
                                    Toast.makeText(PantallaDetallesRutina.this, "No se pudo eliminar la rutina", Toast.LENGTH_SHORT).show();
                                }
                            }

                            @Override
                            public void onFailure(Call<Void> call, Throwable t) {
                                Toast.makeText(PantallaDetallesRutina.this, "Error en la conexión", Toast.LENGTH_SHORT).show();
                            }
                        });
                    })
                    .setNegativeButton("No", null)
                    .show();
            dialogo.getButton(androidx.appcompat.app.AlertDialog.BUTTON_POSITIVE).setTextColor(Color.BLACK);
            dialogo.getButton(androidx.appcompat.app.AlertDialog.BUTTON_NEGATIVE).setTextColor(Color.BLACK);
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