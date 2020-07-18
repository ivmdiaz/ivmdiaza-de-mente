package co.demente.data.service;

import android.annotation.SuppressLint;
import android.content.Context;

import androidx.room.Room;

import co.demente.data.BaseDatosLocal;
import co.demente.data.dao.TemaDao;
import co.demente.data.dao.UsuarioDao;
import co.demente.data.dao.UsuarioTemaProgresoDao;

/**
 * Servicio Singleton. para manejar todas las operaciones de negocio del usuario
 * TODO: Mover a un paquete de negocio, esta clase será la conexión entre BD y la vista.
 *
 * @author idiaz
 */
public class DatabaseService {

    @SuppressLint("StaticFieldLeak")
    private static DatabaseService instance;

    private UsuarioDao usuarioDao;

    private TemaDao temaDao;

    private UsuarioTemaProgresoDao usuarioTemaProgresoDao;


    /**
     * -- ------------------------------------------------------------------------------
     * -- Configuración de base de datos instancia singleton del servicio
     * -- ------------------------------------------------------------------------------
     */


    /**
     * Función encargada de crear una nueva instancia de base de datos para la tabla Usuario.
     *
     * @param context contexto de la aplicación
     */
    protected DatabaseService(Context context) {

        //Obtenemos instancia singleton de la aplicación.
        Context appContext = context.getApplicationContext();

        //Construimos la base de datos
        BaseDatosLocal database = Room
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
     * Función encargada de crear instancia singleton en la vida de la aplicación
     *
     * @param context contexto de la aplicación
     * @return instancia de los servicios correspondientes al usuario
     */
    public static DatabaseService get(Context context) {
        if (instance == null) {
            instance = new DatabaseService(context);
        }
        return instance;
    }

    /**
     * -- ------------------------------------------------------------------------------
     * -- Métodos de negocio y acceso a base de datos.
     * -- ------------------------------------------------------------------------------
     */

    public static DatabaseService getInstance() {
        return instance;
    }

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
