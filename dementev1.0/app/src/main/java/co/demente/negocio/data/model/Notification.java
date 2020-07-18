package co.demente.negocio.data.model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "Notification")
public class Notification {

    @PrimaryKey(autoGenerate = true)
    private Long id;

    @ColumnInfo(name = "titulo")
    private String titulo;

    @ColumnInfo(name = "textoCorto")
    private String textoCorto;

    @ColumnInfo(name = "imagen")
    private String imagen;

    @ColumnInfo(name = "estado")
    private String estado;

    @ColumnInfo(name = "lunes")
    private String lunes;

    @ColumnInfo(name = "martes")
    private String martes;

    @ColumnInfo(name = "miercoles")
    private String miercoles;

    @ColumnInfo(name = "jueves")
    private String jueves;

    @ColumnInfo(name = "viernes")
    private String viernes;

    @ColumnInfo(name = "sabado")
    private String sabado;

    @ColumnInfo(name = "domingo")
    private String domingo;

    @ColumnInfo(name = "horaInicio")
    private Integer horaInicio;

    @ColumnInfo(name = "horaFinal")
    private Integer horaFinal;

    @ColumnInfo(name = "repetir")
    private Long repetir;

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

    public String getTextoCorto() {
        return textoCorto;
    }

    public void setTextoCorto(String textoCorto) {
        this.textoCorto = textoCorto;
    }

    public String getImagen() {
        return imagen;
    }

    public void setImagen(String imagen) {
        this.imagen = imagen;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getLunes() {
        return lunes;
    }

    public void setLunes(String lunes) {
        this.lunes = lunes;
    }

    public String getMartes() {
        return martes;
    }

    public void setMartes(String martes) {
        this.martes = martes;
    }

    public String getMiercoles() {
        return miercoles;
    }

    public void setMiercoles(String miercoles) {
        this.miercoles = miercoles;
    }

    public String getJueves() {
        return jueves;
    }

    public void setJueves(String jueves) {
        this.jueves = jueves;
    }

    public String getViernes() {
        return viernes;
    }

    public void setViernes(String viernes) {
        this.viernes = viernes;
    }

    public String getSabado() {
        return sabado;
    }

    public void setSabado(String sabado) {
        this.sabado = sabado;
    }

    public String getDomingo() {
        return domingo;
    }

    public void setDomingo(String domingo) {
        this.domingo = domingo;
    }

    public Integer getHoraInicio() {
        return horaInicio;
    }

    public void setHoraInicio(Integer horaInicio) {
        this.horaInicio = horaInicio;
    }

    public Integer getHoraFinal() {
        return horaFinal;
    }

    public void setHoraFinal(Integer horaFinal) {
        this.horaFinal = horaFinal;
    }

    public Long getRepetir() {
        return repetir;
    }

    public void setRepetir(Long repetir) {
        this.repetir = repetir;
    }
}
