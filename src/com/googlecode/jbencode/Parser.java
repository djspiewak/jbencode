/*
 * Created on Mar 23, 2008
 */
package com.googlecode.jbencode;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import com.googlecode.jbencode.factories.DictionaryValueFactory;
import com.googlecode.jbencode.factories.IntegerValueFactory;
import com.googlecode.jbencode.factories.ListValueFactory;
import com.googlecode.jbencode.factories.StringValueFactory;
import com.googlecode.jbencode.factories.ValueFactory;
import com.googlecode.jbencode.primitive.StringValue;

/**
 * @author Daniel Spiewak
 */
public final class Parser {
	private final Map<Byte, ValueFactory<?>> factories;
	private final ValueFactory<StringValue> stringFactory = new StringValueFactory();
	
	public Parser() {
		factories = new HashMap<Byte, ValueFactory<?>>();
		
		addFactory(new IntegerValueFactory());
		addFactory(new ListValueFactory());
		addFactory(new DictionaryValueFactory());
	}
	
	public void addFactory(ValueFactory<?> factory) {
		factories.put(factory.getPrefix(), factory);
	}
	
	public Value<?> parse(InputStream is) throws IOException {
		int i = is.read();
		
		if (i < 255) {
			byte b = (byte) i;
			ValueFactory<?> factory = factories.get(b);
			
			if (factory != null) {
				return factory.createValue(is);
			} else if (b > '0' && b < '9') {
				return readString(is, b - '0');
			} else if (b == ' ' || b == '\n' || b == '\r' || b == '\t') {
				return parse(is);		// loop state
			}
		}
		
		throw new IOException("Unexpected character in the parse stream: " + Character.forDigit(i, 10));
	}
	
	private StringValue readString(InputStream is, long length) throws IOException {
		int i = is.read();
		
		if (i < 255) {
			byte b = (byte) i;
			
			if (b == ':') {
				return stringFactory.createValue(new SubStream(is, length));
			} else if (b > '0' && b < '9') {
				return readString(is, (length * 10) + b - '0');
			}
		}
		
		throw new IOException("Unexpected character in the parse stream: " + Character.forDigit(i, 10));
	}
}
