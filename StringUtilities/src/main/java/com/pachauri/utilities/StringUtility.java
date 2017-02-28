package com.pachauri.utilities;

import java.util.HashSet;
import java.util.Set;

/**
 * This class provides the String Utility methods.
 * @author Deepak Pachauri
 * @email deepakpachauri@gmail.com
 *
 */
public final class StringUtility {
	
	/**
	 * This method finds whether there is any character in string which repeats. It will igore the whitespaces.
	 * @param str
	 * @return boolean
	 */
	public static boolean isCharDuplicate(String str){
		if(str==null){
			return false;
		}
		Set<Character> set = new HashSet<Character>();
		for(Character c : str.toCharArray()){
			if(!Character.isWhitespace(c) && !set.add(c)){
				return true;
			}
		}
		return false;
	}

}
