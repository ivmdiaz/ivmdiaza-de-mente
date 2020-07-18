package co.demente.events.ui;

import co.demente.events.AbstractEvent;
import co.demente.events.EventObserver;
import co.demente.negocio.data.model.User;

/**
 * When the 'back to menu' was pressed.
 */
public class StartEvent extends AbstractEvent {

	public static final String TYPE = StartEvent.class.getName();
	public final User user;

	public StartEvent(User user) {
		this.user = user;
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
