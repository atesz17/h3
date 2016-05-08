package hu.bme.mit.inf.irf.chat.application;


/**
 * Interface for Managament Services.
 * 
 * @author Papai Attila
 *
 */
public interface ChatApplicationControlMBean {

	public int getPort();
	public String getAddress();
	public String getTopic();
	public String getAuthor();
	public boolean getBlocked();
	public void setBlocked(boolean value);
	public boolean filterWord(String word);
	public boolean unfilterWord(String word);
	public void clearForbiddenWordList();
}
