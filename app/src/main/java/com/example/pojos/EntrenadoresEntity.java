package com.example.pojos;

public class EntrenadoresEntity {
    private Long id;
    private String nombre;
    private String apellidos;
    private String correo;
    private String contraPassword;
    private String fechaAlta;
    private String especialidad;
    private String experiencia;

    public EntrenadoresEntity(String nombre, String apellidos, String correo, String contraPassword, String fechaAlta, String especialidad, String experiencia) {
        this.nombre = nombre;
        this.apellidos = apellidos;
        this.correo = correo;
        this.contraPassword = contraPassword;
        this.fechaAlta = fechaAlta;
        this.especialidad = especialidad;
        this.experiencia = experiencia;
    }

    public EntrenadoresEntity(String correo, String contraPassword) {
        this.correo = correo;
        this.contraPassword = contraPassword;

    }

    public EntrenadoresEntity(String nombre) {
        this.nombre = nombre;

    }

    public Long getId() {return id;}

    public void setId(Long id) {this.id = id;}

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

    public void setFechaAlta(String fechaAlta) {this.fechaAlta = fechaAlta;}

    public String getEspecialidad() {
        return especialidad;
    }

    public void setEspecialidad(String especialidad) {
        this.especialidad = especialidad;
    }

    public String getExperiencia() {
        return experiencia;
    }

    public void setExperiencia(String experiencia) {
        this.experiencia = experiencia;
    }
}
