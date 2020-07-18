package co.demente.data;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import co.demente.data.dao.TemaDao;
import co.demente.data.dao.UsuarioDao;
import co.demente.data.dao.UsuarioTemaProgresoDao;
import co.demente.data.model.Tema;
import co.demente.data.model.Usuario;
import co.demente.data.model.UsuarioTemaProgreso;


@Database(entities = {Usuario.class, Tema.class, UsuarioTemaProgreso.class}, version = 1)
public abstract class BaseDatosLocal extends RoomDatabase {

    public abstract UsuarioDao getUsuarioDao();

    public abstract TemaDao getTemaDao();

    public abstract UsuarioTemaProgresoDao getUsuarioTemaProgresoDao();
}
