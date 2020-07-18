package co.demente.data.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import co.demente.data.model.UsuarioTemaProgreso;

@Dao
public interface UsuarioTemaProgresoDao {

    @Query("SELECT * FROM UsuarioTemaProgreso ORDER BY idTema ASC")
    List<UsuarioTemaProgreso> getAllUsuarioTemaProgreso();

    @Query("SELECT * FROM UsuarioTemaProgreso WHERE idUsuario = :idUsuario ORDER BY idTema ASC")
    List<UsuarioTemaProgreso> getUsuarioTemaProgresoByUsuario(Long idUsuario);

    @Query("SELECT * FROM UsuarioTemaProgreso WHERE idUsuario = :idUsuario AND idTema = :idTema LIMIT 1")
    UsuarioTemaProgreso getUsuarioTemaProgresoByTema(Long idUsuario, Long idTema);

    @Query("SELECT estado FROM UsuarioTemaProgreso WHERE idUsuario = :idUsuario ORDER BY idTema DESC LIMIT 1")
    String getEstadoUltimoTema(Long idUsuario);

    @Insert
    void addContenidoTema(UsuarioTemaProgreso usuarioTemaProgreso);

    @Insert
    void addAllUsuarioTemaProgreso(List<UsuarioTemaProgreso> progresosUsuario);

    @Delete
    void deleteUsuarioTemaProgreso(UsuarioTemaProgreso usuarioTemaProgreso);

    @Update
    void updateUsuarioTemaProgreso(UsuarioTemaProgreso usuarioTemaProgreso);

}
