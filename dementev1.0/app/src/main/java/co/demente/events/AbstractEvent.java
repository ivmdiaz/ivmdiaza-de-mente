package co.demente.events;

public abstract class AbstractEvent implements Event {

	protected abstract void fire(EventObserver eventObserver);


}
