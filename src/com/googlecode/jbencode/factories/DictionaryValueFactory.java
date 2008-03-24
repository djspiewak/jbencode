/*
 * Created on Mar 23, 2008
 */
package com.googlecode.jbencode.factories;

import java.io.InputStream;

import com.googlecode.jbencode.composite.DictionaryValue;

/**
 * @author Daniel Spiewak
 */
public class DictionaryValueFactory implements ValueFactory<DictionaryValue> {

	public DictionaryValue createValue(InputStream is) {
		return null;
	}

	public byte getPrefix() {
		return 'd';
	}
}
