/*
 * Created on Mar 23, 2008
 */
package com.googlecode.jbencode;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;

import com.googlecode.jbencode.composite.CompositeValue;
import com.googlecode.jbencode.composite.DictionaryValue;
import com.googlecode.jbencode.composite.ListValue;
import com.googlecode.jbencode.primitive.IntegerValue;
import com.googlecode.jbencode.primitive.StringValue;
import com.googlecode.jbencode.primitive.VariantValue;
import com.googlecode.jbencode.util.SubStream;

/**
 * @author Daniel Spiewak
 */
public final class Parser {
	private final Map<Byte, Class<? extends Value<?>>> valueTypes;
	
	public Parser() {
		valueTypes = new HashMap<Byte, Class<? extends Value<?>>>();
		
		addValue(IntegerValue.class);
		addValue(ListValue.class);
		addValue(DictionaryValue.class);
	}
	
	public final void addValue(Class<? extends Value<?>> value) {
		valueTypes.put(value.getAnnotation(ValuePrefix.class).value(), value);
	}
	
	public Class<? extends Value<?>> getValueType(byte b) {
		return valueTypes.get(b);
	}
	
	public final Value<?> parse(InputStream is) throws IOException {
		return new DelegateComposite(this, is).next();
	}
	
	public static final <T extends Value<?>> T createValue(Class<T> type, Parser p, InputStream is) {
		boolean isVariant = true;
		try {
			type.asSubclass(VariantValue.class);
		} catch (ClassCastException e) {
			isVariant = false;
		}
		
		if (isVariant && !type.equals(StringValue.class)) {		// string is special
			try {
				return readVariant(type, p, is, 0);
			} catch (IOException e) {
				return null;
			}
		}
		
		return createValueImpl(type, p, is);
	}
	
	private static final <T extends Value<?>> T createValueImpl(Class<T> type, Parser p, InputStream is) {
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
	
	private static final <T extends Value<?>> T readVariant(Class<T> type, Parser parser, InputStream is, long length) throws IOException {
		int i = is.read();
		
		if (i >= 0) {
			byte b = (byte) i;
			
			if (b == ':') {
				return createValueImpl(type, parser, new SubStream(is, length));
			} else if (b >= '0' && b <= '9') {
				return readVariant(type, parser, is, (length * 10) + b - '0');
			} else {
				throw new IOException("Unexpected character in variant value: " + Character.forDigit(i, 10));
			}
		}
		
		throw new IOException("Unexpected end of stream in variant value");
	}
	
	private static final class DelegateComposite extends CompositeValue<DelegateComposite, Value<?>> {
		private boolean exhausted = false;
		
		private DelegateComposite(Parser parser, InputStream is) {
			super(parser, is);
		}
		
		@Override
		public boolean hasNext() {
			return !exhausted;
		}
		
		public Value<?> next() {
			if (exhausted) {
				assert false;
				return null;
			}
			exhausted = true;
			
			try {
				return parse();
			} catch (IOException e) {
				throw new RuntimeException(e);
			}
		}
	}
}
