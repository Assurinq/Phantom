package org.phantomapi.wraith;

import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.phantomapi.Phantom;
import org.phantomapi.construct.Controllable;
import org.phantomapi.lang.GList;

/**
 * Phantom wraith implementation
 * 
 * @author cyberpwn
 */
public class PhantomWraith extends PhantomNPCWrapper implements Wraith, WraithHandled
{
	private GList<WraithHandler> handlers;
	private Controllable c;
	
	/**
	 * Creat a wraith
	 * 
	 * @param name
	 *            the name
	 */
	public PhantomWraith(String name, Controllable base)
	{
		super(name);
		
		c = base;
		handlers = new GList<WraithHandler>();
		Phantom.instance().getWraithController().registerWraith(this);
	}
	
	@Override
	public GList<WraithHandler> getHandlers()
	{
		return handlers;
	}
	
	@Override
	public void registerHandler(WraithHandler handler)
	{
		handlers.add(handler);
	}
	
	@Override
	public void unregisterHandler(WraithHandler handler)
	{
		handlers.remove(handler);
		handler.unregister();
	}
	
	@Override
	public void destroy()
	{
		for(WraithHandler i : handlers.copy())
		{
			i.unregister();
		}
		
		handlers.clear();
		Phantom.instance().getWraithController().unRegisterWraith(this);
	}
	
	@Override
	public void tick()
	{
		for(WraithHandler i : handlers.copy())
		{
			i.onTick();
		}
		
		super.tick();
	}
	
	@Override
	public void onInteract(Player p)
	{
		for(WraithHandler i : handlers.copy())
		{
			if(i instanceof InteractiveHandle)
			{
				((InteractiveHandle) i).onInteract(p);
			}
		}
	}
	
	@Override
	public void onCollide(Entity p)
	{
		for(WraithHandler i : handlers.copy())
		{
			if(i instanceof CollidableHandle)
			{
				((CollidableHandle) i).onCollide(p);
			}
		}
	}
	
	@Override
	public void onDamage(Entity damager, double damage)
	{
		for(WraithHandler i : handlers.copy())
		{
			if(i instanceof DamagableHandle)
			{
				((DamagableHandle) i).onDamage(damager, damage);
			}
		}
	}
	
	@Override
	public Controllable getBase()
	{
		return c;
	}
}
