/*
 * Created on Mar 23, 2008
 */
package com.googlecode.jbencode.primitive;

import java.io.IOException;
import java.io.InputStream;

import com.googlecode.jbencode.Parser;
import com.googlecode.jbencode.Value;
import com.googlecode.jbencode.ValuePrefix;

/**
 * @author Daniel Spiewak
 */
@ValuePrefix('i')
public class IntegerValue implements Value<Long> {
	private final InputStream is;
	
	private boolean resolved = false;
	
	public IntegerValue(Parser p, InputStream is) {
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
				if (b == '-') {
					negative = true;
				} else if (b == 'e') {
					break;
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
	
	public boolean isResolved() {
		return resolved;
	}
}
