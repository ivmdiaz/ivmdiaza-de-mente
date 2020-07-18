package co.demente.core.servicios.negocio;

import android.annotation.SuppressLint;
import android.content.Context;


import java.util.ArrayList;
import java.util.List;

import co.demente.data.model.Tema;
import co.demente.data.service.DatabaseService;

public class ConfAppService extends DatabaseService {


    //Identificadores públicos para usar en la aplicación
    public static final Long TEMA_1 = 1L;
    public static final Long TEMA_2 = 2L;
    public static final Long TEMA_3 = 3L;
    public static final Long TEMA_4 = 4L;
    public static final Long TEMA_5 = 5L;

    //Titulos de los temas
    private static final String TEMA_TITULO_1 = "Yo y los medios de comunicación";
    private static final String TEMA_TITULO_2 = "Mitos y creencias de la pandemia";
    private static final String TEMA_TITULO_3 = "Aprender a vivir en medio de la pandemia";
    private static final String TEMA_TITULO_4 = "Identificando mis territorios";
    private static final String TEMA_TITULO_5 = "De-Mente";

    //Recursos HTML con el contenido del tema (Debe dejar archivos en assets)
    private static final String TEMA_CONTENIDO_1 = "contenido_tema1.html";
    private static final String TEMA_CONTENIDO_2 = "contenido_tema1.html";
    private static final String TEMA_CONTENIDO_3 = "contenido_tema1.html";
    private static final String TEMA_CONTENIDO_4 = "contenido_tema1.html";
    private static final String TEMA_CONTENIDO_5 = "contenido_tema1.html";

    @SuppressLint("StaticFieldLeak")
    private static ConfAppService instance;

    /**
     * -- ------------------------------------------------------------------------------
     * -- Configuración de base de datos instancia singleton por cada servicio
     * -- ------------------------------------------------------------------------------
     */
    private ConfAppService(Context context) {

        //Instanciamos instancia a base de datos.
        super(context);

    }

    public static ConfAppService getConfAppService(Context context) {
        if (instance == null) {
            instance = new ConfAppService(context);
        }
        return instance;
    }

    /**
     * -- ------------------------------------------------------------------------------
     * -- Métodos de negocio y acceso a base de datos.
     * -- ------------------------------------------------------------------------------
     */

    private boolean temasIsEmpty() {
        Integer rows = getTemaDao().getNumberOfRows();
        return rows == null || rows == 0;
    }

    public void configurarAplicacion() {

        if (temasIsEmpty()) {

            //Agregamos temas:
            // -- ----------------------------------
            List<Tema> lstTemas = new ArrayList<Tema>();
            lstTemas.add(new Tema(TEMA_1, TEMA_TITULO_1, TEMA_CONTENIDO_1, 1));
            lstTemas.add(new Tema(TEMA_2, TEMA_TITULO_2, TEMA_CONTENIDO_2, 2));
            lstTemas.add(new Tema(TEMA_3, TEMA_TITULO_3, TEMA_CONTENIDO_3, 3));
            lstTemas.add(new Tema(TEMA_4, TEMA_TITULO_4, TEMA_CONTENIDO_4, 4));
            lstTemas.add(new Tema(TEMA_5, TEMA_TITULO_5, TEMA_CONTENIDO_5, 5));

            getTemaDao().addAllemas(lstTemas);

        }

        //Agregamos dialogos de texto x faces
        // -- ----------------------------------
    }
}
