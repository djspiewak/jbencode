import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.SortedSet;

import com.googlecode.jbencode.composite.DictionaryType;
import com.googlecode.jbencode.composite.EntryType;
import com.googlecode.jbencode.composite.ListType;
import com.googlecode.jbencode.composite.ListTypeStream;
import com.googlecode.jbencode.primitive.IntegerType;
import com.googlecode.jbencode.primitive.StringType;

/*
 * Created on Jul 14, 2008
 */

/**
 * @author Daniel Spiewak
 */
public class GeneratorTest {
	public static void main(String[] args) {
		ByteArrayOutputStream os = new ByteArrayOutputStream();
		final byte[] picture = new byte[0];		// presumably something interesting
		
		DictionaryType root = new DictionaryType() {
			@Override
			protected void populate(SortedSet<EntryType<?>> entries) {
				entries.add(new EntryType<LiteralStringType>(new LiteralStringType("name"), 
						new LiteralStringType("Arthur Dent")));
				entries.add(new EntryType<LiteralStringType>(new LiteralStringType("number"), 
						new IntegerType(42)));
				
				entries.add(new EntryType<LiteralStringType>(new LiteralStringType("picture"), 
						new StringType() {
					
					@Override
					protected long getLength() {
						return picture.length;
					}

					@Override
					protected void writeValue(OutputStream os) throws IOException {
						os.write(picture);
					}
				}));
				
				entries.add(new EntryType<LiteralStringType>(new LiteralStringType("planets"), 
						new ListType() {
	
					@Override
					protected void populate(ListTypeStream list) throws IOException {
						list.add(new LiteralStringType("Earth"));
						list.add(new LiteralStringType("Somewhere else"));
						list.add(new LiteralStringType("Old Earth"));
					}
				}));
			}
		};
		
		try {
			root.write(os);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		System.out.println(new String(os.toByteArray()));
	}
	
	private static class LiteralStringType extends StringType implements Comparable<LiteralStringType> {
		private final String value;
		
		public LiteralStringType(String value) {
			this.value = value;
		}

		@Override
		protected long getLength() {
			return value.length();
		}

		@Override
		protected void writeValue(OutputStream os) throws IOException {
			os.write(value.getBytes("US-ASCII"));
		}

		public int compareTo(LiteralStringType o) {
			return o.value.compareTo(value);
		}
	}
}
