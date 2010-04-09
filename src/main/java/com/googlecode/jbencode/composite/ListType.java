/*
 * Created on Mar 19, 2008
 */
package com.googlecode.jbencode.composite;

import java.io.IOException;
import java.io.OutputStream;

/**
 * @author Daniel Spiewak
 */
public abstract class ListType extends CompositeType {

	public ListType() {
		super((byte) 'l');
	}

	@Override
	protected final void writeValue(OutputStream os) throws IOException {
		populate(new ListTypeStream(os));
	}
	
	protected abstract void populate(ListTypeStream list) throws IOException;
}
