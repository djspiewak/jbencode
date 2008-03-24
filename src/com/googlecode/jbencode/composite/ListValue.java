/*
 * Created on Mar 23, 2008
 */
package com.googlecode.jbencode.composite;

import com.googlecode.jbencode.Value;

/**
 * @author Daniel Spiewak
 */
public class ListValue extends CompositeValue<ListValue, Value<?>> {

	public ListValue resolve() {
		return this;
	}

	public boolean hasNext() {
		return false;
	}

	public Value<?> next() {
		return null;
	}
}
