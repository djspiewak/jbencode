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
public final class ListTypeStream {
	private final OutputStream os;
	
	ListTypeStream(OutputStream os) {
		this.os = os;
	}
	
	public void add(Type type) throws IOException {
		type.write(os);
	}
}
