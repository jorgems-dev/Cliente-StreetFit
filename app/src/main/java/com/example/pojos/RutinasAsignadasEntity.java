package com.example.pojos;

public class RutinasAsignadasEntity {
    private Long id;
    private AtletasEntity atleta;
    private EntrenadoresEntity entrenador;
    private RutinasEntity rutinas;

    public RutinasAsignadasEntity(AtletasEntity atleta, EntrenadoresEntity entrenador, RutinasEntity rutinas) {
        this.atleta = atleta;
        this.entrenador = entrenador;
        this.rutinas = rutinas;
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

    public RutinasEntity getRutinas() {
        return rutinas;
    }

    public void setRutinas(RutinasEntity rutinas) {
        this.rutinas = rutinas;
    }
}
