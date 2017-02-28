package com.pachauri.utilities;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class StringUtilityTest {

	@Test
	public void isCharDuplicateFalse() {
		boolean result = StringUtility.isCharDuplicate("abcdefghij");
		assertEquals(false, result);
	}

	@Test
	public void isCharDuplicateTrue() {
		boolean result = StringUtility.isCharDuplicate("abcdefghijf");
		assertEquals(true, result);
	}

	@Test
	public void isCharDuplicateNull() {
		boolean result = StringUtility.isCharDuplicate(null);
		assertEquals(false, result);
	}

	@Test
	public void isCharDuplicateBlankString() {
		boolean result = StringUtility.isCharDuplicate("  ");
		assertEquals(false, result);
	}

	@Test
	public void isCharDuplicateFalseWithSpaces() {
		boolean result = StringUtility.isCharDuplicate("abcd efgh ij");
		assertEquals(false, result);
	}

	@Test
	public void isCharDuplicateTrueWithSpaces() {
		boolean result = StringUtility.isCharDuplicate("abcd efgh ij fg");
		assertEquals(true, result);
	}
}
