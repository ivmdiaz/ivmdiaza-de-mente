package co.demente.negocio.services;

import android.annotation.SuppressLint;

import java.util.ArrayList;
import java.util.List;

import co.demente.negocio.data.model.Topic;
import co.demente.negocio.data.model.TopicUserProgress;
import co.demente.negocio.data.model.User;
import co.demente.negocio.data.service.DatabaseService;


public class UsuarioService extends DatabaseService {

    @SuppressLint("StaticFieldLeak")
    private static UsuarioService instance;

    /**
     * -- ------------------------------------------------------------------------------
     * -- Configuración de base de datos instancia singleton por cada servicio
     * -- ------------------------------------------------------------------------------
     */
    private UsuarioService() {

        //Instanciamos instancia a base de datos.
        super();

    }

    public static UsuarioService getInstance() {
        if (instance == null) {
            instance = new UsuarioService();
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

    public User getFirstUsuario() {
        List<User> users = getUsuarioDao().getAllUsuarios();
        if (users == null || users.isEmpty()) {
            return null;
        } else {
            return users.get(0);
        }
    }

    public User registrarUsuarioPrimeraVez(User user) {

        //Primero agregamos usuario
        Long idInsertado = getUsuarioDao().addUsuario(user);
        if (idInsertado != null && idInsertado != 0) {
            user.setId(idInsertado);

            //Crear progreso del usuario
            List<Topic> topics = getTemaDao().getAllTemas();
            List<TopicUserProgress> progresoUsuario = new ArrayList<>();
            for (int i = 0; i < topics.size(); i++) {

                Topic topic = topics.get(i);
                TopicUserProgress progreso = new TopicUserProgress();
                progreso.setIdTema(topic.getId());
                progreso.setIdUsuario(idInsertado);

                if (i == 0) {
                    progreso.setEstado(TopicUserProgress.ESTADO_PROGRESO);
                } else {
                    progreso.setEstado(TopicUserProgress.ESTADO_NOINICIADO);
                }
                progresoUsuario.add(progreso);
            }
            getUsuarioTemaProgresoDao().addAllUsuarioTemaProgreso(progresoUsuario);
            return user;
        }

        return null;
    }


    public TopicUserProgress obtenerProgresoByTema(User user, Long idTema) {
        TopicUserProgress progreso = getUsuarioTemaProgresoDao().
                getUsuarioTemaProgresoByTema(user.getId(), idTema);
        progreso.setRefTopic(getTemaDao().getTemaById(progreso.getIdTema()));
        return progreso;
    }

    public String obtenerEstadoUltimoTema(User user) {
        return getUsuarioTemaProgresoDao().getEstadoUltimoTema(user.getId());
    }

    /**
     *
     * @param user
     * @param progress
     * @return if de topic is de last and no have been finished before.
     */
    public boolean advanceProgress(User user, TopicUserProgress progress){

        //The current progress no has been finished
        if(!TopicUserProgress.ESTADO_COMPLETO.equals(obtenerEstadoUltimoTema(user))){

            progress.setEstado(TopicUserProgress.ESTADO_COMPLETO);

            //Get Next topic to be work
            if(progress.getIdTema() != getUsuarioTemaProgresoDao().getCount(user.getId())){
                TopicUserProgress nextProgress = getUsuarioTemaProgresoDao().getUsuarioTemaProgresoByTema(
                        user.getId(), progress.getIdTema()+1);
                nextProgress.setEstado(TopicUserProgress.ESTADO_PROGRESO);

                //Update current progress
                getUsuarioTemaProgresoDao().updateUsuarioTemaProgreso(progress);

                //Update and prepare next progress
                getUsuarioTemaProgresoDao().updateUsuarioTemaProgreso(nextProgress);
            }
            else{
                //Update current progress
                getUsuarioTemaProgresoDao().updateUsuarioTemaProgreso(progress);
                return true;
            }
        }

        return false;
    }

    public void actualizarProgreso(TopicUserProgress progreso, TopicUserProgress... more) {
        getUsuarioTemaProgresoDao().updateUsuarioTemaProgreso(progreso);
        if (more != null && more.length > 0) {
            for (TopicUserProgress lProgreso : more) {
                getUsuarioTemaProgresoDao().updateUsuarioTemaProgreso(lProgreso);
            }
        }
    }
}
