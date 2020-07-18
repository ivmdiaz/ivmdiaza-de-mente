package co.demente.negocio.data.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import co.demente.negocio.data.model.User;

@Dao
public interface UsuarioDao {

    @Query("SELECT * FROM User")
    List<User> getAllUsuarios();

    @Query("SELECT * FROM User WHERE id = :id")
    User getUsuarioById(Long id);

    @Query("SELECT COUNT(id) FROM User")
    Integer getNumberOfRows();

    @Insert
    long addUsuario(User user);

    @Delete
    void deleteUsuario(User user);

    @Update
    void updateUsuario(User user);
}
