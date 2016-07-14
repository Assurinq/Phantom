package org.cyberpwn.phantom.sfx;

import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;
import org.cyberpwn.phantom.lang.GList;
import org.cyberpwn.phantom.util.Average;

public class Audio implements Audible
{
	private GList<Audible> audibles;
	
	public Audio()
	{
		this.audibles = new GList<Audible>();
	}
	
	
	public Audio(GList<Audible> audibles)
	{
		this.audibles = audibles;
	}
	
	public Audible clone()
	{
		return new Audio(audibles);
	}
	
	public void add(Audible audible)
	{
		audibles.add(audible);
	}
	
	@Override
	public void play(Player p, Location l)
	{
		for(Audible i : audibles)
		{
			i.play(p, l);
		}
	}
	
	@Override
	public void play(Player p)
	{
		for(Audible i : audibles)
		{
			i.play(p);
		}
	}
	
	@Override
	public void play(Location l)
	{
		for(Audible i : audibles)
		{
			i.play(l);
		}
	}
	
	@Override
	public void play(Player p, Vector v)
	{
		for(Audible i : audibles)
		{
			i.play(p, v);
		}
	}
	
	@Override
	public Float getVolume()
	{
		Average a = new Average(-1);
		
		for(Audible i : audibles)
		{
			a.put(i.getVolume());
		}
		
		return (float) a.getAverage();
	}
	
	@Override
	public void setVolume(Float volume)
	{
		for(Audible i : audibles)
		{
			i.setVolume(volume);
		}
	}
	
	@Override
	public Float getPitch()
	{
		Average a = new Average(-1);
		
		for(Audible i : audibles)
		{
			a.put(i.getPitch());
		}
		
		return (float) a.getAverage();
	}
	
	@Override
	public void setPitch(Float pitch)
	{
		for(Audible i : audibles)
		{
			i.setPitch(pitch);
		}
	}
}