package hu.bme.mit.inf.irf.chat.console;

import hu.bme.mit.inf.irf.chat.data.Message;
import hu.bme.mit.inf.irf.chat.util.Filterable;

import java.io.PrintStream;

public class ChatOutputConsole extends Filterable {

    private final PrintStream console;

    public ChatOutputConsole(final PrintStream console) {
        this.console = console;
    }

    public void printMessage(final Message message) {
        StringBuilder builder = new StringBuilder(message.getAuthor());
        builder.append(" : ");
        builder.append(filterText(message.getText()));

        console.println(builder.toString());
    }
}
