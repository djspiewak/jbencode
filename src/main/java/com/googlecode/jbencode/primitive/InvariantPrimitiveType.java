/*
 * Created on Mar 19, 2008
 */
package com.googlecode.jbencode.primitive;

import java.io.IOException;
import java.io.OutputStream;

/**
 * @author Daniel Spiewak
 */
public abstract class InvariantPrimitiveType extends PrimitiveType {
	private final byte prefix;
	
	public InvariantPrimitiveType(byte prefix) {
		this.prefix = prefix;
	}
	
	@Override
	protected final void writePrefix(OutputStream os) throws IOException {
		os.write(prefix);
	}
	
	@Override
	protected final void writeSuffix(OutputStream os) throws IOException {
		os.write('e');
	}
}
