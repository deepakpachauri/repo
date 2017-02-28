package com.pachauri.utilities;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class StringUtilityTest {
	
	@Test
	public void isCharDuplicateFalse(){
		boolean result = StringUtility.isCharDuplicate("abcdefghij");
		assertEquals(false, result);
	}

}
