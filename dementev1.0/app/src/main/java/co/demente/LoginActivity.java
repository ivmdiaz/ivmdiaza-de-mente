package co.demente;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.alphamovie.lib.AlphaMovieView;
import com.google.android.material.textfield.TextInputLayout;

import java.util.Calendar;
import java.util.Locale;

import co.demente.core.servicios.negocio.UsuarioService;
import co.demente.data.model.Usuario;
import co.demente.util.Constantes;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    private UsuarioService userService;

    private AlphaMovieView amvDialogo;
    private TextToSpeech tts;

    private TextInputLayout itNombre;
    private TextInputLayout itEdad;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //Configuramos servicios
        instanceServices(this);

        //Configuramos ventana de dialogo
        amvDialogo = findViewById(R.id.amvLogin);

        //Configuramos formulario del login
        itNombre = findViewById(R.id.itNombre);
        itEdad = findViewById(R.id.itEdad);

        Button btnGuardar = findViewById(R.id.btnGuardarLogin);
        btnGuardar.setOnClickListener(this);
        Button btnRepetir = findViewById(R.id.btnGuardarLogin);
        btnRepetir.setOnClickListener(this);

        //Configuramos TTS que hable
        tts = new TextToSpeech(this, new TextToSpeech.OnInitListener() {

            @Override
            public void onInit(int status) {
                // TODO Auto-generated method stub
                if (status == TextToSpeech.SUCCESS) {
                    int result = tts.setLanguage(Locale.US);
                    if (result == TextToSpeech.LANG_MISSING_DATA ||
                            result == TextToSpeech.LANG_NOT_SUPPORTED) {
                    }
                    else{
                        lanzarDialogo(Constantes.LOGIN_DIALOGO001);
                    }
                }
            }
        }, "com.google.android.tts");


    }

    private void instanceServices(Context context) {
        this.userService = UsuarioService.getUsuarioService(context);
    }

    public void onClickRepetir() {
        lanzarDialogo(Constantes.LOGIN_DIALOGO001);
    }

    public void onClickGuardar() {

        try {

            boolean formValid = true;
            if (TextUtils.isEmpty(itNombre.getEditText().getText())) {
                itNombre.setError(" ");
                formValid = false;
            }

            if (TextUtils.isEmpty(itEdad.getEditText().getText())) {
                itEdad.setError(" ");
                formValid = false;
            }

            if (formValid) {
                Usuario nuevoUsuario = new Usuario();
                nuevoUsuario.setNombre(itNombre.getEditText().getText().toString().trim());

                Integer anios = Integer.parseInt(itEdad.getEditText().getText().toString().trim());
                Calendar calendar = Calendar.getInstance();
                calendar.add(Calendar.YEAR, -anios);
                nuevoUsuario.setFechaNacimiento(calendar.getTime());

                //Agregamos el usuario a la base de datos.
                nuevoUsuario = userService.registrarUsuarioPrimeraVez(nuevoUsuario);

                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                intent.putExtra(Constantes.USUARIO_KEY, nuevoUsuario);
                intent.putExtra(Constantes.CALLBACK_KEY, Constantes.PANTALLAS.LOGIN);
                startActivity(intent);
            }

        } catch (Exception ex) {
            ex.printStackTrace();
            Toast.makeText(LoginActivity.this, "Error", Toast.LENGTH_SHORT).show();
        }
    }

    private void lanzarDialogo(String idFase) {

        try {
            amvDialogo.setVideoFromAssets("video1.mp4");
            Locale locSpanishLatin = new Locale("spa", "MEX");
            tts.setLanguage(locSpanishLatin);
            tts.speak(idFase, TextToSpeech.QUEUE_FLUSH, null, null);
        } catch (Exception ex) {
            ex.printStackTrace();
            Toast.makeText(LoginActivity.this, "Error", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onClick(View view) {
        switch(view.getId()) {
            case R.id.btnGuardarLogin:
                onClickGuardar();
                break;
            case R.id.btnRepetirAudioLogin:
                onClickRepetir();
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
}