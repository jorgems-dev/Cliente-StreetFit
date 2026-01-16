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
import com.example.utils.AtletasUtils;
import com.example.utils.ContraPasswordUtils;
import com.example.pojos.AtletasEntity;
import com.example.service.ApiService;
import com.example.utils.CorreoUtils;

import java.time.LocalDate;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PantallaRegistroAtletas extends AppCompatActivity {
    private Toolbar toolbarPRA;
    private CheckBox isCheckPassA;
    private EditText inpNombre, inpApellidos, inpCorreo, inpPass1, inpPass2;
    private Button btnRegitroA;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_pantalla_registro_atleta);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        isCheckPassA = findViewById(R.id.isCheckPassA);
        inpNombre = findViewById(R.id.inpNombreE);
        inpApellidos = findViewById(R.id.inpApellidosE);
        inpCorreo = findViewById(R.id.inpCorreoE);
        inpPass1 = findViewById(R.id.inpPass1E);
        inpPass2 = findViewById(R.id.inpPass2E);
        btnRegitroA = findViewById(R.id.btnRegistrarA);

        toolbarPRA = findViewById(R.id.toolbarPRA);
        setSupportActionBar(toolbarPRA);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayShowTitleEnabled(false);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setHomeAsUpIndicator(R.drawable.baseline_arrow_back_24);
        }


        btnRegitroA.setOnClickListener(v ->{
            ApiService apiService = ApiCliente.getRetrofit().create(ApiService.class);
            AtletasEntity nuevoAtleta = new AtletasEntity(inpNombre.getText().toString(),
                    inpApellidos.getText().toString(),
                    inpCorreo.getText().toString(),
                    inpPass1.getText().toString(),
                    LocalDate.now().toString());

            if (AtletasUtils.comprobarCamposVacios(inpNombre, inpApellidos, inpCorreo, inpPass1, inpPass2)) {
                return;
            }

            if (ContraPasswordUtils.matchPassword(inpPass1, inpPass2)) {
                return;
            }

            apiService.guardarAtleta(nuevoAtleta).enqueue(new Callback<AtletasEntity>() {
                @Override
                public void onResponse(Call<AtletasEntity> call, Response<AtletasEntity> response) {
                    if (response.isSuccessful()) {
                        Toast.makeText(PantallaRegistroAtletas.this, "Bienvenido a Street Fit", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(PantallaRegistroAtletas.this, PantallaEntrada.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);
                        finish();

                    } else if (response.code() == 409) {
                        CorreoUtils.comprobarIdentificacionCorreoExiste(response, inpCorreo);
                    }
                }

                @Override
                public void onFailure(Call<AtletasEntity> call, Throwable t) {
                    Log.e("API", "Fallo en la llamada: " + t.getMessage(), t);
                    Toast.makeText(PantallaRegistroAtletas.this, "Fallo: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        });
        ContraPasswordUtils.verPasswordDoble(isCheckPassA, inpPass1, inpPass2);

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