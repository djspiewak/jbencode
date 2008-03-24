/*
 * Created on Mar 23, 2008
 */
package com.googlecode.jbencode.factories;

import java.io.InputStream;

import com.googlecode.jbencode.composite.ListValue;

/**
 * @author Daniel Spiewak
 */
public class ListValueFactory implements ValueFactory<ListValue> {

	public ListValue createValue(InputStream is) {
		return null;
	}

	public byte getPrefix() {
		return 'l';
	}
}
