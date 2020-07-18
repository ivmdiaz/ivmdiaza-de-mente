package co.demente.util;

import java.util.HashMap;
import java.util.Map;

public class Constantes {

    //LLAVES ENTRE SESIÓN
    public static enum PANTALLAS {SPLASH, MAIN, LOGIN, CONTENIDO1, CONTENIDO2 };
    public static final String CALLBACK_KEY = "CALLBACK_KEY";
    public static final String USUARIO_KEY = "USUARIO_KEY";
    public static final String TEMA_ACTUAL_KEY = "TEMA_ACTUAL_KEY";
    public static final String TEMA_SIGUEN_KEY = "TEMA_SIGUEN_KEY";

    //DIALOGOS

    public static final String LOGIN_DIALOGO001 = "HOLA, soy Demente y quisiera saber; " +
            "¿Cómo estás afrontando la pandemia?; pero primero quiero conocerte un poco más.";

    public static final String MAIN_DIALOGO001 = "HOLA; estos son los cinco temas que vamos a " +
            "desarrollar juntos.";
    public static final String MAIN_DIALOGO002 = "Felicidades; has acabado los cinco temas.";


    public static final String EVAL_DIALOGO001 = "Consejos para aplicar en tu prueba de opción " +
            "múltiple. En la cartilla número uno que recibimos, llenarás una serie de preguntas " +
            "relacionado al tema que acabaste de leer.";

    //UTILES
    public static final String ASSET_PATH = "file:///android_asset/%s";

    //ERRORES


}
