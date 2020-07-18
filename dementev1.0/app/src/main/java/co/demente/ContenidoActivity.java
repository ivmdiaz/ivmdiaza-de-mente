package co.demente;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.speech.tts.UtteranceProgressListener;
import android.view.View;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.alphamovie.lib.AlphaMovieView;

import java.util.HashMap;
import java.util.Locale;

import co.demente.core.servicios.negocio.UsuarioService;
import co.demente.data.model.Usuario;
import co.demente.data.model.UsuarioTemaProgreso;
import co.demente.util.Constantes;

public class ContenidoActivity extends AppCompatActivity implements View.OnClickListener {

    private UsuarioService userService;
    private UsuarioTemaProgreso temaActual, temaSiguiente;

    private AlphaMovieView amvDialogo;
    private WebView webView;
    private Button btnEvaluacion, btnFinalizar;
    private TextView txtTituloContenido;
    private TextToSpeech tts;

    private Usuario usuario;

    HashMap<String, String> myHashRender;
    String urlAssetVoice;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contenido);

        //Configuramos servicios
        instanceServices(this);

        //Obtenemos variables de sesión
        this.usuario = (Usuario) getIntent().getSerializableExtra(Constantes.USUARIO_KEY);
        this.temaActual = (UsuarioTemaProgreso) getIntent().getSerializableExtra(Constantes.TEMA_ACTUAL_KEY);
        this.temaSiguiente = (UsuarioTemaProgreso) getIntent().getSerializableExtra(Constantes.TEMA_SIGUEN_KEY);

        //Configuramos vistas de la activiy
        amvDialogo = findViewById(R.id.amvContenido);
        btnEvaluacion = findViewById(R.id.btnEvaluacion);
        btnFinalizar = findViewById(R.id.btnFinalizar);
        webView = findViewById(R.id.webContenido);
        txtTituloContenido = findViewById(R.id.txtTituloContenido);

        btnEvaluacion.setOnClickListener(this);
        btnFinalizar.setOnClickListener(this);

        webView.setOnScrollChangeListener(new View.OnScrollChangeListener() {
            @Override
            public void onScrollChange(View view, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
                WebView webView = (WebView) view;
                float contentHeight = webView.getContentHeight() * webView.getScaleY();
                float total = contentHeight * getResources().getDisplayMetrics().density - view.getHeight();
                float percent = Math.min(scrollY / (total - getResources().getDisplayMetrics().density), 1);
                //Si llego al final del scroll.
                if (scrollY >= total - 1) {
                    btnEvaluacion.setVisibility(View.VISIBLE);
                }
            }
        });

        //Configuramos TTS que hable
        tts = new TextToSpeech(this, new TextToSpeech.OnInitListener() {

            @Override
            public void onInit(int status) {
                if (status == TextToSpeech.SUCCESS) {

                    tts.setOnUtteranceProgressListener(new UtteranceProgressListener() {
                        @Override
                        public void onStart(String s) {
                        }

                        @Override
                        public void onDone(String s) {
                            switch (s) {
                                case "idEvaluacion":
                                    btnFinalizar.setClickable(true);
                                    break;
                            }
                        }

                        @Override
                        public void onError(String s) {
                        }
                    });

                    myHashRender = new HashMap();
                    String wakeUpText = "Are you up yet?";
                    urlAssetVoice = String.format(Constantes.ASSET_PATH, "/voice/it-IT-Wavenet-D.wav");
                    myHashRender.put(TextToSpeech.Engine.KEY_PARAM_UTTERANCE_ID, wakeUpText);

                    Locale locSpanishLatin = new Locale("spa", "MEX");
                    int result = tts.setLanguage(locSpanishLatin);
                    if (result == TextToSpeech.LANG_MISSING_DATA ||
                            result == TextToSpeech.LANG_NOT_SUPPORTED) {
                    }
                }
            }
        }, "com.google.android.tts");


        //Cargamos contenido y titulo del tema:
        txtTituloContenido.setText(temaActual.getRefTema().getTitulo());

        String urlAsset = String.format(Constantes.ASSET_PATH, temaActual.getRefTema().getAssetContenido());
        webView.loadUrl(urlAsset);
    }

    private void lanzarDialogo(String idFase) {
        try {
            switch (idFase) {
                case Constantes.EVAL_DIALOGO001:
                    amvDialogo.setVideoFromAssets("video1.mp4");

                    tts.synthesizeToFile(String.format(idFase, usuario.getNombre()), myHashRender, urlAssetVoice);

                    tts.speak(String.format(idFase, usuario.getNombre()),
                            TextToSpeech.QUEUE_FLUSH, null, "idEvaluacion");


                    break;
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            Toast.makeText(ContenidoActivity.this, "Error", Toast.LENGTH_SHORT).show();
        }
    }

    private void onClickEvaluacion() {
        webView.setVisibility(View.GONE);
        btnEvaluacion.setVisibility(View.GONE);
        btnFinalizar.setVisibility(View.VISIBLE);
        btnFinalizar.setClickable(false);

        lanzarDialogo(Constantes.EVAL_DIALOGO001);
    }

    private void onClickFinalizar() {

        if (!UsuarioTemaProgreso.ESTADO_COMPLETO.equals(temaSiguiente.getEstado())) {

            //Finalizó todos los temas
            if (temaActual.getIdTema().longValue() == temaSiguiente.getIdTema().longValue()) {
                temaActual.setEstado(UsuarioTemaProgreso.ESTADO_COMPLETO);
                userService.actualizarProgreso(temaActual);
            } else {
                temaActual.setEstado(UsuarioTemaProgreso.ESTADO_COMPLETO);
                temaSiguiente.setEstado(UsuarioTemaProgreso.ESTADO_PROGRESO);
                userService.actualizarProgreso(temaActual, temaSiguiente);
            }

            Intent intent = new Intent(ContenidoActivity.this, MainActivity.class);
            intent.putExtra(Constantes.USUARIO_KEY, usuario);
            intent.putExtra(Constantes.CALLBACK_KEY, Constantes.PANTALLAS.CONTENIDO1);
            startActivity(intent);

        }
        //Si ya acabó
        else{
            Intent intent = new Intent(ContenidoActivity.this, MainActivity.class);
            intent.putExtra(Constantes.USUARIO_KEY, usuario);
            intent.putExtra(Constantes.CALLBACK_KEY, Constantes.PANTALLAS.CONTENIDO2);
            startActivity(intent);
        }


    }

    private void instanceServices(Context context) {
        this.userService = UsuarioService.getUsuarioService(context);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnEvaluacion:
                onClickEvaluacion();
                break;
            case R.id.btnFinalizar:
                onClickFinalizar();
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

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}