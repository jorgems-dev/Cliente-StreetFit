package com.example.proyectotfg;

import android.annotation.SuppressLint;
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

import java.time.LocalDate;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PantallaCrearRutinas extends AppCompatActivity {
    private Toolbar toolbarEntrenadoresContratados;
    private EditText inpNombreEjercicio, inpGrupoMusuclar, inpSeriesEjercicio, inpRepeticionesEjercicio, inpPesoEjercicio;
    private Button btnCrearRutina;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_pantalla_crear_rutinas);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        inpNombreEjercicio = findViewById(R.id.inpNombreEjercicio);
        inpGrupoMusuclar = findViewById(R.id.inpGrupoMusuclar);
        inpSeriesEjercicio = findViewById(R.id.inpSeriesEjercicio);
        inpRepeticionesEjercicio = findViewById(R.id.inpRepeticionesEjercicio);
        inpPesoEjercicio = findViewById(R.id.inpPesoEjercicio);
        btnCrearRutina = findViewById(R.id.btnCrearRutina);

        toolbarEntrenadoresContratados = findViewById(R.id.toolbarCreacionRutinas);
        setSupportActionBar(toolbarEntrenadoresContratados);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayShowTitleEnabled(false);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setHomeAsUpIndicator(R.drawable.baseline_arrow_back_24);
        }

        ApiService apiService = ApiCliente.getRetrofit().create(ApiService.class);
        String correoCreador = getIntent().getStringExtra("correo");

        btnCrearRutina.setOnClickListener(v ->{
            if (RutinasUtils.comprobarCamposEjercicioVacios(inpNombreEjercicio, inpGrupoMusuclar, inpSeriesEjercicio, inpRepeticionesEjercicio, inpPesoEjercicio)) {
                return;
            }

            int series = Integer.parseInt(inpSeriesEjercicio.getText().toString());
            int repeticiones = Integer.parseInt(inpRepeticionesEjercicio.getText().toString());
            float peso = Float.parseFloat(inpPesoEjercicio.getText().toString());

            RutinasEntity nuevaRutina = new RutinasEntity(inpNombreEjercicio.getText().toString(), inpGrupoMusuclar.getText().toString(),
                    series,
                    repeticiones,
                    peso, correoCreador,
                    LocalDate.now().toString());
            apiService.crearRutina(nuevaRutina).enqueue(new Callback<RutinasEntity>() {
                @Override
                public void onResponse(Call<RutinasEntity> call, Response<RutinasEntity> response) {
                    if (response.isSuccessful() && response.body() != null) {
                        Toast.makeText(PantallaCrearRutinas.this, "Rutina creada con exito", Toast.LENGTH_SHORT).show();
                        finish();
                    } else  {
                        RutinasUtils.comprobarIdentificacionNombreExiste(response, inpNombreEjercicio);
                    }
                }

                @Override
                public void onFailure(Call<RutinasEntity> call, Throwable t) {
                    Log.e("API", "Fallo en la llamada: " + t.getMessage(), t);
                    Toast.makeText(PantallaCrearRutinas.this, "Fallo: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
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