package co.demente.core.servicios.negocio;

import android.annotation.SuppressLint;
import android.content.Context;

import java.util.ArrayList;
import java.util.List;

import co.demente.data.model.Tema;
import co.demente.data.model.Usuario;
import co.demente.data.model.UsuarioTemaProgreso;
import co.demente.data.service.DatabaseService;

public class UsuarioService extends DatabaseService {

    @SuppressLint("StaticFieldLeak")
    private static UsuarioService instance;

    /**
     * -- ------------------------------------------------------------------------------
     * -- Configuración de base de datos instancia singleton por cada servicio
     * -- ------------------------------------------------------------------------------
     */
    private UsuarioService(Context context) {

        //Instanciamos instancia a base de datos.
        super(context);

    }

    public static UsuarioService getUsuarioService(Context context) {
        if (instance == null) {
            instance = new UsuarioService(context);
        }
        return instance;
    }

    /**
     * -- ------------------------------------------------------------------------------
     * -- Métodos de negocio y acceso a base de datos.
     * -- ------------------------------------------------------------------------------
     */

    public boolean usuariosIsEmpty() {
        Integer rows = getUsuarioDao().getNumberOfRows();
        return rows == null || rows == 0;
    }

    public Usuario getFirstUsuario() {
        List<Usuario> usuarios = getUsuarioDao().getAllUsuarios();
        if (usuarios == null || usuarios.isEmpty()) {
            return null;
        } else {
            return usuarios.get(0);
        }
    }

    public Usuario registrarUsuarioPrimeraVez(Usuario usuario) {

        //Primero agregamos usuario
        Long idInsertado = getUsuarioDao().addUsuario(usuario);
        if (idInsertado != null && idInsertado != 0) {
            usuario.setId(idInsertado);

            //Crear progreso del usuario
            List<Tema> temas = getTemaDao().getAllTemas();
            List<UsuarioTemaProgreso> progresoUsuario = new ArrayList<UsuarioTemaProgreso>();
            for (int i = 0; i < temas.size(); i++) {

                Tema tema = temas.get(i);
                UsuarioTemaProgreso progreso = new UsuarioTemaProgreso();
                progreso.setIdTema(tema.getId());
                progreso.setIdUsuario(idInsertado);

                if (i == 0) {
                    progreso.setEstado(UsuarioTemaProgreso.ESTADO_PROGRESO);
                } else {
                    progreso.setEstado(UsuarioTemaProgreso.ESTADO_NOINICIADO);
                }
                progresoUsuario.add(progreso);
            }
            getUsuarioTemaProgresoDao().addAllUsuarioTemaProgreso(progresoUsuario);
            return usuario;
        }

        return null;
    }

    public List<UsuarioTemaProgreso> obtenerProgresoUsuario(Usuario usuario) {
        List<UsuarioTemaProgreso> lstProgreso;
        lstProgreso = getUsuarioTemaProgresoDao().getUsuarioTemaProgresoByUsuario(usuario.getId());
        for (UsuarioTemaProgreso progreso : lstProgreso) {
            progreso.setRefTema(getTemaDao().getTemaById(progreso.getIdTema()));
        }
        return lstProgreso;
    }

    public UsuarioTemaProgreso obtenerProgresoByTema(Usuario usuario, Long idTema) {
        UsuarioTemaProgreso progreso = getUsuarioTemaProgresoDao().
                getUsuarioTemaProgresoByTema(usuario.getId(), idTema);
        progreso.setRefTema(getTemaDao().getTemaById(progreso.getIdTema()));
        return progreso;
    }

    public String obtenerEstadoUltimoTema(Usuario usuario) {
        return getUsuarioTemaProgresoDao().getEstadoUltimoTema(usuario.getId());
    }

    public void actualizarProgreso(UsuarioTemaProgreso progreso, UsuarioTemaProgreso... more) {

        getUsuarioTemaProgresoDao().updateUsuarioTemaProgreso(progreso);

        if (more != null && more.length > 0) {
            for (UsuarioTemaProgreso lProgreso : more) {
                getUsuarioTemaProgresoDao().updateUsuarioTemaProgreso(lProgreso);
            }
        }
    }
}
