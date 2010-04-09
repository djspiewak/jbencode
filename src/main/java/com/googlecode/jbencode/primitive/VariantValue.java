/*
 * Created on Mar 23, 2008
 */
package com.googlecode.jbencode.primitive;

import java.io.IOException;
import java.io.InputStream;

import com.googlecode.jbencode.Value;

/**
 * @author Daniel Spiewak
 */
public interface VariantValue<T> extends Value<T> {
	public long length() throws IOException;
	
	public InputStream getStream();
}
