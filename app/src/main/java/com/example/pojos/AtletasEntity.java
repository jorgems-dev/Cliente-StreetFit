package com.example.pojos;

import com.google.gson.annotations.SerializedName;

public class AtletasEntity {
    private Long id;
    private String nombre;
    private String apellidos;

    @SerializedName("correo")
    private String correo;

    @SerializedName("contraPassword")
    private String contraPassword;
    private String fechaAlta;

    /**
     * Constructor general
     * @param nombre
     * @param apellidos
     * @param correo
     * @param contraPassword
     * @param fechaAlta
     */
    public AtletasEntity(String nombre, String apellidos, String correo, String contraPassword, String fechaAlta) {
        this.nombre = nombre;
        this.apellidos = apellidos;
        this.correo = correo;
        this.contraPassword = contraPassword;
        this.fechaAlta = fechaAlta;
    }

    public AtletasEntity(Long id) {
        this.id = id;
    }

    public AtletasEntity(String nombre) {
        this.nombre = nombre;
    }

    /**
     * Contructor para login
     * @param correo
     * @param contraPassword
     */
    public AtletasEntity(String correo, String contraPassword) {
        this.correo = correo;
        this.contraPassword = contraPassword;
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

    public String getApellidos() {
        return apellidos;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getContraPassword() {
        return contraPassword;
    }

    public void setContraPassword(String contraPassword) {
        this.contraPassword = contraPassword;
    }

    public String getFechaAlta() {
        return fechaAlta;
    }

    public void setFechaAlta(String fechaAlta) {
        this.fechaAlta = fechaAlta;
    }
}
