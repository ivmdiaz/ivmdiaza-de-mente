package co.demente.ui;

import android.content.Context;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.Locale;

import co.demente.R;
import co.demente.common.Shared;
import co.demente.negocio.data.model.Topic;
import co.demente.negocio.data.model.TopicUserProgress;
import co.demente.util.FontLoader;

public class TopicView extends LinearLayout {

    private TextView mTitle;

    public TopicView(Context context) {
        this(context, null);
    }

    public TopicView(Context context, AttributeSet attrs) {
        super(context, attrs);
        LayoutInflater.from(context).inflate(R.layout.topic_view, this, true);
        setOrientation(LinearLayout.VERTICAL);
        mTitle = findViewById(R.id.title);
        FontLoader.setTypeface(getContext(), new TextView[]{mTitle}, FontLoader.Font.GROBOLD);
    }

    public void setUiTopic(TopicUserProgress progress) {

        int drawableResourceId = Shared.context.getResources().getIdentifier(
                "topic", "drawable", Shared.context.getPackageName());

        if(progress != null && TopicUserProgress.ESTADO_NOINICIADO.equals(progress.getEstado())){
            drawableResourceId = Shared.context.getResources().getIdentifier(
                    "topic_blocked", "drawable", Shared.context.getPackageName());
        }

        if (progress != null){
            mTitle.setText(progress.getRefTopic().getTitulo());
        }

        mTitle.setBackgroundResource(drawableResourceId);
    }
}
