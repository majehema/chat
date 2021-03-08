/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.mherrera.chat;

//import com.google.common.eventbus.EventBus;
import static java.lang.System.exit;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Executors;
import org.mherrera.chat.api.EventBus;

/**
 *
 * @author mary_herrera
 */
public class Main {

    private static EventBus bus;
    private static final Map<String, Player> CHAT_MEMBERS = new HashMap<>();
    private static String initiator;
    private static String message;

    public static void main(String[] args) {

        try {
            setUpVariables();
            run();

        } catch (Exception e) {
            System.out.println(e);
        }
    }

    /**
     * Setting variables 
     */
    private static void setUpVariables(){
        message = "Hello!";
        initiator = "Initiator";
        CHAT_MEMBERS.put(initiator, new Player(initiator));
        CHAT_MEMBERS.put("2nd player", new Player("2nd player"));

        bus = new EventBus(Executors.newFixedThreadPool(4));
        
        CHAT_MEMBERS.values().forEach(player -> {
            bus.subscribe(player);
        });
    }

     /**
     * Sending the first message to start the chat and finishing when there are no more messages to process
     */
    private static void run() {

        CHAT_MEMBERS.get(initiator).send(message);

        while (true) {
            if (bus.isEmpty()) {
                System.out.println("Successfully completed");
                exit(0);
            }

            try {
                Thread.sleep(1000);
            } catch (InterruptedException exception) {
                System.out.println(exception);
            }
        }

    }

}
