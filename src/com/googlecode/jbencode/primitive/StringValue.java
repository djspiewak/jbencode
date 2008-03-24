/*
 * Created on Mar 23, 2008
 */
package com.googlecode.jbencode.primitive;

import java.io.InputStream;

/**
 * @author Daniel Spiewak
 */
public class StringValue implements VariantValue<byte[]> {
	
	public InputStream getStream() {
		return null;
	}

	public byte[] resolve() {
		return null;
	}

	public long length() {
		return 0;
	}
}
