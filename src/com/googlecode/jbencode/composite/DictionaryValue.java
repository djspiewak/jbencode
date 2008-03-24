/*
 * Created on Mar 23, 2008
 */
package com.googlecode.jbencode.composite;

/**
 * @author Daniel Spiewak
 */
public class DictionaryValue extends CompositeValue<DictionaryValue, EntryPair> {

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
