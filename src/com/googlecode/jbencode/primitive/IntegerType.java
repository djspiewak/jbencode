/*
 * Created on Mar 19, 2008
 */
package com.googlecode.jbencode.primitive;

import java.io.IOException;
import java.io.OutputStream;

/**
 * @author Daniel Spiewak
 */
public class IntegerType extends InvariantPrimitiveType {
	private final long value;
	
	public IntegerType(long value) {
		super((byte) 'i');
		
		this.value = value;
	}
	
	@Override
	protected void writeValue(OutputStream os) throws IOException {
		os.write(Long.toString(value).getBytes());
	}
}
