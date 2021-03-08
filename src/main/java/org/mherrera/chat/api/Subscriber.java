/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.mherrera.chat.api;

import org.mherrera.chat.interfaces.Listener;
import java.util.concurrent.Executor;

/**
 *
 * @author mary_herrera
 */
public class Subscriber {
    
    private final Listener listener;
    private final Executor executor;

    Subscriber(Executor executor, Listener listener) {
        this.listener = listener;
        this.executor = executor;
    }

    final void dispatchEvent(String event) {
        synchronized (this) {
            executor.execute( () -> {
                listener.onMessage(event);
            });
        }
    }

    Listener getListener() {
        return listener;
    }

}
