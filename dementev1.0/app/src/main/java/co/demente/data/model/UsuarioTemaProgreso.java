package co.demente.data.model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import co.demente.data.converter.DateConverter;

@Entity(tableName = "UsuarioTemaProgreso",
        foreignKeys = {
                @ForeignKey(entity = Usuario.class, parentColumns = "id", childColumns = "idUsuario", onDelete = ForeignKey.CASCADE),
                @ForeignKey(entity = Tema.class, parentColumns = "id", childColumns = "idTema", onDelete = ForeignKey.CASCADE)
        }
)
@TypeConverters(DateConverter.class)
public class UsuarioTemaProgreso implements Serializable {

    /**
     * Variables persistidas
     */

    @PrimaryKey(autoGenerate = true)
    private Long id;

    @ColumnInfo(name = "idUsuario")
    private Long idUsuario;

    @ColumnInfo(name = "idTema")
    private Long idTema;

    @ColumnInfo(name = "estado")
    private String estado;

    @ColumnInfo(name = "fecha")
    private Date fecha;


    @Ignore
    public static final String ESTADO_COMPLETO = "COMPLETO";
    @Ignore
    public static final String ESTADO_PROGRESO = "EN_PROGRESO";
    @Ignore
    public static final String ESTADO_NOINICIADO = " NO_INICIADO";
    @Ignore
    private Tema refTema;

    public UsuarioTemaProgreso() {

    }

    public UsuarioTemaProgreso(Long idUsuario, Long idTema, String estado) {
        this.idUsuario = idUsuario;
        this.idTema = idTema;
        this.estado = estado;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(Long idUsuario) {
        this.idUsuario = idUsuario;
    }

    public Long getIdTema() {
        return idTema;
    }

    public void setIdTema(Long idTema) {
        this.idTema = idTema;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    /**
     * Métodos no persistentes
     */

    public Tema getRefTema() {
        return refTema;
    }

    public void setRefTema(Tema refTema) {
        this.refTema = refTema;
    }
}
