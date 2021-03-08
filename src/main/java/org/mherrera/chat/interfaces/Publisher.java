/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.mherrera.chat.interfaces;

import java.util.function.Predicate;

/**
 *
 * @author mary_herrera
 */
public interface Publisher {

    /**
     * Creating text messages
     * @return String
     */
    String getMessage();

    /**
     * Exclude items (not to consider the messages of the other player)
     * @return predicate
     */
    Predicate<Listener> getExcludes();
}