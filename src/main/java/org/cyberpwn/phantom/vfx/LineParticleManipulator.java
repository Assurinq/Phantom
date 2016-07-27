package org.cyberpwn.phantom.vfx;

import org.bukkit.Location;

/**
 * A Particle effect which is a line from a to b. Override play(location) to
 * customize
 * 
 * @author cyberpwn
 *
 */
public class LineParticleManipulator extends ParticleManipulator
{
	/**
	 * Runs play(Location l) across a line between a and b with ppa particles
	 * per block
	 * 
	 * @param a
	 *            the first location
	 * @param b
	 *            the second location
	 * @param ppb
	 *            plays per block. Setting this to 1 will play one particle per
	 *            block. Setting this to 2 will play 2 particles per block (more
	 *            density on the line)
	 */
	public void play(Location a, Location b, Double ppb)
	{
		Double dist = a.distance(b);
		Double jump = dist / ppb;
		Double jumps = dist * ppb;
		Location cursor = a.clone();
		
		for(int i = 0; i < jumps; i++)
		{
			play(cursor);
			cursor = cursor.add(b.subtract(a).toVector().normalize().multiply(jump)).clone();
		}
	}
}