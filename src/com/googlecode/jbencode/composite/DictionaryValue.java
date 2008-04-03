/*
 * Created on Mar 23, 2008
 */
package com.googlecode.jbencode.composite;

import java.io.InputStream;

import com.googlecode.jbencode.Parser;
import com.googlecode.jbencode.Prefix;

/**
 * @author Daniel Spiewak
 */
@Prefix('d')
public class DictionaryValue extends CompositeValue<DictionaryValue, EntryPair> {
	
	DictionaryValue(Parser p, InputStream is) {
	}

	public DictionaryValue resolve() {
		return this;
	}

	public boolean hasNext() {
		return false;
	}

	public EntryPair next() {
		return null;
	}
}
