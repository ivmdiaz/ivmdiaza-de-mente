package co.demente;

import androidx.fragment.app.FragmentActivity;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.widget.ImageView;

import co.demente.events.ui.BackGameEvent;
import co.demente.engine.ScreenController;
import co.demente.common.Shared;
import co.demente.events.ui.StartEvent;
import co.demente.negocio.data.model.User;
import co.demente.ui.PopupManager;
import co.demente.util.Utils;

public class MainActivity extends FragmentActivity {

    private ImageView mBackgroundImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Override context application
        Shared.context = getApplicationContext();

        setContentView(R.layout.activity_main);
        mBackgroundImage = (ImageView) findViewById(R.id.background_image);

        Shared.activity = this;
        Shared.engine.start();
        Shared.engine.setBackgroundImageView(mBackgroundImage);

        // asignamos fondo de pantalla
        setBackgroundImage();

        // set select main fragment
        User currentUser = Shared.services.mUserService.getFirstUsuario();
        Shared.eventBus.notify(new StartEvent(currentUser));
    }

    private void setBackgroundImage() {
        Bitmap bitmap = Utils.scaleDown(R.drawable.background3, Utils.screenWidth(), Utils.screenHeight());
        bitmap = Utils.crop(bitmap, Utils.screenHeight(), Utils.screenWidth());
        bitmap = Utils.downscaleBitmap(bitmap, 2);
        mBackgroundImage.setImageBitmap(bitmap);
    }

    @Override
    protected void onDestroy() {
        Shared.engine.stop();
        super.onDestroy();
    }

    @Override
    public void onBackPressed() {
        if (PopupManager.isShown()) {
            PopupManager.closePopup();
            if (ScreenController.getLastScreen() == ScreenController.Screen.TOPIC) {
                Shared.eventBus.notify(new BackGameEvent());
            }
        } else if (ScreenController.getInstance().onBack()) {
            super.onBackPressed();
        }
    }
}