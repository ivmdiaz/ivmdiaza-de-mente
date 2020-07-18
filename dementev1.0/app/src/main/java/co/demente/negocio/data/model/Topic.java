package co.demente.negocio.data.model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import java.io.Serializable;

@Entity(tableName = "Topic")
public class Topic implements Serializable {

    @PrimaryKey
    private Long id;

    @ColumnInfo(name = "titulo")
    private String titulo;

    @ColumnInfo(name = "asset_contenido")
    private String assetContenido;

    @ColumnInfo(name = "prioridad")
    private Integer prioridad;

    public Topic() {

    }

    public Topic(Long id, String titulo, String assetContenido, Integer prioridad) {
        this.id = id;
        this.titulo = titulo;
        this.assetContenido = assetContenido;
        this.prioridad = prioridad;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getAssetContenido() {
        return assetContenido;
    }

    public void setAssetContenido(String assetContenido) {
        this.assetContenido = assetContenido;
    }

    public Integer getPrioridad() {
        return prioridad;
    }

    public void setPrioridad(Integer prioridad) {
        this.prioridad = prioridad;
    }

}
