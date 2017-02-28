package com.pachauri.utilities;

import java.util.HashSet;
import java.util.Set;

/**
 * 
 * @author Deepak Pachauri
 * @email deepakpachauri@gmail.com
 *
 */
public final class StringUtility {
	
	/**
	 * 
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
