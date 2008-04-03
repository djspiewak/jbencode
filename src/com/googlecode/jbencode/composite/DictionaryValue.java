/*
 * Created on Mar 23, 2008
 */
package com.googlecode.jbencode.composite;

import java.io.IOException;
import java.io.InputStream;

import com.googlecode.jbencode.Parser;
import com.googlecode.jbencode.Prefix;

/**
 * @author Daniel Spiewak
 */
@Prefix('d')
public class DictionaryValue extends CompositeValue<DictionaryValue, EntryPair> {
	private EntryPair previous;
	
	public DictionaryValue(Parser p, InputStream is) {
		super(p, is);
		
		previous = null;
	}

	public EntryPair next() {
		try {
			if (previous != null) {
				previous.resolve();
			}
			
			return previous = new EntryPair(this);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}
}
