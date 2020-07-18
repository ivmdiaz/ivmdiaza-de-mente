package co.demente;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import com.alphamovie.lib.AlphaMovieView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.speech.tts.TextToSpeech;
import android.view.View;

import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import co.demente.core.servicios.negocio.ConfAppService;
import co.demente.core.servicios.negocio.UsuarioService;
import co.demente.data.model.Usuario;
import co.demente.data.model.UsuarioTemaProgreso;
import co.demente.util.Constantes;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {


    private UsuarioService userService;

    private AlphaMovieView amvDialogo;
    private TextToSpeech tts;

    private Usuario usuario;
    private Constantes.PANTALLAS callback;
    private List<UsuarioTemaProgreso> progresos;

    private Map<String, Button> mapLv1, mapLv2, mapLv3, mapLv4, mapLv5;
    private TextView txtLv1, txtLv2, txtLv3, txtLv4, txtLv5;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Configuramos servicios
        instanceServices(this);

        //Obtenemos variables de sesión
        this.usuario = (Usuario) getIntent().getSerializableExtra(Constantes.USUARIO_KEY);
        callback = (Constantes.PANTALLAS) getIntent().getSerializableExtra(Constantes.CALLBACK_KEY);

        //Configuramos ventana de dialogo
        amvDialogo = findViewById(R.id.amvMain);

        //Configuramos vistas de la activiy
        mapLv1 = new HashMap<>();
        mapLv1.put(UsuarioTemaProgreso.ESTADO_COMPLETO, (Button) findViewById(R.id.bntLv1Completed));
        mapLv1.put(UsuarioTemaProgreso.ESTADO_NOINICIADO, (Button) findViewById(R.id.bntLv1Blocked));
        mapLv1.put(UsuarioTemaProgreso.ESTADO_PROGRESO, (Button) findViewById(R.id.bntLv1Current));

        mapLv2 = new HashMap<>();
        mapLv2.put(UsuarioTemaProgreso.ESTADO_COMPLETO, (Button) findViewById(R.id.bntLv2Completed));
        mapLv2.put(UsuarioTemaProgreso.ESTADO_NOINICIADO, (Button) findViewById(R.id.bntLv2Blocked));
        mapLv2.put(UsuarioTemaProgreso.ESTADO_PROGRESO, (Button) findViewById(R.id.bntLv2Current));

        mapLv3 = new HashMap<>();
        mapLv3.put(UsuarioTemaProgreso.ESTADO_COMPLETO, (Button) findViewById(R.id.bntLv3Completed));
        mapLv3.put(UsuarioTemaProgreso.ESTADO_NOINICIADO, (Button) findViewById(R.id.bntLv3Blocked));
        mapLv3.put(UsuarioTemaProgreso.ESTADO_PROGRESO, (Button) findViewById(R.id.bntLv3Current));

        mapLv4 = new HashMap<>();
        mapLv4.put(UsuarioTemaProgreso.ESTADO_COMPLETO, (Button) findViewById(R.id.bntLv4Completed));
        mapLv4.put(UsuarioTemaProgreso.ESTADO_NOINICIADO, (Button) findViewById(R.id.bntLv4Blocked));
        mapLv4.put(UsuarioTemaProgreso.ESTADO_PROGRESO, (Button) findViewById(R.id.bntLv4Current));

        mapLv5 = new HashMap<>();
        mapLv5.put(UsuarioTemaProgreso.ESTADO_COMPLETO, (Button) findViewById(R.id.bntLv5Completed));
        mapLv5.put(UsuarioTemaProgreso.ESTADO_NOINICIADO, (Button) findViewById(R.id.bntLv5Blocked));
        mapLv5.put(UsuarioTemaProgreso.ESTADO_PROGRESO, (Button) findViewById(R.id.bntLv5Current));

        txtLv1 = findViewById(R.id.txtLv1);
        txtLv2 = findViewById(R.id.txtLv2);
        txtLv3 = findViewById(R.id.txtLv3);
        txtLv4 = findViewById(R.id.txtLv4);
        txtLv5 = findViewById(R.id.txtLv5);

        findViewById(R.id.bntLv1Current).setOnClickListener(this);
        findViewById(R.id.bntLv2Current).setOnClickListener(this);
        findViewById(R.id.bntLv3Current).setOnClickListener(this);
        findViewById(R.id.bntLv4Current).setOnClickListener(this);
        findViewById(R.id.bntLv5Current).setOnClickListener(this);

        findViewById(R.id.bntLv1Completed).setOnClickListener(this);
        findViewById(R.id.bntLv2Completed).setOnClickListener(this);
        findViewById(R.id.bntLv3Completed).setOnClickListener(this);
        findViewById(R.id.bntLv4Completed).setOnClickListener(this);
        findViewById(R.id.bntLv5Completed).setOnClickListener(this);

        //Configuramos TTS que hable
        tts = new TextToSpeech(this, new TextToSpeech.OnInitListener() {

            @Override
            public void onInit(int status) {
                if (status == TextToSpeech.SUCCESS) {
                    int result = tts.setLanguage(Locale.US);
                    if (result == TextToSpeech.LANG_MISSING_DATA ||
                            result == TextToSpeech.LANG_NOT_SUPPORTED) {
                    } else {

                        //Si se inicia por primera vez.
                        if (callback.equals(Constantes.PANTALLAS.SPLASH)
                                || callback.equals(Constantes.PANTALLAS.LOGIN)) {

                            if (!UsuarioTemaProgreso.ESTADO_COMPLETO.equals(
                                    userService.obtenerEstadoUltimoTema(usuario))) {
                                lanzarDialogo(Constantes.MAIN_DIALOGO001);
                            } else {
                                lanzarDialogo(Constantes.MAIN_DIALOGO003);
                            }
                        }
                        //Activiy anterior revisar contenido
                        else if (callback.equals(Constantes.PANTALLAS.CONTENIDO1)
                                && UsuarioTemaProgreso.ESTADO_COMPLETO.equals(
                                userService.obtenerEstadoUltimoTema(usuario))) {
                            lanzarDialogo(Constantes.MAIN_DIALOGO002);
                        }
                    }
                }
            }
        }, "com.google.android.tts");

        //Cargamos progreso de usuario
        cargarProgreso();


    }

    private void cargarProgreso() {
        this.progresos = userService.obtenerProgresoUsuario(usuario);

        if (progresos.size() == 5) {
            configurarNivelView(mapLv1, txtLv1, progresos.get(0));
            configurarNivelView(mapLv2, txtLv2, progresos.get(1));
            configurarNivelView(mapLv3, txtLv3, progresos.get(2));
            configurarNivelView(mapLv4, txtLv4, progresos.get(3));
            configurarNivelView(mapLv5, txtLv5, progresos.get(4));
        }
    }

    private void lanzarDialogo(String idFase) {

        try {

            Locale locSpanishLatin = new Locale("spa", "MEX");
            tts.setLanguage(locSpanishLatin);

            switch (idFase) {
                case Constantes.MAIN_DIALOGO001:
                case Constantes.MAIN_DIALOGO002:
                case Constantes.MAIN_DIALOGO003:
                    amvDialogo.setVideoFromAssets("video1.mp4");
                    tts.speak(String.format(idFase, usuario.getNombre()),
                            TextToSpeech.QUEUE_FLUSH, null, null);
                    break;
            }

        } catch (Exception ex) {
            ex.printStackTrace();
            Toast.makeText(MainActivity.this, "Error", Toast.LENGTH_SHORT).show();
        }
    }

    private void configurarNivelView(Map<String, Button> nivel, TextView texto, UsuarioTemaProgreso progreso) {
        switch (progreso.getEstado()) {
            case UsuarioTemaProgreso.ESTADO_COMPLETO:
                nivel.get(UsuarioTemaProgreso.ESTADO_COMPLETO).setVisibility(View.VISIBLE);
                nivel.get(UsuarioTemaProgreso.ESTADO_PROGRESO).setVisibility(View.GONE);
                nivel.get(UsuarioTemaProgreso.ESTADO_NOINICIADO).setVisibility(View.GONE);
                texto.setText(progreso.getRefTema().getTitulo());
                break;
            case UsuarioTemaProgreso.ESTADO_PROGRESO:
                nivel.get(UsuarioTemaProgreso.ESTADO_COMPLETO).setVisibility(View.GONE);
                nivel.get(UsuarioTemaProgreso.ESTADO_PROGRESO).setVisibility(View.VISIBLE);
                nivel.get(UsuarioTemaProgreso.ESTADO_NOINICIADO).setVisibility(View.GONE);
                texto.setText(progreso.getRefTema().getTitulo());
                break;
            case UsuarioTemaProgreso.ESTADO_NOINICIADO:
                nivel.get(UsuarioTemaProgreso.ESTADO_COMPLETO).setVisibility(View.GONE);
                nivel.get(UsuarioTemaProgreso.ESTADO_PROGRESO).setVisibility(View.GONE);
                nivel.get(UsuarioTemaProgreso.ESTADO_NOINICIADO).setVisibility(View.VISIBLE);
                texto.setText(progreso.getRefTema().getTitulo());
                break;
        }
    }

    private void onClickAbrirTema(Long idTemaActual, Long idProximoTema) {

        Intent intent = new Intent(MainActivity.this, ContenidoActivity.class);
        intent.putExtra(Constantes.CALLBACK_KEY, Constantes.PANTALLAS.MAIN);
        intent.putExtra(Constantes.USUARIO_KEY, usuario);
        intent.putExtra(Constantes.TEMA_ACTUAL_KEY, userService.obtenerProgresoByTema(usuario, idTemaActual));
        intent.putExtra(Constantes.TEMA_SIGUEN_KEY, userService.obtenerProgresoByTema(usuario, idProximoTema));
        startActivity(intent);
    }

    private void instanceServices(Context context) {
        this.userService = UsuarioService.getUsuarioService(context);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.bntLv1Current:
            case R.id.bntLv1Completed:
                onClickAbrirTema(ConfAppService.TEMA_1, ConfAppService.TEMA_2);
                break;
            case R.id.bntLv2Current:
            case R.id.bntLv2Completed:
                onClickAbrirTema(ConfAppService.TEMA_2, ConfAppService.TEMA_3);
                break;
            case R.id.bntLv3Current:
            case R.id.bntLv3Completed:
                onClickAbrirTema(ConfAppService.TEMA_3, ConfAppService.TEMA_4);
                break;
            case R.id.bntLv4Current:
            case R.id.bntLv4Completed:
                onClickAbrirTema(ConfAppService.TEMA_4, ConfAppService.TEMA_5);
                break;
            case R.id.bntLv5Current:
            case R.id.bntLv5Completed:
                onClickAbrirTema(ConfAppService.TEMA_5, ConfAppService.TEMA_5);
                break;
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        amvDialogo.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        amvDialogo.onPause();
    }

    /*
    @Override
    public void onBackPressed() {
        new AlertDialog.Builder(this)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .setTitle("Cerrar aplicación")
                .setMessage("¿Desea salir de la aplicación?")
                .setPositiveButton("Si", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        MainActivity.super.onBackPressed();
                    }
                })
                .setNegativeButton("No", null)
                .show();
    }
     */
}