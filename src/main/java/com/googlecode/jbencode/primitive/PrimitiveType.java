/*
 * Created on Mar 19, 2008
 */
package com.googlecode.jbencode.primitive;

import java.io.IOException;
import java.io.OutputStream;

import com.googlecode.jbencode.Type;

/**
 * @author Daniel Spiewak
 */
public abstract class PrimitiveType implements Type {
	
	public PrimitiveType() {}

	public final void write(OutputStream os) throws IOException {
		writePrefix(os);
		writeValue(os);
		writeSuffix(os);
	}
	
	protected abstract void writePrefix(OutputStream os) throws IOException;
	protected abstract void writeValue(OutputStream os) throws IOException;
	protected abstract void writeSuffix(OutputStream os) throws IOException;
}
