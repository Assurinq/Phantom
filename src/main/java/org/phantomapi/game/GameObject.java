package org.phantomapi.game;

import org.phantomapi.clust.DataEntity;

/**
 * Represents a game object
 * 
 * @author cyberpwn
 */
public interface GameObject extends DataEntity
{
	/**
	 * Get the type of game object
	 * 
	 * @return the game type
	 */
	public String getType();
	
	/**
	 * Get the id of this object
	 * 
	 * @return the game object id
	 */
	public String getId();
	
	/**
	 * Get the game
	 * 
	 * @return the game
	 */
	public Game getGame();
	
	/**
	 * Destroy this game object and remove the reference in the game state
	 */
	public void destroy();
}
