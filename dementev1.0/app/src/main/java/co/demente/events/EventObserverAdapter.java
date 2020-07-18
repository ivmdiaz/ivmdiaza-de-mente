package co.demente.events;

import co.demente.events.ui.BackGameEvent;
import co.demente.events.ui.ClosePopupEvent;
import co.demente.events.ui.StartEvent;
import co.demente.events.ui.TopicFinishedEvent;
import co.demente.events.ui.TopicSelectedEvent;
import co.demente.events.ui.TopicTestEvent;

public class EventObserverAdapter implements EventObserver {

    @Override
    public void onEvent(StartEvent event) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void onEvent(BackGameEvent event) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void onEvent(TopicSelectedEvent event) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void onEvent(ClosePopupEvent event) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void onEvent(TopicTestEvent event)  {
        throw new UnsupportedOperationException();
    }

    @Override
    public void onEvent(TopicFinishedEvent event)   {
        throw new UnsupportedOperationException();
    }
}
