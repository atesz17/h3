package hu.bme.mit.inf.irf.chat.util;

import java.util.HashSet;
import java.util.Set;

/**
 * This class represents anything, that can be filtered
 * according to list of forbidden words. 
 * 
 * @author Papai Attila
 *
 */
public class Filterable {

	// container for forbidden words
	private Set<String> filteredWords;
	
	public Filterable()	{
		filteredWords = new HashSet<String>();
	}
	
	/**
	 * Filters the given text according to the forbidden words
	 * 
	 * @param text
	 * @return
	 */
	public String filterText(String text)	{
		if (filteredWords.isEmpty())	{
			return text;
		}
		String newText = text;
    	for (String word : filteredWords)	{
    		newText = newText.replaceAll(word, "****");
    	}
    	return newText;
	}
	
	/**
	 * Appends the forbidden word list with the given word
	 * 
	 * @param word - word to be filtered
	 * @return - True, if the word was not already in the list
	 */
	public boolean addForbiddenWord(String word)	{
		return filteredWords.add(word);
	}
	
	/**
	 * Removes the word from the forbidden word list
	 * 
	 * @param word - word to be removed
	 * @return - True, if the word was in the list
	 */
	public boolean removeForbiddenWord(String word)	{
		return filteredWords.remove(word);
	}
	
	/**
	 * Clears the forbidden word list
	 */
	public void eraseList()	{
		filteredWords.clear();
	}
}
