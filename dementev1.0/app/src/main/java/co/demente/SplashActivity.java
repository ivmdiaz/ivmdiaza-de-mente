package co.demente;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.widget.ImageView;
import android.widget.Toast;

import co.demente.common.Shared;
import co.demente.engine.Engine;
import co.demente.events.EventBus;
import co.demente.negocio.services.Services;
import co.demente.util.Utils;

public class SplashActivity extends AppCompatActivity {

    private ImageView mBackgroundImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        //Inflamos la vista para configurar el splash de la aplicación.
        super.onCreate(savedInstanceState);

        //Configuración de la aplicación
        Shared.context = getApplicationContext();
        Shared.engine = Engine.getInstance();
        Shared.eventBus = EventBus.getInstance();
        Shared.services = Services.getInstance();

        setContentView(R.layout.activity_splash);
        mBackgroundImage = (ImageView) findViewById(R.id.background_image_splash);

        // asignamos fondo de pantalla
        setBackgroundImage();

        //Lanzamos proceso para pausar la pantalla durante 2 segundos.
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                try {

                    //Si la aplicación no ha sido configurada, la configuramos.
                    Shared.services.mConfAppService.configurarAplicacion();

                    Intent intent = new Intent(SplashActivity.this, MainActivity.class);
                    startActivity(intent);
                    overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);

                } catch (Exception ex) {
                    ex.printStackTrace();
                    Toast.makeText(SplashActivity.this, "Error", Toast.LENGTH_SHORT).show();
                }

                finish();
            }
        }, 5000);
    }

    private void setBackgroundImage() {
        Bitmap bitmap = Utils.scaleDown(R.drawable.background3, Utils.screenWidth(), Utils.screenHeight());
        bitmap = Utils.crop(bitmap, Utils.screenHeight(), Utils.screenWidth());
        bitmap = Utils.downscaleBitmap(bitmap, 2);
        mBackgroundImage.setImageBitmap(bitmap);
    }

}