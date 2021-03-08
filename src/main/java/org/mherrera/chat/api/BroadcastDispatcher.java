package org.mherrera.chat.api;

import org.mherrera.chat.interfaces.Dispatcher;
import java.util.ArrayDeque;
import java.util.Iterator;
import java.util.Queue;

/**
 *
 * @author mary_herrera
 */
class BroadcastDispatcher implements Dispatcher {
    private final ThreadLocal<Queue<Event>> queue = ThreadLocal.withInitial(ArrayDeque::new);
    private final ThreadLocal<Boolean> dispatching = ThreadLocal.withInitial(() -> false);
    
    @Override
    public void dispatch(String event, Iterator<Subscriber> subscribers) {
        
        Queue<Event> queueForThread = queue.get();
        queueForThread.offer(new Event(event, subscribers));

        if (!dispatching.get()) {
            dispatching.set(true);
            try {
                Event nextEvent;
                
                while ((nextEvent = queueForThread.poll()) != null) {
                    while(nextEvent.subscribers.hasNext()){
                        nextEvent.subscribers.next().dispatchEvent(nextEvent.message);
                    }
                }
            } finally {
                dispatching.remove();
                queue.remove();
            }
        }
    }

    /**
     * Class to store the next event in the queue
     */
    private static final class Event {
        private final String message;
        private final Iterator<Subscriber> subscribers;

        private Event(String message, Iterator<Subscriber> subscribers) {
            this.message = message;
            this.subscribers = subscribers;
        }
    }
}
