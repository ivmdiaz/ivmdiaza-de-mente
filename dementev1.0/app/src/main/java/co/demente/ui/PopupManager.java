package co.demente.ui;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.graphics.Color;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.RelativeLayout.LayoutParams;

import co.demente.R;
import co.demente.common.Shared;
import co.demente.events.ui.BackGameEvent;
import co.demente.events.ui.ClosePopupEvent;

public class PopupManager {

    private static ImageView configureBackGround(RelativeLayout popupContainer) {

        // background
        ImageView imageView = new ImageView(Shared.context);
        imageView.setBackgroundColor(Color.parseColor("#88555555"));
        imageView.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
        imageView.setClickable(true);
        popupContainer.addView(imageView);

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Shared.eventBus.notify(new ClosePopupEvent());
            }
        });

        return imageView;
    }

    public static void showPopupSettings() {

        RelativeLayout popupContainer = (RelativeLayout) Shared.activity.findViewById(R.id.popup_container);
        popupContainer.removeAllViews();

        ImageView imageView = configureBackGround(popupContainer);

        // popup
        PopupSettingsView popupSettingsView = new PopupSettingsView(Shared.context);
        int width = Shared.context.getResources().getDimensionPixelSize(R.dimen.popup_settings_width);
        int height = Shared.context.getResources().getDimensionPixelSize(R.dimen.popup_settings_height);
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(width, height);
        params.addRule(RelativeLayout.CENTER_IN_PARENT);
        params.addRule(RelativeLayout.CENTER_VERTICAL);

        popupContainer.addView(popupSettingsView, params);

        // animate all together
        ObjectAnimator scaleXAnimator = ObjectAnimator.ofFloat(popupSettingsView, "scaleX", 0f, 1f);
        ObjectAnimator scaleYAnimator = ObjectAnimator.ofFloat(popupSettingsView, "scaleY", 0f, 1f);
        ObjectAnimator alphaAnimator = ObjectAnimator.ofFloat(imageView, "alpha", 0f, 1f);
        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.playTogether(scaleXAnimator, scaleYAnimator, alphaAnimator);
        animatorSet.setDuration(500);
        animatorSet.setInterpolator(new DecelerateInterpolator(2));
        animatorSet.start();
    }

    public static void showPopupAvatar(String idVocabulaty) {

        if (Shared.engine.voice) {

            RelativeLayout popupContainer = (RelativeLayout) Shared.activity.findViewById(R.id.popup_container);
            popupContainer.removeAllViews();

            ImageView imageView = configureBackGround(popupContainer);

            // popup
            PopupAvatarView popupAvatarView = new PopupAvatarView(Shared.context, imageView, idVocabulaty);
            int width = Shared.context.getResources().getDimensionPixelSize(R.dimen.popup_avatar_width);
            int height = Shared.context.getResources().getDimensionPixelSize(R.dimen.popup_avatar_height);
            RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(width, height);
            params.addRule(RelativeLayout.CENTER_IN_PARENT);
            popupContainer.addView(popupAvatarView, params);

            // animate all together
            ObjectAnimator scaleXAnimator = ObjectAnimator.ofFloat(popupAvatarView, "scaleX", 0f, 1f);
            ObjectAnimator scaleYAnimator = ObjectAnimator.ofFloat(popupAvatarView, "scaleY", 0f, 1f);
            ObjectAnimator alphaAnimator = ObjectAnimator.ofFloat(imageView, "alpha", 0f, 1f);
            AnimatorSet animatorSet = new AnimatorSet();
            animatorSet.playTogether(scaleXAnimator, scaleYAnimator, alphaAnimator);
            animatorSet.setDuration(500);
            animatorSet.setInterpolator(new DecelerateInterpolator(2));
            animatorSet.start();
        }
    }

    public static void closePopup() {
        final RelativeLayout popupContainer = (RelativeLayout) Shared.activity.findViewById(R.id.popup_container);
        int childCount = popupContainer.getChildCount();
        if (childCount > 0) {
            View background = null;
            View viewPopup = null;
            if (childCount == 1) {
                viewPopup = popupContainer.getChildAt(0);
            } else {
                background = popupContainer.getChildAt(0);
                viewPopup = popupContainer.getChildAt(1);
            }

            AnimatorSet animatorSet = new AnimatorSet();
            ObjectAnimator scaleXAnimator = ObjectAnimator.ofFloat(viewPopup, "scaleX", 0f);
            ObjectAnimator scaleYAnimator = ObjectAnimator.ofFloat(viewPopup, "scaleY", 0f);
            if (childCount > 1) {
                ObjectAnimator alphaAnimator = ObjectAnimator.ofFloat(background, "alpha", 0f);
                animatorSet.playTogether(scaleXAnimator, scaleYAnimator, alphaAnimator);
            } else {
                animatorSet.playTogether(scaleXAnimator, scaleYAnimator);
            }
            animatorSet.setDuration(300);
            animatorSet.setInterpolator(new AccelerateInterpolator(2));
            animatorSet.addListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    popupContainer.removeAllViews();
                }
            });
            animatorSet.start();
        }
    }

    public static boolean isShown() {
        RelativeLayout popupContainer = (RelativeLayout) Shared.activity.findViewById(R.id.popup_container);
        return popupContainer.getChildCount() > 0;
    }
}
