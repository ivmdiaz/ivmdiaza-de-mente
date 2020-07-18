package co.demente.ui;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;


import co.demente.R;
import co.demente.common.Shared;
import co.demente.util.FontLoader;

public class PopupSettingsView extends LinearLayout {

    private ImageView mSoundImage;
    private TextView mSoundText;

    public PopupSettingsView(Context context) {
        this(context, null);
    }

    public PopupSettingsView(Context context, AttributeSet attrs) {
        super(context, attrs);
        setOrientation(LinearLayout.VERTICAL);
        //setBackgroundResource(R.drawable.settings_popup);
        LayoutInflater.from(getContext()).inflate(R.layout.popup_settings_view, this, true);
        mSoundText = (TextView) findViewById(R.id.sound_off_text);
        FontLoader.setTypeface(context, new TextView[]{mSoundText}, FontLoader.Font.GROBOLD);
        mSoundImage = (ImageView) findViewById(R.id.sound_image);
        View soundOff = findViewById(R.id.sound_off);
        soundOff.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Shared.engine.voice =  !Shared.engine.voice;
                setMusicButton();
            }
        });
        setMusicButton();
    }

    private void setMusicButton() {
        if (Shared.engine.voice) {
            mSoundText.setText("Sonido activado");
            mSoundImage.setImageResource(R.drawable.sound_on);
        } else {
            mSoundText.setText("Sonido desactivado");
            mSoundImage.setImageResource(R.drawable.sound_off);
        }
    }

}
