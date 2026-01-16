package com.example.pojos;

public class ContratosEntity {
    private Long id;
    private AtletasEntity atleta;
    private EntrenadoresEntity entrenador;

    public ContratosEntity(EntrenadoresEntity entrenador, AtletasEntity atleta) {
        this.entrenador = entrenador;
        this.atleta = atleta;
    }

    public AtletasEntity getAtleta() {
        return atleta;
    }

    public void setAtleta(AtletasEntity atleta) {
        this.atleta = atleta;
    }

    public EntrenadoresEntity getEntrenador() {
        return entrenador;
    }

    public void setEntrenador(EntrenadoresEntity entrenador) {
        this.entrenador = entrenador;
    }
}
