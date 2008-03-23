/*
 * Created on Mar 19, 2008
 */
package com.googlecode.jbencode.primitive;

import java.io.IOException;
import java.io.OutputStream;

/**
 * @author Daniel Spiewak
 */
public abstract class VariantPrimitiveType extends PrimitiveType {
	private final char prefix;
	
	public VariantPrimitiveType(char prefix) {
		this.prefix = prefix;
	}
	
	protected abstract long getLength();

	@Override
	protected void writePrefix(OutputStream os) throws IOException {
		os.write(prefix);
		writeLength(os);
	}
	
	protected final void writeLength(OutputStream os) throws IOException {
		os.write(Long.toString(getLength()).getBytes());
		os.write(':');
	}

	@Override
	protected final void writeSuffix(OutputStream os) throws IOException {}
}
