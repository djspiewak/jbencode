/*
 * Created on Mar 19, 2008
 */
package com.googlecode.jbencode.composite;

import java.io.IOException;
import java.io.OutputStream;

import com.googlecode.jbencode.Type;
import com.googlecode.jbencode.primitive.StringType;

/**
 * @author Daniel Spiewak
 */
public class EntryType<T extends StringType & Comparable<T>> implements Type, Comparable<EntryType<T>> {
	private final T key;
	private final Type value;
	
	public EntryType(T key, Type value) {
		this.key = key;
		this.value = value;
	}
	
	public void write(OutputStream os) throws IOException {
		key.write(os);
		value.write(os);
	}

	public int compareTo(EntryType<T> o) {
		return o.key.compareTo(key);
	}
}
