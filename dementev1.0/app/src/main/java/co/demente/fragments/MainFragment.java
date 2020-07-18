package co.demente.fragments;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.BounceInterpolator;

import androidx.fragment.app.Fragment;

import com.github.clans.fab.FloatingActionButton;

import co.demente.R;
import co.demente.events.ui.TopicSelectedEvent;
import co.demente.common.Shared;
import co.demente.negocio.data.model.TopicUserProgress;
import co.demente.negocio.data.model.User;
import co.demente.ui.PopupManager;
import co.demente.ui.TopicView;
import co.demente.util.Constantes;
import co.demente.util.Utils;

public class MainFragment extends Fragment {

    private FloatingActionButton mSettingsButton, mAvatarButton;
    private TopicView topic1, topic2, topic3, topic4, topic5;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = LayoutInflater.from(Shared.context).inflate(R.layout.main_fragment, container, false);
        User mUser = Shared.engine.getmUser();

        TopicUserProgress progress1 = Shared.services.mUserService.obtenerProgresoByTema(mUser, 1L);
        topic1 = view.findViewById(R.id.select_difficulty_1);
        topic1.setUiTopic(progress1);
        setOnClick(topic1, progress1);

        TopicUserProgress progress2 = Shared.services.mUserService.obtenerProgresoByTema(mUser, 2L);
        topic2 = view.findViewById(R.id.select_difficulty_2);
        topic2.setUiTopic(progress2);
        setOnClick(topic2, progress2);

        TopicUserProgress progress3 = Shared.services.mUserService.obtenerProgresoByTema(mUser, 3L);
        topic3 = view.findViewById(R.id.select_difficulty_3);
        topic3.setUiTopic(progress3);
        setOnClick(topic3, progress3);

        TopicUserProgress progress4 = Shared.services.mUserService.obtenerProgresoByTema(mUser, 4L);
        topic4 = view.findViewById(R.id.select_difficulty_4);
        topic4.setUiTopic(progress4);
        setOnClick(topic4, progress4);

        TopicUserProgress progress5 = Shared.services.mUserService.obtenerProgresoByTema(mUser, 5L);
        topic5 = view.findViewById(R.id.select_difficulty_5);
        topic5.setUiTopic(progress5);
        setOnClick(topic5, progress5);

        //Start animate main
        animate(topic1, topic2, topic3, topic4, topic5);

        //Config button floating
        mSettingsButton = view.findViewById(R.id.menu_item_config);
        mSettingsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PopupManager.showPopupSettings();
            }
        });

        //Avatar speaker button floating
        mAvatarButton = view.findViewById(R.id.menu_item_avatar);
        mAvatarButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PopupManager.showPopupAvatar(Constantes.MAIN_DIALOGO001);
            }
        });

        return view;
    }

    private void animate(View... view) {
        AnimatorSet animatorSet = new AnimatorSet();
        AnimatorSet.Builder builder = animatorSet.play(new AnimatorSet());
        for (int i = 0; i < view.length; i++) {
            ObjectAnimator scaleX = ObjectAnimator.ofFloat(view[i], "scaleX", 0.8f, 1f);
            ObjectAnimator scaleY = ObjectAnimator.ofFloat(view[i], "scaleY", 0.8f, 1f);
            builder.with(scaleX).with(scaleY);
        }
        animatorSet.setDuration(1200);
        animatorSet.setInterpolator(new BounceInterpolator());
        animatorSet.start();
    }

    protected void animateAllAssetsOff(View topicView, AnimatorListenerAdapter adapter) {

        long durationCorner = 350;
        long durationCenter = 380;


        // First row animation up
        ObjectAnimator upAnimatorTopic1y = null;
        upAnimatorTopic1y = ObjectAnimator.ofFloat(topic1, "translationY", Utils.px(-150));
        upAnimatorTopic1y.setInterpolator(new AccelerateInterpolator(2));
        upAnimatorTopic1y.setDuration(durationCorner);

        ObjectAnimator upAnimatorTopic2y = null;
        upAnimatorTopic2y = ObjectAnimator.ofFloat(topic2, "translationY", Utils.px(-150));
        upAnimatorTopic2y.setInterpolator(new AccelerateInterpolator(2));
        upAnimatorTopic2y.setDuration(durationCenter);

        ObjectAnimator upAnimatorTopic3y = null;
        upAnimatorTopic3y = ObjectAnimator.ofFloat(topic3, "translationY", Utils.px(-150));
        upAnimatorTopic3y.setInterpolator(new AccelerateInterpolator(2));
        upAnimatorTopic3y.setDuration(durationCorner);


        // Second row animation down
        ObjectAnimator downAnimatorTopic4y = null;
        downAnimatorTopic4y = ObjectAnimator.ofFloat(topic4, "translationY", Utils.px(150));
        downAnimatorTopic4y.setInterpolator(new AccelerateInterpolator(2));
        downAnimatorTopic4y.setDuration(durationCorner);

        ObjectAnimator downAnimatorTopic5y = null;
        downAnimatorTopic5y = ObjectAnimator.ofFloat(topic5, "translationY", Utils.px(150));
        downAnimatorTopic5y.setInterpolator(new AccelerateInterpolator(2));
        downAnimatorTopic5y.setDuration(durationCenter);


        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.playTogether(
                upAnimatorTopic1y, upAnimatorTopic2y, upAnimatorTopic3y,
                downAnimatorTopic4y, downAnimatorTopic5y
        );

        animatorSet.addListener(adapter);
        animatorSet.start();
    }

    private void setOnClick(final View view, final TopicUserProgress progress) {
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(!TopicUserProgress.ESTADO_NOINICIADO.equals(progress.getEstado())){
                    animateAllAssetsOff(view, new AnimatorListenerAdapter() {
                        @Override
                        public void onAnimationEnd(Animator animation) {
                            Shared.eventBus.notify(new TopicSelectedEvent(progress.getRefTopic()));
                        }
                    });
                }
            }
        });
    }
}