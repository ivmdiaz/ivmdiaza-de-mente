package co.demente.events.ui;

import co.demente.events.AbstractEvent;
import co.demente.events.EventObserver;

public class TopicFinishedEvent extends AbstractEvent {

    public boolean lasTopicFinished = false;

    public TopicFinishedEvent(boolean lasTopicFinished){
        this.lasTopicFinished = lasTopicFinished;
    }

    public static final String TYPE = TopicFinishedEvent.class.getName();

    @Override
    protected void fire(EventObserver eventObserver) {
        eventObserver.onEvent(this);
    }

    @Override
    public String getType() {
        return TYPE;
    }
}
