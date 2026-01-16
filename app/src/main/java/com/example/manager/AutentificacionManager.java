package com.example.manager;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import com.example.cliente.ApiCliente;
import com.example.pojos.AtletasEntity;
import com.example.pojos.EntrenadoresEntity;
import com.example.proyectotfg.PantallaAtletas;
import com.example.proyectotfg.PantallaEntrenador;
import com.example.service.ApiService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AutentificacionManager {
    private final Context context;
    private final ApiService apiService;

    public AutentificacionManager(Context context) {
        this.context = context;
        this.apiService = ApiCliente.getRetrofit().create(ApiService.class);
    }

    /**
     *
     * @param correo
     * @param contraPassword
     */
    public void login(String correo, String contraPassword){
        AtletasEntity logAtleta = new AtletasEntity(correo, contraPassword);
        EntrenadoresEntity logEntrenador = new EntrenadoresEntity(correo, contraPassword);

        apiService.loginAtleta(logAtleta).enqueue(new Callback<AtletasEntity>() {
            @Override
            public void onResponse(Call<AtletasEntity> call, Response<AtletasEntity> response) {
                Log.d("Login", "Atleta response code: " + response.code() + ", body: " + response.body());
                if (response.isSuccessful() && response.body() != null) {
                    navegarPantallaAtleta(response.body());
                } else {
                    intentarLoginEntrenador(logEntrenador);
                }
            }
            @Override
            public void onFailure(Call<AtletasEntity> call, Throwable t) {
                intentarLoginEntrenador(logEntrenador);
            }
        });
    }

    /**
     *
     * @param logEntrenador
     */
    public void intentarLoginEntrenador(EntrenadoresEntity logEntrenador) {
        apiService.loginEntrenador(logEntrenador).enqueue(new Callback<EntrenadoresEntity>() {
            @Override
            public void onResponse(Call<EntrenadoresEntity> call, Response<EntrenadoresEntity> response) {
                Log.d("Login", "Entrenador response code: " + response.code() + ", body: " + response.body());
                if (response.isSuccessful() && response.body() != null) {
                    navegarPantallaEntrenador(response.body());
                } else if (response.code() == 409) {
                    Toast.makeText(context, "Correo o contraseña no válidos", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(context, "Error al iniciar sesión", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<EntrenadoresEntity> call, Throwable t) {
                Toast.makeText(context, "Fallo conexión " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    /**
     *
     * @param atleta
     */
    private void navegarPantallaAtleta(AtletasEntity atleta){
        Toast.makeText(context, "Bienvenido " + atleta.getNombre(), Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(context, PantallaAtletas.class);
        intent.putExtra("id", atleta.getId());
        intent.putExtra("nombre", atleta.getNombre());
        intent.putExtra("correo", atleta.getCorreo());
        context.startActivity(intent);
    }

    /**
     *
     * @param entrenador
     */
    private void navegarPantallaEntrenador(EntrenadoresEntity entrenador){
        Toast.makeText(context, "Bienvenido " + entrenador.getNombre(), Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(context, PantallaEntrenador.class);
        intent.putExtra("id", entrenador.getId());
        intent.putExtra("nombre", entrenador.getNombre());
        intent.putExtra("correo", entrenador.getCorreo());
        context.startActivity(intent);
    }
}
