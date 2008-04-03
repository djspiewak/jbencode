/*
 * Created on Mar 23, 2008
 */
package com.googlecode.jbencode.composite;

import java.io.IOException;
import java.io.InputStream;
import java.util.Iterator;

import com.googlecode.jbencode.Parser;
import com.googlecode.jbencode.SubStream;
import com.googlecode.jbencode.Value;
import com.googlecode.jbencode.primitive.StringValue;
import com.googlecode.jbencode.util.None;
import com.googlecode.jbencode.util.Option;
import com.googlecode.jbencode.util.Some;

/**
 * @author Daniel Spiewak
 */
public abstract class CompositeValue<T extends CompositeValue<T, V>, V extends Value<?>> 
		implements Value<T>, Iterator<V>, Iterable<V> {
	private final Parser parser;
	private final InputStream is;
	
	private boolean resolved = false;
	private Option<Byte> readAhead;
	private Value<?> previous;
	
	public CompositeValue(Parser parser, InputStream is) {
		this.parser = parser;
		this.is = is;
		
		readAhead = new None<Byte>();
		previous = null;
	}

	public T resolve() throws IOException {
		if (resolved) {
			throw new IOException("Value already resolved");
		}
		resolved = true;
		
		for (V value : this) {
			value.resolve();
		}
		
		return (T) this;
	}
	
	public boolean isResolved() {
		return resolved;
	}

	public boolean hasNext() {
		if (resolved) {
			return false;
		}
		
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
	
    public final Iterator<V> iterator() {
    	return this;
    }
    
    public final void remove() {
    }

	protected final Value<?> parse() throws IOException {
		if (resolved) {
			throw new IOException("Composite value already resolved");
		}
		
		if (previous != null) {
			if (!previous.isResolved()) {
				previous.resolve();		// ensure we're at the right spot in the stream
			}
		}
		
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
				return previous = Parser.createValue(valueType, parser, is);
			} else if (b > '0' && b <= '9') {
				return previous = readString(b - '0');
			} else if (b == ' ' || b == '\n' || b == '\r' || b == '\t') {
				return parse();		// loop state
			} else {
				throw new IOException("Unexpected character in the parse stream: " + Character.forDigit(b, 10));
			}
		}
		
		throw new IOException("Unexpected end of stream in composite value");
	}
	
	private final StringValue readString(long length) throws IOException {
		int i = is.read();
		
		if (i >= 0) {
			byte b = (byte) i;
			
			if (b == ':') {
				return Parser.createValue(StringValue.class, parser, new SubStream(is, length));
			} else if (b >= '0' && b <= '9') {
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
