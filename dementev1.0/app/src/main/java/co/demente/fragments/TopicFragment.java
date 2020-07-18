package co.demente.fragments;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import co.demente.R;
import co.demente.common.Shared;
import co.demente.events.ui.BackGameEvent;
import co.demente.events.ui.TopicSelectedEvent;
import co.demente.events.ui.TopicTestEvent;
import co.demente.negocio.data.model.Topic;
import co.demente.negocio.data.model.TopicUserProgress;
import co.demente.ui.PopupManager;
import co.demente.util.Constantes;
import co.demente.util.FontLoader;

public class TopicFragment extends Fragment {

    private View mView;
    private WebView mWebView;
    private Button mButtonTest, mButtonBack;

    private TextView txtTitleTopic;

    private Topic mTopic;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.topic_fragment, container, false);

        //Obtain topic selected
        mTopic = Shared.engine.getSelectedTopic();

        //Button test
        mButtonTest = mView.findViewById(R.id.buttonTest);
        FontLoader.setTypeface(getContext(), new Button[]{mButtonTest}, FontLoader.Font.GROBOLD);
        mButtonTest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Shared.eventBus.notify(new TopicTestEvent());
            }
        });

        //Button back
        mButtonBack = mView.findViewById(R.id.buttonBack);
        FontLoader.setTypeface(getContext(), new Button[]{mButtonBack}, FontLoader.Font.GROBOLD);
        mButtonBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Shared.eventBus.notify(new BackGameEvent());
            }
        });

        //Content topic from web html resource of asset
        mWebView = mView.findViewById(R.id.webContainer);
        mWebView.setOnScrollChangeListener(new View.OnScrollChangeListener() {
            @Override
            public void onScrollChange(View view, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
                WebView webView = (WebView) view;
                float contentHeight = webView.getContentHeight() * webView.getScaleY();
                float total = contentHeight * getResources().getDisplayMetrics().density - view.getHeight();
                float percent = Math.min(scrollY / (total - getResources().getDisplayMetrics().density), 1);
                //Arrive end scroll'.
                if (scrollY >= total - 1) {
                    mButtonTest.setVisibility(View.VISIBLE);
                }
            }
        });

        txtTitleTopic = mView.findViewById(R.id.txtTitleTopic);
        FontLoader.setTypeface(getContext(), new TextView[]{txtTitleTopic}, FontLoader.Font.GROBOLD);

        if(mTopic != null){

            //Load webview
            String urlAsset = String.format(Constantes.ASSET_PATH, mTopic.getAssetContenido());
            mWebView.loadUrl(urlAsset);

            //Load Title
            txtTitleTopic.setText(mTopic.getTitulo());
        }

        return mView;
    }

}