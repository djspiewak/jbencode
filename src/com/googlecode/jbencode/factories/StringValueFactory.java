/*
 * Created on Mar 23, 2008
 */
package com.googlecode.jbencode.factories;

import java.io.InputStream;

import com.googlecode.jbencode.primitive.StringValue;

/**
 * @author Daniel Spiewak
 */
public class StringValueFactory implements ValueFactory<StringValue> {

	public StringValue createValue(InputStream is) {
		return null;
	}

	public byte getPrefix() {
		return 0;
	}
}
