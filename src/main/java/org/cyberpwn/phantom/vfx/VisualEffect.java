package org.cyberpwn.phantom.vfx;

import org.bukkit.Location;
import org.cyberpwn.phantom.lang.GList;

/**
 * A visual effect which may be an actual effect, or a super-effect (multiple
 * sub effects)
 * 
 * @author cyberpwn
 *
 */
public interface VisualEffect
{
	/**
	 * Get all effects in this visual effect
	 * 
	 * @return the effects
	 */
	public GList<VisualEffect> getEffects();
	
	/**
	 * Play this effect. Will loop through all visual effects and play them, or
	 * play itself
	 * 
	 * @param l
	 *            the location to play the effect
	 */
	public void play(Location l);
	
	/**
	 * Add a visual effect to this effect. Nothing will happen unless this is a
	 * System effect
	 * 
	 * @param e
	 *            the effect
	 */
	public void addEffect(VisualEffect e);
}
