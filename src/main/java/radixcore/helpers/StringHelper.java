package radixcore.helpers;

public final class StringHelper
{
	/**
	 * @param 	string	The string to be modified.
	 * @return	The provided string with the first letter capitalized.
	 */
	public static String upperFirstLetter(String string)
	{
		return string.substring(0, 1).toUpperCase() + string.subSequence(1, string.length());
	}
	
	private StringHelper()
	{
	}
}
