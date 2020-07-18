package co.demente.negocio.data.service;

import android.annotation.SuppressLint;
import android.content.Context;

import androidx.room.Room;

import co.demente.common.Shared;
import co.demente.negocio.data.BaseDatosLocal;
import co.demente.negocio.data.dao.TemaDao;
import co.demente.negocio.data.dao.UsuarioDao;
import co.demente.negocio.data.dao.UsuarioTemaProgresoDao;

/**
 * Servicio Singleton. para manejar todas las operaciones de negocio del usuario
 *
 * @author idiaz
 */
public class DatabaseService {

    @SuppressLint("StaticFieldLeak")
    private static DatabaseService instance;

    private co.demente.negocio.data.dao.UsuarioDao usuarioDao;

    private co.demente.negocio.data.dao.TemaDao temaDao;

    private co.demente.negocio.data.dao.UsuarioTemaProgresoDao usuarioTemaProgresoDao;


    /**
     * -- ------------------------------------------------------------------------------
     * -- Configuración de base de datos instancia singleton del servicio
     * -- ------------------------------------------------------------------------------
     */


    /**
     * Función encargada de crear una nueva instancia de base de datos para la tabla Usuario.
     *
     */
    protected DatabaseService() {

        //Obtenemos instancia singleton de la aplicación.
        Context appContext = Shared.context;

        //Construimos la base de datos
        co.demente.negocio.data.BaseDatosLocal database = Room
                .databaseBuilder(appContext, BaseDatosLocal.class, "BaseDatosLocal")
                .allowMainThreadQueries().build();

        //Obtenemos DAO del usuario.
        this.usuarioDao = database.getUsuarioDao();

        //Obtenemos DAO del Tema
        this.temaDao = database.getTemaDao();

        //Obtenemos DAO del usuarioTemaProgresoDao
        this.usuarioTemaProgresoDao = database.getUsuarioTemaProgresoDao();
    }

    /**
     * -- ------------------------------------------------------------------------------
     * -- Métodos de negocio y acceso a base de datos.
     * -- ------------------------------------------------------------------------------
     */

    protected UsuarioDao getUsuarioDao() {
        return usuarioDao;
    }

    protected TemaDao getTemaDao() {
        return temaDao;
    }

    protected UsuarioTemaProgresoDao getUsuarioTemaProgresoDao() {
        return usuarioTemaProgresoDao;
    }
}
