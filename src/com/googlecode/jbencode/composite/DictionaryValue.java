/*
 * Created on Mar 23, 2008
 */
package com.googlecode.jbencode.composite;

import java.io.IOException;
import java.io.InputStream;

import com.googlecode.jbencode.Parser;
import com.googlecode.jbencode.ValuePrefix;

/**
 * @author Daniel Spiewak
 */
@ValuePrefix('d')
public class DictionaryValue extends CompositeValue<DictionaryValue, EntryValue> {
	private EntryValue previous;
	
	public DictionaryValue(Parser p, InputStream is) {
		super(p, is);
		
		previous = null;
	}

	public EntryValue next() {
		try {
			if (previous != null) {
				previous.resolve();
			}
			
			return previous = new EntryValue(this);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}
}
