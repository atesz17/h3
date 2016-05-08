package hu.bme.mit.inf.irf.chat.console;

import hu.bme.mit.inf.irf.chat.data.Message;
import hu.bme.mit.inf.irf.chat.network.MQTTSubscriberCallback;
import hu.bme.mit.inf.irf.chat.util.Filterable;

import java.util.Scanner;

public class ChatInputConsole extends Filterable implements Runnable {

    private final MQTTSubscriberCallback mqttConnection;
    private final String author;
    private boolean blocked;

    public ChatInputConsole(final MQTTSubscriberCallback mqttConnection,
            final String author) {
        this.mqttConnection = mqttConnection;
        this.author = author;
        blocked = false;
    }

    @Override
    public void run() {
        StringBuilder builder = new StringBuilder("Welcome ");
        builder.append(author);
        builder.append("! Let's start chatting!");

        System.out.println(builder.toString());

        Scanner scanner = new Scanner(System.in);

        while (!Thread.currentThread().isInterrupted()) {
        	if (!blocked)	{
	            String text = scanner.nextLine();
	            
	            // filter word
            	text = filterText(text);
	
	            Message message = new Message(author, text);
	            mqttConnection.publishMessage(message);
        	} else	{
        		// we throw messages away which were sent when user was blocked
        		scanner.nextLine();
        	}
        }
    }
    
    /**
     * 
     * @return True, if user is blocked
     */
    public boolean getBlocked()	{
    	return blocked;
    }
    
    /**
     * 
     * @param value - new blocked value
     */
    public void setBlocked(boolean value)	{
    	blocked = value;
    }
    
    /**
     * 
     * @return author's name
     */
    public String getAuthor()	{
    	return author;
    }

}
