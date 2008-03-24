/*
 * Created on Mar 23, 2008
 */
package com.googlecode.jbencode.composite;

import java.util.Iterator;

import com.googlecode.jbencode.Value;

/**
 * @author Daniel Spiewak
 */
public abstract class CompositeValue<T,V> implements Value<T>, Iterator<V>, Iterable<V> {
	
    public Iterator<V> iterator() {
    	return this;
    }
    
    public void remove() {
    }
}
