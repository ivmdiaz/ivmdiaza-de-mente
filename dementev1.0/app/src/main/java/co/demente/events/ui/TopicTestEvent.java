package co.demente.events.ui;

import co.demente.events.AbstractEvent;
import co.demente.events.EventObserver;
import co.demente.negocio.data.model.Topic;

public class TopicTestEvent extends AbstractEvent {

    public static final String TYPE = TopicTestEvent.class.getName();

    @Override
    protected void fire(EventObserver eventObserver) {
        eventObserver.onEvent(this);
    }

    @Override
    public String getType() {
        return TYPE;
    }
}
