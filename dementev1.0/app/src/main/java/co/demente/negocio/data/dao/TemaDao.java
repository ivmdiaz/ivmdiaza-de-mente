package co.demente.negocio.data.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import co.demente.negocio.data.model.Topic;

@Dao
public interface TemaDao {

    @Query("SELECT COUNT(id) FROM Topic")
    Integer getNumberOfRows();

    @Query("SELECT * FROM Topic ORDER BY prioridad ASC")
    List<Topic> getAllTemas();

    @Query("SELECT * FROM Topic WHERE id = :id")
    Topic getTemaById(Long id);

    @Insert
    void addTema(Topic topic);

    @Insert
    void addAllemas(List<Topic> topics);

    @Delete
    void deleteTema(Topic topic);

    @Update
    void updateTema(Topic topic);


}
