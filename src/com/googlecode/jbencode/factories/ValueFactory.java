/*
 * Created on Mar 23, 2008
 */
package com.googlecode.jbencode.factories;

import java.io.InputStream;

import com.googlecode.jbencode.Value;

/**
 * @author Daniel Spiewak
 */
public interface ValueFactory<T extends Value<?>> {
	
	public byte getPrefix();
	
	public T createValue(InputStream is);
}
