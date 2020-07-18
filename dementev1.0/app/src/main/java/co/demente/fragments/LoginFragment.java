package co.demente.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.speech.tts.TextToSpeech;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.alphamovie.lib.AlphaMovieView;

import java.util.Calendar;
import java.util.Locale;

import co.demente.R;
import co.demente.common.Shared;
import co.demente.events.ui.StartEvent;
import co.demente.negocio.data.model.User;
import co.demente.util.Constantes;

public class LoginFragment extends Fragment {

    private AlphaMovieView mAlphaMovieView;
    private TextToSpeech mTextToSpeech;

    private EditText mEtName, mEtAge;

    private User newUser;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = LayoutInflater.from(Shared.context).inflate(R.layout.login_fragment, container, false);

        mAlphaMovieView = view.findViewById(R.id.alphaMovieAvatarLogin);
        mEtName = view.findViewById(R.id.etName);
        mEtAge = view.findViewById(R.id.etAge);

        Button mButtonSign = view.findViewById(R.id.btnGuardarLogin);
        mButtonSign.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean signed = signValidate();
                if(signed){
                    Shared.eventBus.notify(new StartEvent(newUser));
                }
            }
        });

        //Configuramos TTS que hable
        mTextToSpeech = new TextToSpeech(Shared.context, new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if (status == TextToSpeech.SUCCESS) {
                    Locale locSpanishLatin = new Locale("spa", "MEX");
                    int result = mTextToSpeech.setLanguage(locSpanishLatin);
                    if (result == TextToSpeech.LANG_MISSING_DATA ||
                            result == TextToSpeech.LANG_NOT_SUPPORTED) {
                    } else {
                        launchAvatar();
                    }
                }
            }
        }, "com.google.android.tts");

        return view;
    }

    private boolean signValidate() {
        try {

            boolean formValid = true;
            if (TextUtils.isEmpty(mEtName.getText())) {
                mEtName.setError(" ");
                formValid = false;
            }

            if (TextUtils.isEmpty(mEtAge.getText())) {
                mEtAge.setError(" ");
                formValid = false;
            }

            if (formValid) {
                newUser = new User();
                newUser.setNombre(mEtName.getText().toString().trim());

                Integer anios = Integer.parseInt(mEtAge.getText().toString().trim());
                Calendar calendar = Calendar.getInstance();
                calendar.add(Calendar.YEAR, -anios);
                newUser.setFechaNacimiento(calendar.getTime());

                //add the user to database
                newUser = Shared.services.mUserService.registrarUsuarioPrimeraVez(newUser);

                //return statement
                return newUser != null && newUser.getId() != null && newUser.getId() != 0L;
            }

        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return false;
    }


    //TODO: Centralizar metodo
    private void launchAvatar() {
        try {
            String idFase = Constantes.LOGIN_DIALOGO001;
            switch (idFase) {
                case Constantes.LOGIN_DIALOGO001:
                    mAlphaMovieView.setVideoFromAssets("video1.mp4");
                    mTextToSpeech.speak(String.format(idFase, "Mauricio"),
                            TextToSpeech.QUEUE_FLUSH, null, null);
                    break;
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}