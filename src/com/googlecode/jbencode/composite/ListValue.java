/*
 * Created on Mar 23, 2008
 */
package com.googlecode.jbencode.composite;

import java.io.IOException;
import java.io.InputStream;

import com.googlecode.jbencode.Parser;
import com.googlecode.jbencode.Prefix;
import com.googlecode.jbencode.Value;

/**
 * @author Daniel Spiewak
 */
@Prefix('l')
public class ListValue extends CompositeValue<ListValue, Value<?>> {

	public ListValue(Parser parser, InputStream is) {
		super(parser, is);
	}

	public Value<?> next() {
		try {
			return parse();
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}
}
