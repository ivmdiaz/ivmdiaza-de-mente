package co.demente;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.WindowManager;
import android.widget.Toast;

import co.demente.core.servicios.negocio.ConfAppService;
import co.demente.core.servicios.negocio.UsuarioService;
import co.demente.util.Constantes;

public class SplashActivity extends AppCompatActivity {

    private UsuarioService userService;
    private ConfAppService confService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        //Inflamos la vista para configurar el splash de la aplicación.
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        //Configuramos servicios
        instanceServices(this);

        //Configuramos que el splash ocupe toda la pantalla del dispositivo.
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        //Lanzamos proceso para pausar la pantalla durante 2 segundos.
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                try {

                    //Si la aplicación no ha sido configurada, la configuramos.
                    confService.configurarAplicacion();

                    //Verificamos que exista usuario creado en la base de datos
                    boolean usuariosIsEmpty = userService.usuariosIsEmpty();

                    //Lanzamos ventana de login
                    if (usuariosIsEmpty) {
                        Intent intent = new Intent(SplashActivity.this, LoginActivity.class);
                        startActivity(intent);
                    }
                    //Lanzamos ventana principal.
                    else {
                        Intent intent = new Intent(SplashActivity.this, MainActivity.class);
                        intent.putExtra(Constantes.USUARIO_KEY, userService.getFirstUsuario());
                        intent.putExtra(Constantes.CALLBACK_KEY, Constantes.PANTALLAS.SPLASH);
                        startActivity(intent);
                    }

                } catch (Exception ex) {
                    ex.printStackTrace();
                    Toast.makeText(SplashActivity.this, "Error", Toast.LENGTH_SHORT).show();
                }

                finish();
            }
        }, 2000);
    }

    private void instanceServices(Context context) {
        this.userService = UsuarioService.getUsuarioService(context);
        this.confService = ConfAppService.getConfAppService(context);
    }
}