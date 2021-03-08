package org.mherrera.chat;

import org.mherrera.chat.api.EventBus;

import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Predicate;
import org.mherrera.chat.interfaces.Listener;
import org.mherrera.chat.interfaces.Publisher;
import org.mherrera.chat.interfaces.Bus;

/**
 *
 * @author mary_herrera
 */
public class Player implements Listener, Bus {
    public final String name;
    private short sendCounter = 0;
    private short receiveCounter = 0;
    private final AtomicReference<EventBus> busPlayer = new AtomicReference<>();
    
    public Player(String nicName) {
        this.name = nicName;
    }

    @Override
    public void onSubscribe(EventBus bus) {
        this.busPlayer.set(bus);
    }

    @Override
    public void onUnsubscribe(EventBus bus) {
        this.busPlayer.set(null);
    }
    
    /**
     * Sending a message to the other player
     * @param message 
     */
    public void send(String message) {
        EventBus eventBus;
        eventBus = busPlayer.get();
        
        if (eventBus == null) {
            return;
        }
        
        sendCounter++;
        
        if (sendCounter>=10 && receiveCounter>=10) {
            eventBus.unsubscribe(this);
        }

        eventBus.post(new Publisher() {
            @Override
            public String getMessage() {
                String newMessage = message + "," + sendCounter;
                System.out.println(name + " send message: " + message);
                return newMessage;
            }

            @Override
            public Predicate<Listener> getExcludes() {
                return (listener) -> listener.equals(Player.this);
            }
        });

    }

    @Override
    public void onMessage(String message) {
        System.out.println(name + " received message: " + message);
        receiveCounter++;
        send(message);
    }

}
