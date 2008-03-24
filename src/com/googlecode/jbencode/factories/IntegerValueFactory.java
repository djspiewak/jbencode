/*
 * Created on Mar 23, 2008
 */
package com.googlecode.jbencode.factories;

import java.io.InputStream;

import com.googlecode.jbencode.primitive.IntegerValue;

/**
 * @author Daniel Spiewak
 */
public class IntegerValueFactory implements ValueFactory<IntegerValue> {

	public IntegerValue createValue(InputStream is) {
		return null;
	}

	public byte getPrefix() {
		return 'i';
	}
}
