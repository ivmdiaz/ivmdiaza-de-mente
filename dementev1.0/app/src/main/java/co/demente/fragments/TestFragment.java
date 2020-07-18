package co.demente.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.fragment.app.Fragment;

import co.demente.R;
import co.demente.common.Shared;
import co.demente.events.ui.TopicFinishedEvent;
import co.demente.negocio.data.model.Topic;
import co.demente.negocio.data.model.TopicUserProgress;
import co.demente.negocio.data.model.User;
import co.demente.ui.PopupManager;
import co.demente.util.Constantes;
import co.demente.util.FontLoader;

public class TestFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View mView = inflater.inflate(R.layout.test_fragment, container, false);

        //Obtain topic selected
        Topic mTopic = Shared.engine.getSelectedTopic();
        final User mUser = Shared.engine.getmUser();
        final TopicUserProgress mProgress = Shared.services.mUserService.obtenerProgresoByTema(mUser, mTopic.getId());

        PopupManager.showPopupAvatar(Constantes.EVAL_DIALOGO001);

        //Button test
        Button mButtonEnd = mView.findViewById(R.id.buttonFinalizar);
        FontLoader.setTypeface(getContext(), new Button[]{mButtonEnd}, FontLoader.Font.GROBOLD);
        mButtonEnd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean lastTopicFinshed = Shared.services.mUserService.advanceProgress(mUser, mProgress);
                Shared.eventBus.notify(new TopicFinishedEvent(lastTopicFinshed));
            }
        });

        return mView;
    }

}