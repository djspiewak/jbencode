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
		return new DelegateComposite(this, is).next();
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
