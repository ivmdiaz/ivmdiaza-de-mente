package co.demente.data.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import co.demente.data.model.Usuario;

@Dao
public interface UsuarioDao {

    @Query("SELECT * FROM Usuario")
    List<Usuario> getAllUsuarios();

    @Query("SELECT * FROM Usuario WHERE id = :id")
    Usuario getUsuarioById(Long id);

    @Query("SELECT COUNT(id) FROM Usuario")
    Integer getNumberOfRows();

    @Insert
    long addUsuario(Usuario usuario);

    @Delete
    void deleteUsuario(Usuario usuario);

    @Update
    void updateUsuario(Usuario usuario);
}
