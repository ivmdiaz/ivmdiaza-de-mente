package co.demente.events.ui;

import co.demente.events.AbstractEvent;
import co.demente.events.EventObserver;
import co.demente.negocio.data.model.Topic;
import co.demente.negocio.data.model.TopicUserProgress;

public class TopicSelectedEvent extends AbstractEvent {

    public static final String TYPE = TopicSelectedEvent.class.getName();

    public final Topic topic;

    public TopicSelectedEvent(Topic topic) {
        this.topic = topic;
    }

    @Override
    protected void fire(EventObserver eventObserver) {
        eventObserver.onEvent(this);
    }

    @Override
    public String getType() {
        return TYPE;
    }
}
