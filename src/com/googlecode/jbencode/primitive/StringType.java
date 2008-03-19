/*
 * Created on Mar 19, 2008
 */
package com.googlecode.jbencode.primitive;

import java.io.IOException;
import java.io.OutputStream;

/**
 * @author Daniel Spiewak
 */
public abstract class StringType extends VariantPrimitiveType {
	
	public StringType() {
		super(' ');
	}
	
	@Override
	protected final void writePrefix(OutputStream os) throws IOException {
		writeLength(os);
	}
}
