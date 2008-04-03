/*
 * Created on Mar 23, 2008
 */
package com.googlecode.jbencode;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;

import com.googlecode.jbencode.composite.DictionaryValue;
import com.googlecode.jbencode.composite.ListValue;
import com.googlecode.jbencode.primitive.IntegerValue;
import com.googlecode.jbencode.primitive.StringValue;

/**
 * @author Daniel Spiewak
 */
public final class Parser {
	private final Map<Byte, Class<? extends Value<?>>> valueTypes;
	
	public Parser() {
		valueTypes = new HashMap<Byte, Class<? extends Value<?>>>();
		
		addType(IntegerValue.class);
		addType(ListValue.class);
		addType(DictionaryValue.class);
	}
	
	private final void addType(Class<? extends Value<?>> type) {
		valueTypes.put(type.getAnnotation(Prefix.class).value(), type);
	}
	
	public Class<? extends Value<?>> getValueType(byte b) {
		return valueTypes.get(b);
	}
	
	public final Value<?> parse(InputStream is) throws IOException {
		int i = is.read();
		
		if (i >= 0) {
			byte b = (byte) i;
			Class<? extends Value<?>> valueType = getValueType(b);
			
			if (valueType != null) {
				return createValue(valueType, this, is);
			} else if (b > '0' && b < '9') {
				return readString(is, b - '0');
			} else if (b == ' ' || b == '\n' || b == '\r' || b == '\t') {
				return parse(is);		// loop state
			} else {
				throw new IOException("Unexpected character in the parse stream: " + Character.forDigit(i, 10));
			}
		}
		
		throw new IOException("Unexpected end of stream durring parse");
	}
	
	private final StringValue readString(InputStream is, long length) throws IOException {
		int i = is.read();
		
		if (i >= 0) {
			byte b = (byte) i;
			
			if (b == ':') {
				return createValue(StringValue.class, this, new SubStream(is, length));
			} else if (b > '0' && b < '9') {
				return readString(is, (length * 10) + b - '0');
			} else {
				throw new IOException("Unexpected character in string value: " + Character.forDigit(i, 10));
			}
		}
		
		throw new IOException("Unexpected end of stream in string value");
	}
	
	public static final <T extends Value<?>> T createValue(Class<T> type, Parser p, InputStream is) {
		try {
			return type.getConstructor(Parser.class, InputStream.class).newInstance(p, is);
		} catch (IllegalArgumentException e) {
		} catch (SecurityException e) {
		} catch (InstantiationException e) {
		} catch (IllegalAccessException e) {
		} catch (InvocationTargetException e) {
		} catch (NoSuchMethodException e) {
		}
		
		return null;
	}
}
