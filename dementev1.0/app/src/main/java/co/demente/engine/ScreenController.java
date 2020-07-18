package co.demente.engine;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import co.demente.R;
import co.demente.fragments.LoginFragment;
import co.demente.fragments.MainFragment;
import co.demente.common.Shared;
import co.demente.fragments.TestFragment;
import co.demente.fragments.TopicFragment;

public class ScreenController {

    private static ScreenController mInstance = null;
    private static List<Screen> openedScreens = new ArrayList<Screen>();
    private FragmentManager mFragmentManager;

    private ScreenController() {
    }

    public static ScreenController getInstance() {
        if (mInstance == null) {
            mInstance = new ScreenController();
        }
        return mInstance;
    }

    public static enum Screen {
        LOGIN,
        MAIN,
        TOPIC,
        TEST,
    }

    public static Screen getLastScreen() {
        return openedScreens.get(openedScreens.size() - 1);
    }

    public void openScreen(Screen screen) {
        mFragmentManager = Shared.activity.getSupportFragmentManager();

        //Delete Login view from list screens opened
        if(openedScreens != null && openedScreens.contains(Screen.LOGIN)) {
            Iterator it = openedScreens.iterator();
            while (it.hasNext()){
                Screen objScreen = (Screen) it.next();
                if(Screen.LOGIN.equals(objScreen)){
                    it.remove();
                }
            }
        }

        //
        if (screen == Screen.MAIN && openedScreens.size()> 1
                && openedScreens.get(openedScreens.size() - 1) == Screen.TOPIC) {
            openedScreens.remove(openedScreens.size() - 1);
        }
        if (screen == Screen.MAIN && openedScreens.size()> 1
                && openedScreens.get(openedScreens.size() - 1) == Screen.TEST) {
            openedScreens.remove(openedScreens.size() - 1);
        }


        Fragment fragment = getFragment(screen);
        FragmentTransaction fragmentTransaction = mFragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fragment_container, fragment);
        fragmentTransaction.commit();
        openedScreens.add(screen);
    }

    public boolean onBack() {

        if (openedScreens.size() > 0) {
            Screen screenToRemove = openedScreens.get(openedScreens.size() - 1);
            openedScreens.remove(openedScreens.size() - 1);
            if(screenToRemove.equals(Screen.LOGIN)){
                return false;
            }
            if (openedScreens.size() == 0) {
                return true;
            }
            Screen screen = openedScreens.get(openedScreens.size() - 1);
            openedScreens.remove(openedScreens.size() - 1);
            openScreen(screen);
            return false;
        }
        return true;
    }

    private Fragment getFragment(Screen screen) {
        switch (screen) {
            case LOGIN:
                return new LoginFragment();
            case MAIN:
                return new MainFragment();
            case TOPIC:
                return new TopicFragment();
            case TEST:
                return new TestFragment();
            default:
                break;
        }
        return null;
    }
}
