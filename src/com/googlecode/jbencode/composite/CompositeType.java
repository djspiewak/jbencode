/*
 * Created on Mar 19, 2008
 */
package com.googlecode.jbencode.composite;

import java.io.IOException;
import java.io.OutputStream;

import com.googlecode.jbencode.Type;

/**
 * @author Daniel Spiewak
 */
public abstract class CompositeType implements Type {
	private final char prefix;
	
	public CompositeType(char prefix) {
		this.prefix = prefix;
	}

	public final void write(OutputStream os) throws IOException {
		os.write(prefix);
		writeValue(os);
		os.write('e');
	}
	
	protected abstract void writeValue(OutputStream os) throws IOException;
}
