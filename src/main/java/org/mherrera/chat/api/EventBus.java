/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.mherrera.chat.api;

import org.mherrera.chat.interfaces.Dispatcher;
import org.mherrera.chat.interfaces.Publisher;
import org.mherrera.chat.interfaces.Listener;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import org.mherrera.chat.interfaces.Bus;

/**
 *
 * @author mary_herrera
 */
public class EventBus {
    private final Dispatcher dispatcher;
    private final ExecutorService executor;
    private final CopyOnWriteArraySet<Subscriber> subscribers = new CopyOnWriteArraySet<>();
    private Boolean isEmpty = true;
    
    public EventBus(ExecutorService executor) {
        this.dispatcher = (Dispatcher) new BroadcastDispatcher();
        this.executor = executor;
    }

    Executor getExecutor() {
        return executor;
    }

    public Boolean getIsEmpty() {
        return isEmpty;
    }

    public void setIsEmpty(Boolean isEmpty){
        this.isEmpty = isEmpty;
    }
    
    public boolean isEmpty() {
        return getIsEmpty();
    }

    /**
     * Adding events to the queue
     * @param listener 
     */
    public void subscribe(Listener listener) {
        subscribers.add(new Subscriber(this.getExecutor(), listener));
        if (listener instanceof Bus) {
            ((Bus) listener).onSubscribe(this);
        }
        setIsEmpty(false);
    }

    /**
     * Dropping events from the queue
     * @param listener 
     */
    public void unsubscribe(Listener listener) {
        Set<Subscriber> subscribersToRemove = subscribers.stream()
                        .filter(subscriber -> listener.equals(subscriber.getListener()))
                        .collect(Collectors.toSet());

        subscribers.removeAll(subscribersToRemove);

        setIsEmpty(subscribers.isEmpty());
        if (listener instanceof Bus) {
            ((Bus) listener).onUnsubscribe(this);
        }
    }

    public void post(Publisher publisher) {
        post(publisher.getMessage(), publisher.getExcludes());
    }

    /**
     * Posting events belonging to the user
     * @param message
     * @param excludes 
     */
    private void post(String message, Predicate<Listener> excludes) {
        
        Predicate<Listener> predicate = excludes == null ? s -> true : excludes.negate();
        Set<Subscriber> subscribSet = this.subscribers.stream()
                .filter(subscriber -> predicate.test(subscriber.getListener()))
                .collect(Collectors.toSet());

        dispatcher.dispatch(message, subscribSet.iterator());
    }

}
