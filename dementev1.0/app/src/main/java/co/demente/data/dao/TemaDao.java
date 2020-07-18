package co.demente.data.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import co.demente.data.model.Tema;

@Dao
public interface TemaDao {

    @Query("SELECT COUNT(id) FROM Tema")
    Integer getNumberOfRows();

    @Query("SELECT * FROM Tema ORDER BY prioridad ASC")
    List<Tema> getAllTemas();

    @Query("SELECT * FROM Tema WHERE id = :id")
    Tema getTemaById(Long id);

    @Insert
    void addTema(Tema tema);

    @Insert
    void addAllemas(List<Tema> temas);

    @Delete
    void deleteTema(Tema tema);

    @Update
    void updateTema(Tema tema);


}
