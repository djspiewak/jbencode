/*
 * Created on Mar 23, 2008
 */
package com.googlecode.jbencode.primitive;

import java.io.IOException;
import java.io.InputStream;

import com.googlecode.jbencode.Parser;

/**
 * @author Daniel Spiewak
 */
public class StringValue implements VariantValue<byte[]> {
	private final InputStream is;
	private final long length;
	
	private boolean resolved = false;
	
	public StringValue(Parser p, InputStream is) throws IOException {
		this.is = is;
		
		this.length = is.available();
	}
	
	public InputStream getStream() {
		return is;
	}

	public byte[] resolve() throws IOException {
		if (resolved || is.available() == 0) {
			throw new IOException("Value already resolved");
		}
		resolved = true;
		
		byte[] bytes = new byte[is.available()];
		is.read(bytes);
		
		return bytes;
	}
	
	public boolean isResolved() {
		return resolved;
	}

	public long length() throws IOException {
		return length;
	}
}
