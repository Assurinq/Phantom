package org.phantomapi.wraith;

import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerTeleportEvent.TeleportCause;
import org.bukkit.inventory.ItemStack;
import org.phantomapi.Phantom;
import org.phantomapi.lang.GList;
import org.phantomapi.text.MessageBuilder;
import org.phantomapi.text.TagProvider;
import org.phantomapi.util.C;
import org.phantomapi.world.Area;
import net.citizensnpcs.api.CitizensAPI;
import net.citizensnpcs.api.npc.NPC;

public class PhantomNPCWrapper implements NPCWrapper, TagProvider
{
	private NPC npc;
	private String chatName;
	private String chatHover;
	private MessageBuilder mb;
	private WraithTarget target;
	
	public PhantomNPCWrapper(String name)
	{
		this.npc = null;
		this.npc = CitizensAPI.getNPCRegistry().createNPC(EntityType.PLAYER, name);
		this.chatName = npc.getName() + ": ";
		this.chatHover = "Hi, I'm " + npc.getName();
		this.mb = new MessageBuilder(this);
		this.target = null;
	}
	
	public void spawn(Location location)
	{
		npc.spawn(location);
	}
	
	public void despawn()
	{
		npc.despawn();
	}
	
	public boolean isSpawned()
	{
		return npc.isSpawned();
	}
	
	public Location getLocation()
	{
		return npc.getStoredLocation();
	}
	
	public void teleport(Location location)
	{
		if(isSpawned())
		{
			npc.teleport(location, TeleportCause.PLUGIN);
		}
	}
	
	public Entity getEntity()
	{
		if(isSpawned())
		{
			return npc.getEntity();
		}
		
		return null;
	}
	
	public int getEntityId()
	{
		if(isSpawned())
		{
			return getEntity().getEntityId();
		}
		
		return -1;
	}
	
	public void target(Location location)
	{
		if(isSpawned())
		{
			target = new WraithTarget(location);
		}
	}
	
	public void target(Entity entity)
	{
		if(isSpawned())
		{
			target = new WraithTarget(entity);
		}
	}
	
	public void look(Location location)
	{
		if(isSpawned())
		{
			npc.faceLocation(location);
		}
	}
	
	public void setEquipment(WraithEquipment slot, ItemStack item)
	{
		if(isSpawned())
		{
			Player p = (Player) getEntity();
			
			switch(slot)
			{
				case CHEST:
					p.getInventory().setChestplate(item);
				case FEET:
					p.getInventory().setBoots(item);
				case HAND:
					p.getInventory().setItemInHand(item);
				case HEAD:
					p.getInventory().setHelmet(item);
				case LEGS:
					p.getInventory().setLeggings(item);
				default:
					break;
			}
		}
	}
	
	public ItemStack getEquipment(WraithEquipment slot)
	{
		if(isSpawned())
		{
			Player p = (Player) getEntity();
			
			switch(slot)
			{
				case CHEST:
					return p.getInventory().getChestplate();
				case FEET:
					return p.getInventory().getBoots();
				case HAND:
					return p.getInventory().getItemInHand();
				case HEAD:
					return p.getInventory().getHelmet();
				case LEGS:
					return p.getInventory().getLeggings();
				default:
					break;
			}
		}
		
		return null;
	}
	
	public String getName()
	{
		return npc.getName();
	}
	
	public void setName(String name)
	{
		npc.setName(name);
		chatName = npc.getName() + ": ";
		chatHover = "Hi, I'm " + npc.getName();
		mb = new MessageBuilder(this);
	}
	
	@Override
	public void setSneaking(boolean sneaking)
	{
		getPlayer().setSneaking(sneaking);
	}
	
	@Override
	public boolean isSneaking()
	{
		return getPlayer().isSneaking();
	}
	
	@Override
	public void setSprinting(boolean sprinting)
	{
		getPlayer().setSprinting(sprinting);
	}
	
	@Override
	public boolean isSprinting()
	{
		return getPlayer().isSprinting();
	}
	
	@Override
	public void allowFlight(boolean flightFinding)
	{
		npc.setFlyable(flightFinding);
	}
	
	@Override
	public Player getPlayer()
	{
		return (Player) getEntity();
	}
	
	@Override
	public boolean isAllowedFlight()
	{
		return npc.isFlyable();
	}
	
	@Override
	public void setProtected(boolean protect)
	{
		npc.setProtected(protect);
	}
	
	@Override
	public boolean isProtected()
	{
		return npc.isProtected();
	}
	
	@Override
	public boolean hasTarget()
	{
		return npc.getNavigator().isNavigating() || target != null;
	}
	
	@Override
	public void clearTarget()
	{
		npc.getNavigator().cancelNavigation();
		target = null;
	}
	
	@Override
	public void say(String message)
	{
		for(Player i : Phantom.instance().onlinePlayers())
		{
			say(message, i);
		}
	}
	
	@Override
	public void say(String message, double radius)
	{
		Area a = new Area(getLocation(), radius);
		
		for(Player i : a.getNearbyPlayers())
		{
			say(message, i);
		}
	}
	
	@Override
	public void say(String message, Player p)
	{
		if(isSpawned())
		{
			mb.message(p, C.RESET + message);
		}
	}
	
	@Override
	public void say(String message, Player... players)
	{
		for(Player i : players)
		{
			say(message, i);
		}
	}
	
	@Override
	public void say(String message, GList<Player> players)
	{
		say(message, players.toArray(new Player[players.size()]));
	}
	
	@Override
	public String getChatTag()
	{
		return chatName;
	}
	
	@Override
	public String getChatTagHover()
	{
		return chatHover;
	}
	
	@Override
	public String getChatName()
	{
		return getChatTag();
	}
	
	@Override
	public String getChatHover()
	{
		return getChatHover();
	}
	
	@Override
	public void setChatName(String name)
	{
		chatName = name;
	}
	
	@Override
	public void setChatHover(String hover)
	{
		chatHover = hover;
	}
	
	@Override
	public WraithTarget getTarget()
	{
		return target;
	}
	
	@Override
	public void updateTarget()
	{
		if(hasTarget())
		{
			npc.getNavigator().setTarget(getTarget().getTarget());
		}
	}
}
