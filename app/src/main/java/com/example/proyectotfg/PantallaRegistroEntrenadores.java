package com.example.proyectotfg;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.cliente.ApiCliente;
import com.example.utils.CorreoUtils;
import com.example.utils.ContraPasswordUtils;
import com.example.pojos.EntrenadoresEntity;
import com.example.service.ApiService;
import com.example.utils.EntrenadoresUtils;

import java.time.LocalDate;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PantallaRegistroEntrenadores extends AppCompatActivity {
    private Toolbar toolbarPRE;
    private CheckBox isCheckPassE;
    private Button btnRegistrarE;
    private EditText inpNombre, inpApellidos, inpCorreo, inpPass1, inpPass2, inpEspecialidad, inpExperiencia;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_pantalla_registro_entrenador);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        toolbarPRE = findViewById(R.id.toolbarPRE);
        setSupportActionBar(toolbarPRE);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayShowTitleEnabled(false);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setHomeAsUpIndicator(R.drawable.baseline_arrow_back_24);
        }

        isCheckPassE = findViewById(R.id.isCheckPassE);
        btnRegistrarE = findViewById(R.id.btnRegistrarE);
        inpNombre = findViewById(R.id.inpNombreE);
        inpApellidos = findViewById(R.id.inpApellidosE);
        inpCorreo = findViewById(R.id.inpCorreoE);
        inpPass1 = findViewById(R.id.inpPass1E);
        inpPass2 = findViewById(R.id.inpPass2E);
        inpEspecialidad = findViewById(R.id.inpEspecialidad);
        inpExperiencia = findViewById(R.id.inpExperiencia);
        ApiService apiService = ApiCliente.getRetrofit().create(ApiService.class);

        btnRegistrarE.setOnClickListener(v ->{
            EntrenadoresEntity nuevoEntrenador = new EntrenadoresEntity(inpNombre.getText().toString(), inpApellidos.getText().toString(), inpCorreo.getText().toString(), inpPass1.getText().toString(),
                    LocalDate.now().toString(),inpEspecialidad.getText().toString(), inpExperiencia.getText().toString());

            if (EntrenadoresUtils.comprobarCamposVacios(inpNombre, inpApellidos, inpCorreo, inpPass1, inpPass2, inpEspecialidad, inpExperiencia)) {
                return;
            }

            if (ContraPasswordUtils.matchPassword(inpPass1, inpPass2)) {
                return;
            }

            apiService.guardarEntrenador(nuevoEntrenador).enqueue(new Callback<EntrenadoresEntity>() {
                @Override
                public void onResponse(Call<EntrenadoresEntity> call, Response<EntrenadoresEntity> response) {
                    if (response.isSuccessful()) {
                        Toast.makeText(PantallaRegistroEntrenadores.this, "Bienvenido a Street Fit", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(PantallaRegistroEntrenadores.this, PantallaEntrada.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);
                        finish();
                    } else if (response.code() == 409) {
                        CorreoUtils.comprobarIdentificacionCorreoExiste(response, inpCorreo);
                    }
                }

                @Override
                public void onFailure(Call<EntrenadoresEntity> call, Throwable t) {
                    Log.e("API", "Fallo en la llamada: " + t.getMessage(), t);
                    Toast.makeText(PantallaRegistroEntrenadores.this, "Fallo: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        });
        ContraPasswordUtils.verPasswordDoble(isCheckPassE, inpPass1, inpPass2);
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