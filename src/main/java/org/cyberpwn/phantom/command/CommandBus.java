package org.cyberpwn.phantom.command;

import java.lang.annotation.Annotation;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.cyberpwn.phantom.CommandRegistryController;
import org.cyberpwn.phantom.command.CommandFilter.ArgumentRange;
import org.cyberpwn.phantom.command.CommandFilter.ConsoleOnly;
import org.cyberpwn.phantom.command.CommandFilter.OperatorOnly;
import org.cyberpwn.phantom.command.CommandFilter.Permission;
import org.cyberpwn.phantom.command.CommandFilter.Permissions;
import org.cyberpwn.phantom.command.CommandFilter.PlayerOnly;
import org.cyberpwn.phantom.command.CommandFilter.SubCommands;
import org.cyberpwn.phantom.text.MessageBuilder;
import org.cyberpwn.phantom.util.M;

public class CommandBus
{
	private String root;
	private String[] args;
	private CommandSender sender;
	private CommandResult result;
	
	public CommandBus(CommandRegistryController c, String root, String[] args, CommandSender sender)
	{
		this.root = root;
		this.args = args;
		this.sender = sender;
		this.result = CommandResult.NO_HANDLE;

		if(!c.getRegistry().containsKey(root.toLowerCase()))
		{
			return;
		}
		
		for(CommandListener i : c.getRegistry().get(root.toLowerCase()))
		{
			if(hasFilter(i, ArgumentRange.class))
			{
				ArgumentRange ar = (ArgumentRange) getFilter(i, ArgumentRange.class);
				
				if(!M.within(M.min(ar.value()), M.max(ar.value()), args.length))
				{
					i.getMessageInvalidArguments(args.length, M.min(ar.value()), M.max(ar.value()));
					continue;
				}
			}
			
			if(hasFilter(i, ConsoleOnly.class))
			{
				if(sender instanceof Player)
				{
					i.getMessageNotConsole();
					continue;
				}
			}
			
			if(hasFilter(i, PlayerOnly.class))
			{
				if(!(sender instanceof Player))
				{
					i.getMessageNotPlayer();
					continue;
				}
			}
			
			if(hasFilter(i, OperatorOnly.class))
			{
				if(!sender.isOp())
				{
					i.getMessageNoPermission();
					continue;
				}
			}
			
			if(hasFilter(i, Permission.class))
			{
				Permission p = (Permission) getFilter(i, Permission.class);
				
				if(!sender.hasPermission(p.value()))
				{
					i.getMessageNoPermission();
					continue;
				}
			}
			
			if(hasFilter(i, Permissions.class))
			{
				Permissions p = (Permissions) getFilter(i, Permissions.class);
				
				boolean f = false;
				
				for(String j : p.value())
				{
					if(!sender.hasPermission(j))
					{
						f = true;
						break;
					}
				}
				
				if(f)
				{
					i.getMessageNoPermission();
					continue;
				}
			}
			
			if(hasFilter(i, SubCommands.class))
			{
				SubCommands s = (SubCommands) getFilter(i, SubCommands.class);
				
				if(s.value().length > args.length)
				{
					i.getMessageUnknownSubCommand(args.toString());
					continue;
				}
				
				boolean f = false;
				
				for(int j = 0; j < s.value().length; j++)
				{
					if(!s.value()[j].equalsIgnoreCase(args[j]))
					{
						f = true;
						break;
					}
				}
				
				if(f)
				{
					i.getMessageUnknownSubCommand(args.toString());
					continue;
				}
			}
			
			try
			{
				PhantomCommandSender ps = new PhantomSender(sender);
				PhantomCommand cmd = new PhantomCommand(root, args);
				ps.setMessageBuilder(new MessageBuilder(i));
				
				if(i.onCommand(ps, cmd))
				{
					result = CommandResult.HANDLED;
					return;
				}
			}
			
			catch(Exception e)
			{
				result = CommandResult.EXCEPTION;
				e.printStackTrace();
			}
		}
	}
	
	public Annotation getFilter(CommandListener l, Class<? extends Annotation> clazz)
	{
		try
		{
			return l.getClass().getMethod("onCommand", PhantomCommandSender.class, PhantomCommand.class).getDeclaredAnnotation(clazz);
		}
		
		catch(Exception e)
		{
			e.printStackTrace();
		}
		
		return null;
	}
	
	public boolean hasFilter(CommandListener l, Class<? extends Annotation> clazz)
	{
		try
		{
			return l.getClass().getMethod("onCommand", PhantomCommandSender.class, PhantomCommand.class).isAnnotationPresent(clazz);
		}
		
		catch(Exception e)
		{
			e.printStackTrace();
		}
		
		return false;
	}
	
	public CommandResult getResult()
	{
		return result;
	}

	public String getRoot()
	{
		return root;
	}
	
	public void setRoot(String root)
	{
		this.root = root;
	}
	
	public String[] getArgs()
	{
		return args;
	}
	
	public void setArgs(String[] args)
	{
		this.args = args;
	}
	
	public CommandSender getSender()
	{
		return sender;
	}
	
	public void setSender(CommandSender sender)
	{
		this.sender = sender;
	}
}