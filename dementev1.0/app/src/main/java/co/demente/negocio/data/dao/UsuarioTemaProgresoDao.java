package co.demente.negocio.data.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import co.demente.negocio.data.model.TopicUserProgress;

@Dao
public interface UsuarioTemaProgresoDao {

    @Query("SELECT * FROM TopicUserProgress ORDER BY idTema ASC")
    List<TopicUserProgress> getAllUsuarioTemaProgreso();

    @Query("SELECT * FROM TopicUserProgress WHERE idUsuario = :idUsuario ORDER BY idTema ASC")
    List<TopicUserProgress> getUsuarioTemaProgresoByUsuario(Long idUsuario);

    @Query("SELECT * FROM TopicUserProgress WHERE idUsuario = :idUsuario AND idTema = :idTema LIMIT 1")
    TopicUserProgress getUsuarioTemaProgresoByTema(Long idUsuario, Long idTema);

    @Query("SELECT estado FROM TopicUserProgress WHERE idUsuario = :idUsuario ORDER BY idTema DESC LIMIT 1")
    String getEstadoUltimoTema(Long idUsuario);

    @Query("SELECT COUNT(id) FROM TopicUserProgress WHERE idUsuario = :idUsuario ")
    int getCount(Long idUsuario);

    @Insert
    void addContenidoTema(TopicUserProgress topicUserProgress);

    @Insert
    void addAllUsuarioTemaProgreso(List<TopicUserProgress> progresosUsuario);

    @Delete
    void deleteUsuarioTemaProgreso(TopicUserProgress topicUserProgress);

    @Update
    void updateUsuarioTemaProgreso(TopicUserProgress topicUserProgress);

}
