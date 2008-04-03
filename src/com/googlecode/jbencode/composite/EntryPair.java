/*
 * Created on Mar 23, 2008
 */
package com.googlecode.jbencode.composite;

import java.io.IOException;

import com.googlecode.jbencode.Value;
import com.googlecode.jbencode.primitive.StringValue;

/**
 * @author Daniel Spiewak
 */
public class EntryPair implements Value<EntryPair> {
	private final DictionaryValue parent;
	
	private StringValue key;
	private Value<?> value;
	
	private boolean resolved = false;
	
	EntryPair(DictionaryValue parent) {
		this.parent = parent;
	}

	public EntryPair resolve() throws IOException {
		if (resolved) {
			throw new IOException("Value already resolved");
		}
		resolved = true;
		
		if (!getKey().isResolved()) {
			getKey().resolve();
		}
		
		if (!getValue().isResolved()) {
			getValue().resolve();
		}
		
		return this;
	}
	
	public boolean isResolved() {
		return resolved;
	}
	
	public StringValue getKey() throws IOException {
		if (key == null) {
			Value<?> parse = parent.parse();
			
			if (parse instanceof StringValue) {
				key = (StringValue) parse;
			} else {
				throw new IOException("Unexpected key type: " + key.getClass());
			}
		}
		
		return key;
	}
	
	public Value<?> getValue() throws IOException {
		if (value == null) {
			if (!getKey().isResolved()) {
				getKey().resolve();		// reposition stream
			}
			
			value = parent.parse();
		}
		
		return value;
	}
}
