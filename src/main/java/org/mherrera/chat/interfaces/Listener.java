/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.mherrera.chat.interfaces;

/**
 *
 * @author mary_herrera
 */
public interface Listener {

    /**
     * 
     * @param message payload
     */
    void onMessage(String message);
}