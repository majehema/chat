/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.mherrera.chat.interfaces;

import java.util.Iterator;
import org.mherrera.chat.api.Subscriber;

/**
 *
 * @author mary_herrera
 */
public interface Dispatcher {
        /**
     * Dispatching event to subscribers
     * @param event event
     * @param subscribers subscribers
     */
    public void dispatch(String event, Iterator<Subscriber> subscribers);
}
