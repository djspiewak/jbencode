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
public final class ListValueStream {
	private final OutputStream os;
	
	ListValueStream(OutputStream os) {
		this.os = os;
	}
	
	public void add(Type value) throws IOException {
		value.write(os);
	}
}
