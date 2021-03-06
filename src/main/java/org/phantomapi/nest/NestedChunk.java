package org.phantomapi.nest;

import org.bukkit.block.Block;
import org.phantomapi.lang.GChunk;
import org.phantomapi.lang.GLocation;
import org.phantomapi.lang.GMap;

/**
 * Nested chunk
 * 
 * @author cyberpwn
 */
public class NestedChunk extends NestedObject
{
	private static final long serialVersionUID = 1L;
	private final GMap<GLocation, NestedBlock> blocks;
	private final GChunk chunk;
	
	/**
	 * Create a new nested chunk
	 * 
	 * @param chunk
	 *            the gchunk
	 */
	public NestedChunk(GChunk chunk)
	{
		this.blocks = new GMap<GLocation, NestedBlock>();
		this.chunk = chunk;
	}
	
	public static long getSerialversionuid()
	{
		return serialVersionUID;
	}
	
	public GMap<GLocation, NestedBlock> getBlocks()
	{
		return blocks;
	}
	
	public GChunk getChunk()
	{
		return chunk;
	}
	
	/**
	 * Get the nested block
	 * 
	 * @param block
	 *            the block
	 * @return the nested block
	 */
	public NestedBlock getBlock(Block block)
	{
		GLocation l = new GLocation(block.getLocation());
		
		if(!blocks.containsKey(l))
		{
			blocks.put(l, new NestedBlock(l));
		}
		
		return blocks.get(l);
	}
	
	public int size()
	{
		int k = 0;
		
		for(GLocation i : blocks.k())
		{
			k += blocks.get(i).size();
		}
		
		return super.size() + k;
	}
	
	@Override
	public int hashCode()
	{
		final int prime = 31;
		int result = 1;
		result = prime * result + ((blocks == null) ? 0 : blocks.hashCode());
		result = prime * result + ((chunk == null) ? 0 : chunk.hashCode());
		return result;
	}
	
	@Override
	public boolean equals(Object obj)
	{
		if(this == obj)
			return true;
		if(obj == null)
			return false;
		if(getClass() != obj.getClass())
			return false;
		NestedChunk other = (NestedChunk) obj;
		if(blocks == null)
		{
			if(other.blocks != null)
				return false;
		}
		else if(!blocks.equals(other.blocks))
			return false;
		if(chunk == null)
		{
			if(other.chunk != null)
				return false;
		}
		else if(!chunk.equals(other.chunk))
			return false;
		return true;
	}
}
