package co.demente.ui;

import android.content.Context;
import android.speech.tts.TextToSpeech;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alphamovie.lib.AlphaMovieView;

import java.util.Locale;

import co.demente.R;
import co.demente.common.Shared;
import co.demente.events.ui.ClosePopupEvent;
import co.demente.util.Constantes;
import co.demente.util.FontLoader;

public class PopupAvatarView extends LinearLayout {

    private TextView mTitleTextAvatar;
    private ImageView mButtonCancel;

    private AlphaMovieView mAlphaMovieAvatar;
    private TextToSpeech tts;

    public PopupAvatarView(Context context, ImageView background, String idVocabulary) {
        this(context, null, background, idVocabulary);
    }

    public PopupAvatarView(Context context, AttributeSet attrs, ImageView background, final String idVocabulary) {
        super(context, attrs);
        LayoutInflater.from(getContext()).inflate(R.layout.popup_avatar_view, this, true);

        mTitleTextAvatar = findViewById(R.id.title_text_avatar);
        mButtonCancel = findViewById(R.id.buttonCancel);
        mAlphaMovieAvatar = findViewById(R.id.alphaMovieAvatar);

        FontLoader.setTypeface(context, new TextView[]{mTitleTextAvatar}, FontLoader.Font.GROBOLD);
        mButtonCancel.bringToFront();

        mButtonCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                customClosePopUp();
            }
        });

        background.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                customClosePopUp();
            }
        });

        //Configuramos TTS que hable
        tts = new TextToSpeech(context, new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if (status == TextToSpeech.SUCCESS) {
                    Locale locSpanishLatin = new Locale("spa", "MEX");
                    int result = tts.setLanguage(locSpanishLatin);
                    if (result == TextToSpeech.LANG_MISSING_DATA ||
                            result == TextToSpeech.LANG_NOT_SUPPORTED) {
                    } else {
                        lanzarDialogo(idVocabulary);
                    }
                }
            }
        }, "com.google.android.tts");

    }

    private void customClosePopUp(){
        if(tts != null){
            tts.shutdown();
        }
        if(mAlphaMovieAvatar != null){
            mAlphaMovieAvatar.stop();
        }
        Shared.eventBus.notify(new ClosePopupEvent());
    }

    private void lanzarDialogo(String idVocabulary) {
        try {

            //Video
            mAlphaMovieAvatar.setVideoFromAssets("video1.mp4");

            //Sound
            tts.speak(idVocabulary, TextToSpeech.QUEUE_FLUSH, null, null);

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

}
