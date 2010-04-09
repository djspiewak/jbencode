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
		super((byte) 'd');
	}
	
	@Override
	protected final void writeValue(OutputStream os) throws IOException {
		final SortedSet<EntryType<?>> entries = new TreeSet<EntryType<?>>();
		populate(entries);
		
		for (EntryType<?> entry : entries) {
			entry.write(os);
		}
	}
	
	protected abstract void populate(SortedSet<EntryType<?>> entries);
}
