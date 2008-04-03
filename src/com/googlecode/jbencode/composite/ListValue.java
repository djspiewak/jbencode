/*
 * Created on Mar 23, 2008
 */
package com.googlecode.jbencode.composite;

import java.io.IOException;
import java.io.InputStream;

import com.googlecode.jbencode.Parser;
import com.googlecode.jbencode.Prefix;
import com.googlecode.jbencode.SubStream;
import com.googlecode.jbencode.Value;
import com.googlecode.jbencode.primitive.StringValue;
import com.googlecode.jbencode.util.None;
import com.googlecode.jbencode.util.Option;
import com.googlecode.jbencode.util.Some;

/**
 * @author Daniel Spiewak
 */
@Prefix('l')
public class ListValue extends CompositeValue<ListValue, Value<?>> {
	private final Parser parser;
	private final InputStream is;
	
	private boolean resolved = false;
	private Option<Byte> readAhead;
	
	ListValue(Parser parser, InputStream is) {
		this.parser = parser;
		this.is = is;
		
		readAhead = new None<Byte>();
	}

	public ListValue resolve() throws IOException {
		if (resolved) {
			throw new IOException("Value already resolved");
		}
		resolved = true;
		
		for (Value<?> value : this) {
			value.resolve();
		}
		
		return this;
	}

	public boolean hasNext() {
		if (readAhead instanceof Some && readAhead.value() == 'e') {
			return false;
		}
		
		try {
			byte b = read();
			
			if (b < 0) {
				return false;
			}
			
			readAhead = new Some<Byte>(b);
			
			if (b == 'e') {
				return false;
			}
			
			return true;
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	public Value<?> next() {
		try {
			return parse();
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}
	
	private final Value<?> parse() throws IOException {
		byte b = -1;
		if (readAhead instanceof Some) {
			b = readAhead.value();
			readAhead = new None<Byte>();
		} else {
			b = read();
		}
		
		if (b >= 0) {
			Class<? extends Value<?>> valueType = parser.getValueType(b);
			
			if (valueType != null) {
				return Parser.createValue(valueType, parser, is);
			} else if (b > '0' && b < '9') {
				return readString(b - '0');
			} else if (b == ' ' || b == '\n' || b == '\r' || b == '\t') {
				return parse();		// loop state
			} else {
				throw new IOException("Unexpected character in the parse stream: " + Character.forDigit(b, 10));
			}
		}
		
		throw new IOException("Unexpected end of stream in list value");
	}
	
	private final StringValue readString(long length) throws IOException {
		int i = is.read();
		
		if (i >= 0) {
			byte b = (byte) i;
			
			if (b == ':') {
				return Parser.createValue(StringValue.class, parser, new SubStream(is, length));
			} else if (b > '0' && b < '9') {
				return readString((length * 10) + b - '0');
			} else {
				throw new IOException("Unexpected character in string value: " + Character.forDigit(i, 10));
			}
		}
		
		throw new IOException("Unexpected end of stream in string value");
	}
	
	private final byte read() throws IOException {
		return (byte) is.read();
	}
}
