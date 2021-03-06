package org.phantomapi.text;

import org.apache.commons.lang.WordUtils;
import org.phantomapi.lang.GList;
import org.phantomapi.util.C;
import org.phantomapi.util.F;

/**
 * Textual Utilities
 * 
 * @author cyberpwn
 */
public class TXT
{
	/**
	 * Repeat a string
	 * 
	 * @param str
	 *            the string
	 * @param len
	 *            the amount of repeats
	 * @return the string
	 */
	public static String repeat(String str, int len)
	{
		return F.repeat(str, len);
	}
	
	/**
	 * Wrap the text into lines
	 * 
	 * @param str
	 *            the string
	 * @param len
	 *            the length
	 * @return the strings
	 */
	public static GList<String> wrap(String str, int len)
	{
		String format = C.getLastColors(str);
		GList<String> lines = new GList<String>();
		
		for(String i : WordUtils.wrap(str, len).split("\n"))
		{
			lines.add(format + C.getLastColors(i) + i.trim());
		}
		
		return lines;
	}
	
	/**
	 * Create a line
	 * 
	 * @param color
	 *            the color
	 * @param len
	 *            the length
	 * @return the line
	 */
	public static String line(C color, int len)
	{
		return color + "" + C.STRIKETHROUGH + repeat(" ", len);
	}
	
	/**
	 * Create an underline
	 * 
	 * @param color
	 *            the color
	 * @param len
	 *            the length
	 * @return the line
	 */
	public static String underline(C color, int len)
	{
		return color + "" + C.STRIKETHROUGH + repeat(" ", len);
	}
	
	/**
	 * Get a fancy underline
	 * 
	 * @param cc
	 *            the color
	 * @param len
	 *            the length of the line
	 * @param percent
	 *            the progress of the line
	 * @param l
	 *            the left text
	 * @param f
	 *            the centered text
	 * @param r
	 *            the right text
	 * @return the line
	 */
	public static String getLine(C cc, int len, double percent, String l, String f, String r)
	{
		String k = cc + "" + C.UNDERLINE + l;
		len = len < l.length() + r.length() + f.length() ? l.length() + r.length() + f.length() + 6 : len;
		int a = len - (l.length() + r.length() + f.length());
		int b = (int) ((double) a * (double) percent);
		int c = len - b;
		return (percent == 0.0 ? ((k + C.DARK_GRAY + C.UNDERLINE + F.repeat(" ", c) + C.DARK_GRAY + C.UNDERLINE + r)) : (k + F.repeat(" ", b) + (percent == 1.0 ? r : (f + C.DARK_GRAY + C.UNDERLINE + F.repeat(" ", c) + C.DARK_GRAY + C.UNDERLINE + r))));
	}
}
