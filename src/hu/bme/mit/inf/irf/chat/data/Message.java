package hu.bme.mit.inf.irf.chat.data;

import com.google.gson.Gson;

public class Message {

    private final String author;
    private final String text;

    public Message(final String author, final String text) {
        this.author = author;
        this.text = text;
    }

    public String getAuthor() {
        return author;
    }

    public String getText() {
        return text;
    }

    public String toJson() {
        return new Gson().toJson(this);
    }
}
