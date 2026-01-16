package com.example.pojos;


import java.io.Serializable;

public class RutinasEntity implements Serializable {
    private Long id;
    private String nombre;
    private String grupoMuscular;
    private Integer series;
    private Integer repeticiones;
    private Float peso;
    private String identificacionCreador;
    private String fechaCreacion;

    public RutinasEntity(String nombre, String grupoMuscular, Integer series, Integer repeticiones, Float peso, String identificacionCreador, String fechaCreacion) {
        this.nombre = nombre;
        this.grupoMuscular = grupoMuscular;
        this.series = series;
        this.repeticiones = repeticiones;
        this.peso = peso;
        this.identificacionCreador = identificacionCreador;
        this.fechaCreacion = fechaCreacion;
    }

    public RutinasEntity(String nombre, String grupoMuscular, Integer series, Integer repeticiones, Float peso) {
        this.nombre = nombre;
        this.grupoMuscular = grupoMuscular;
        this.series = series;
        this.repeticiones = repeticiones;
        this.peso = peso;
    }

    public RutinasEntity(String nombre) {
        this.nombre = nombre;

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getGrupoMuscular() {
        return grupoMuscular;
    }

    public void setGrupoMuscular(String grupoMuscular) {
        this.grupoMuscular = grupoMuscular;
    }

    public Integer getSeries() {
        return series;
    }

    public void setSeries(Integer series) {
        this.series = series;
    }

    public Float getPeso() {
        return peso;
    }

    public void setPeso(Float peso) {
        this.peso = peso;
    }

    public Integer getRepeticiones() {
        return repeticiones;
    }

    public void setRepeticiones(Integer repeticiones) {
        this.repeticiones = repeticiones;
    }

    public String getIdentificacionCreador() {
        return identificacionCreador;
    }

    public void setIdentificacionCreador(String identificacionCreador) {
        this.identificacionCreador = identificacionCreador;
    }

    public String getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(String fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }
}
