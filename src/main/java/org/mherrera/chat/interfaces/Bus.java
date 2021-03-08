/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.mherrera.chat.interfaces;

import org.mherrera.chat.api.EventBus;

/**
 *
 * @author mary_herrera
 */
public interface Bus {

    /**
     * Setting the bus for this object.
     *
     * @param bus event bus
     */
    public void onSubscribe(EventBus bus);

    /**
     * Removing the bus from this object.
     * @param bus  Removed bus
     */
    public void onUnsubscribe(EventBus bus);
}
