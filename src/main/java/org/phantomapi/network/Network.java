package org.phantomapi.network;

import org.phantomapi.lang.GList;

/**
 * Represents a bungeecord network
 * 
 * @author cyberpwn
 */
public interface Network
{
	/**
	 * Get servers on the network
	 * 
	 * @return the servers
	 */
	public GList<NetworkedServer> getServers();
	
	/**
	 * Get remote servers on the network
	 * 
	 * @return the remote servers
	 */
	public GList<NetworkedServer> getRemoteServers();
	
	/**
	 * Get lobby servers
	 * 
	 * @return the lobby servers
	 */
	public GList<NetworkedServer> getLobbyServers();
	
	/**
	 * Get the list of players on all servers
	 * 
	 * @return the list of players
	 */
	public GList<String> getPlayers();
	
	/**
	 * Get the Local server on this network (This server)
	 * 
	 * @return the local server instance
	 */
	public NetworkedServer getLocalServer();
	
	/**
	 * Get the remote server under the bungeecord name
	 * 
	 * @param name
	 *            the name needed for this server
	 * @return the remote server or null if cannot be found
	 */
	public NetworkedServer getRemoteServer(String name);
}
