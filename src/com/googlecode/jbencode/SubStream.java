/*
 * Created on Mar 23, 2008
 */
package com.googlecode.jbencode;

import java.io.IOException;
import java.io.InputStream;

/**
 * @author Daniel Spiewak
 */
public class SubStream extends InputStream {
	private final InputStream is;
	private final long length;
	
	private long index = 0;
	
	public SubStream(InputStream is, long length) {
		this.is = is;
		this.length = length;
	}
	
	@Override
	public int available() throws IOException {
		return (int) length;
	}

	@Override
	public int read() throws IOException {
		if (index++ < length) {
			return is.read();
		}
		
		throw new IOException("Stream segment overrun");
	}
}
