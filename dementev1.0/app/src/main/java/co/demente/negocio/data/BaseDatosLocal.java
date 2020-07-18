package co.demente.negocio.data;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import co.demente.negocio.data.dao.TemaDao;
import co.demente.negocio.data.dao.UsuarioDao;
import co.demente.negocio.data.dao.UsuarioTemaProgresoDao;
import co.demente.negocio.data.model.Topic;
import co.demente.negocio.data.model.User;
import co.demente.negocio.data.model.TopicUserProgress;


@Database(entities = {User.class, Topic.class, TopicUserProgress.class}, version = 1)
public abstract class BaseDatosLocal extends RoomDatabase {

    public abstract UsuarioDao getUsuarioDao();

    public abstract TemaDao getTemaDao();

    public abstract UsuarioTemaProgresoDao getUsuarioTemaProgresoDao();
}
