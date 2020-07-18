package co.demente.engine;

import android.os.Handler;
import android.widget.ImageView;

import co.demente.events.EventObserverAdapter;
import co.demente.events.ui.BackGameEvent;
import co.demente.events.ui.ClosePopupEvent;
import co.demente.events.ui.StartEvent;
import co.demente.common.Shared;
import co.demente.events.ui.TopicFinishedEvent;
import co.demente.events.ui.TopicSelectedEvent;
import co.demente.events.ui.TopicTestEvent;
import co.demente.negocio.data.model.Topic;
import co.demente.negocio.data.model.TopicUserProgress;
import co.demente.negocio.data.model.User;
import co.demente.negocio.services.UsuarioService;
import co.demente.ui.PopupManager;
import co.demente.util.Constantes;

public class Engine extends EventObserverAdapter {

    private static Engine mInstance = null;
    private ScreenController mScreenController;
    private ImageView mBackgroundImage;
    private Handler mHandler;
    private Topic mSelectedTopic;
    private User mUser;

    public static Boolean voice = true;
    public static UsuarioService userService;

    private Engine() {
        mScreenController = ScreenController.getInstance();
        mHandler = new Handler();
    }


    public static Engine getInstance() {
        if (mInstance == null) {
            mInstance = new Engine();
        }
        return mInstance;
    }

    public void start() {
        Shared.eventBus.listen(StartEvent.TYPE, this);
        Shared.eventBus.listen(BackGameEvent.TYPE, this);
        Shared.eventBus.listen(TopicSelectedEvent.TYPE, this);
        Shared.eventBus.listen(ClosePopupEvent.TYPE, this);
        Shared.eventBus.listen(TopicTestEvent.TYPE, this);
        Shared.eventBus.listen(TopicFinishedEvent.TYPE, this);
    }

    public void setBackgroundImageView(ImageView backgroundImage) {
        mBackgroundImage = backgroundImage;
    }

    public void stop() {

        mBackgroundImage.setImageDrawable(null);
        mBackgroundImage = null;
        mHandler.removeCallbacksAndMessages(null);
        mHandler = null;

        Shared.eventBus.unlisten(StartEvent.TYPE, this);
        Shared.eventBus.unlisten(BackGameEvent.TYPE, this);
        Shared.eventBus.unlisten(TopicSelectedEvent.TYPE, this);
        Shared.eventBus.unlisten(ClosePopupEvent.TYPE, this);
        Shared.eventBus.unlisten(TopicTestEvent.TYPE, this);
        Shared.eventBus.unlisten(TopicFinishedEvent.TYPE, this);

        mInstance = null;
    }

    @Override
    public void onEvent(BackGameEvent event) {
        PopupManager.closePopup();
        mScreenController.openScreen(ScreenController.Screen.MAIN);
    }

    @Override
    public void onEvent(ClosePopupEvent event) {
        PopupManager.closePopup();
    }

    @Override
    public void onEvent(StartEvent event) {
        mUser = event.user;
        if(mUser == null){
            mScreenController.openScreen(ScreenController.Screen.LOGIN);
        }
        else{
            mScreenController.openScreen(ScreenController.Screen.MAIN);
            PopupManager.showPopupAvatar(Constantes.MAIN_DIALOGO001);
        }
    }

    @Override
    public void onEvent(TopicSelectedEvent event) {
        mSelectedTopic = event.topic;
        mScreenController.openScreen(ScreenController.Screen.TOPIC);
    }

    @Override
    public void onEvent(TopicTestEvent event) {
        mScreenController.openScreen(ScreenController.Screen.TEST);
    }

    @Override
    public void onEvent(TopicFinishedEvent event) {
        boolean finishedLastTopic = event.lasTopicFinished;
        if(finishedLastTopic){
            PopupManager.showPopupAvatar(Constantes.MAIN_DIALOGO002);
        }
        mScreenController.openScreen(ScreenController.Screen.MAIN);
    }

    public Topic getSelectedTopic() {
        return mSelectedTopic;
    }

    public User getmUser() {
        return mUser;
    }
}
