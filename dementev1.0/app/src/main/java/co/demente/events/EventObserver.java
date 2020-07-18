package co.demente.events;

import co.demente.events.ui.BackGameEvent;
import co.demente.events.ui.ClosePopupEvent;
import co.demente.events.ui.StartEvent;
import co.demente.events.ui.TopicFinishedEvent;
import co.demente.events.ui.TopicSelectedEvent;
import co.demente.events.ui.TopicTestEvent;

public interface EventObserver {

    void onEvent(StartEvent event);

    void onEvent(BackGameEvent event);

    void onEvent(TopicSelectedEvent event);

    void onEvent(ClosePopupEvent event);

    void onEvent(TopicTestEvent event);

    void onEvent(TopicFinishedEvent event);


}
