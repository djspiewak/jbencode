/*
 * Created on Mar 23, 2008
 */
package com.googlecode.jbencode.composite;

import com.googlecode.jbencode.Value;
import com.googlecode.jbencode.primitive.StringValue;

/**
 * @author Daniel Spiewak
 */
public class EntryPair implements Value<EntryPair> {

	public EntryPair resolve() {
		return this;
	}
	
	public StringValue getKey() {
		return null;
	}
	
	public Value<?> getValue() {
		return null;
	}
}
