package co.demente.events.ui;

import co.demente.events.AbstractEvent;
import co.demente.events.EventObserver;

/**
 * When the 'back to menu' was pressed.
 */
public class ClosePopupEvent extends AbstractEvent {

	public static final String TYPE = ClosePopupEvent.class.getName();

	@Override
	protected void fire(EventObserver eventObserver) {
		eventObserver.onEvent(this);
	}

	@Override
	public String getType() {
		return TYPE;
	}

}
