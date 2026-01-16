package com.example.service;

import com.example.pojos.AtletasEntity;
import com.example.pojos.ContratosEntity;
import com.example.pojos.EntrenadoresEntity;
import com.example.pojos.RutinasAsignadasEntity;
import com.example.pojos.RutinasEntity;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.Field;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ApiService {
    /* Atletas */
    @POST("/atletas/registro")
    Call<AtletasEntity> guardarAtleta(@Body AtletasEntity atleta);

    @POST("/atletas/login")
    Call<AtletasEntity> loginAtleta(@Body AtletasEntity atleta);

    @GET("/atletas/id/{id}")
    Call<AtletasEntity> obtenerAtletasPorId(@Path("id")Long id);

    @GET("/atletas/disponibles/{id}")
    Call<List<AtletasEntity>> obtenerListaAtletasDisponibles(@Path("id")Long id);

    /* Entrenadores */
    @POST("/entrenadores/registro")
    Call<EntrenadoresEntity> guardarEntrenador(@Body EntrenadoresEntity entrenador);

    @GET("/entrenadores")
    Call<List<EntrenadoresEntity>> listaEntrenadores();

    @POST("/entrenadores/login")
    Call<EntrenadoresEntity> loginEntrenador(@Body EntrenadoresEntity entrenador);

    @GET("/entrenadores/id/{id}")
    Call<EntrenadoresEntity> obtenerEntrenadorPorId(@Path("id")Long id);

    @GET("/entrenadores/contrataciones/{id}")
    Call<List<EntrenadoresEntity>>  obtenerListaEntrenadoresNoContratados(@Path("id")Long id);

    @GET("/entrenadores/contratados/{id}")
    Call<List<EntrenadoresEntity>>  obtenerListaEntrenadoresContratados(@Path("id")Long id);

    /* Contratos */
    @POST("/contratos")
    Call<ContratosEntity> realizarContratacion(@Body ContratosEntity contrato);

    @POST("/contratos/baja")
    Call<Void> realizarBajaContratacion(@Body ContratosEntity contrato);

    /* Rutinas */
    @POST("/rutinas/crear")
    Call<RutinasEntity> crearRutina(@Body RutinasEntity rutina);

    @GET("/rutinas/creador/{identificacionCreador}")
    Call<List<RutinasEntity>> obtenerRutinasCreador(@Path("identificacionCreador")String correo);

    @GET("/rutinas/{id}")
    Call<RutinasEntity> obtenerRutinasPorId(@Path("id")Long id);

    @PUT("/rutinas/detalles-editar/{id}")
    Call<RutinasEntity> actulizarRutina(@Path("id")Long id, @Body RutinasEntity rutina);

    @DELETE("/rutinas/detalles-editar/{id}")
    Call<Void> eliminarRutina(@Path("id") Long id);

    /* Rutinas asignadas */
    @POST("/rutinas-asignadas/asignar")
    Call<RutinasAsignadasEntity> asignarRutina(@Field("idEntrenador") Long idEntrenador,
                                               @Field("idAtleta") Long idAtleta,
                                               @Field("nombreRutina") String nombreRutina);
}
