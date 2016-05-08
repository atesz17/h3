package hu.bme.mit.inf.irf.chat.application;

/**
 * This class implements ChatApplicationControlMBean interface.
 * 
 * @author Papai Attila
 *
 */

public class ChatApplicationControl implements ChatApplicationControlMBean {

	ChatApplication app = null;
	
	public ChatApplicationControl(ChatApplication app) {
		this.app = app;
	}
	
	@Override
	public int getPort() {
		return app.port;
	}

	@Override
	public String getAddress() {
		return app.connectionConfiguration.getFullAddress();
	}

	@Override
	public String getTopic() {
		return app.connectionConfiguration.getTopic();
	}

	@Override
	public boolean filterWord(String word) {
		app.subscriber.getChatOutput().addForbiddenWord(word);
		// since this one cannot call separately these two function, the return value will be the same
		return app.cons.addForbiddenWord(word);
	}

	@Override
	public String getAuthor() {
		return app.cons.getAuthor();
	}

	@Override
	public boolean getBlocked() {
		return app.cons.getBlocked();
	}

	@Override
	public void setBlocked(boolean value) {
		app.cons.setBlocked(value);
	}

	@Override
	public boolean unfilterWord(String word) {
		app.subscriber.getChatOutput().removeForbiddenWord(word);
		return app.cons.removeForbiddenWord(word);
	}

	@Override
	public void clearForbiddenWordList() {
		app.subscriber.getChatOutput().eraseList();
		app.cons.eraseList();
	}

}
