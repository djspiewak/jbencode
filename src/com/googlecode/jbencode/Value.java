/*
 * Created on Mar 23, 2008
 */
package com.googlecode.jbencode;

/**
 * @author Daniel Spiewak
 */
public interface Value<T> {
	public T resolve();
}
