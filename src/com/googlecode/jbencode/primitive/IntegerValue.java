/*
 * Created on Mar 23, 2008
 */
package com.googlecode.jbencode.primitive;

import java.io.IOException;
import java.io.InputStream;

import com.googlecode.jbencode.Parser;
import com.googlecode.jbencode.Prefix;
import com.googlecode.jbencode.Value;

/**
 * @author Daniel Spiewak
 */
@Prefix('i')
public class IntegerValue implements Value<Long> {
	private final InputStream is;
	
	private boolean resolved = false;
	
	IntegerValue(Parser p, InputStream is) {
		this.is = is;
	}
	
	public Long resolve() throws IOException {
		if (resolved) {
			throw new IOException("Value already resolved");
		}
		resolved = true;
		
		boolean negative = false;
		long value = 0;
		
		int b = 0;
		while ((b = is.read()) >= 0) {
			int digit = b - '0';
			
			if (digit < 0 || digit > 9) {
				if (digit == '-') {
					negative = true;
				} else {
					throw new IOException("Unexpected character in integer value: " + Character.forDigit(b, 10));
				}
			} else {
				value = (value * 10) + digit;
			}
		}
		
		if (negative) {
			value *= -1;
		}
		
		return value;
	}
}
