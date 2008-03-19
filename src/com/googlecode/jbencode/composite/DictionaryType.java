/*
 * Created on Mar 19, 2008
 */
package com.googlecode.jbencode.composite;

import java.io.IOException;
import java.io.OutputStream;
import java.util.SortedSet;
import java.util.TreeSet;


/**
 * @author Daniel Spiewak
 */
public abstract class DictionaryType extends CompositeType {
	
	public DictionaryType() {
		super('d');
	}
	
	@Override
	protected final void writeValue(OutputStream os) throws IOException {
		final SortedSet<Key<?>> keys = new TreeSet<Key<?>>();
		populate(keys);
		
		for (Key<?> key : keys) {
			key.write(os);
		}
	}
	
	protected abstract void populate(SortedSet<Key<?>> keys);
}
