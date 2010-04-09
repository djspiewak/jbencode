import static org.junit.Assert.fail;

import java.io.ByteArrayInputStream;
import java.io.IOException;

import com.googlecode.jbencode.Parser;
import com.googlecode.jbencode.Value;
import com.googlecode.jbencode.composite.DictionaryValue;
import com.googlecode.jbencode.composite.EntryValue;
import com.googlecode.jbencode.composite.ListValue;
import com.googlecode.jbencode.primitive.IntegerValue;
import com.googlecode.jbencode.primitive.StringValue;

/*
 * Created on Apr 2, 2008
 */

/**
 * Joke of a unit test
 * 
 * @author Daniel Spiewak
 */
public class ParserTest {

	public static void main(String[] args) throws IOException {
		Parser parser = new Parser();
		ByteArrayInputStream is = new ByteArrayInputStream(
				"ld5:Helloi5e5:World7:Testing4:Fivei4ee10:Test Valueli123ei456ei7890eee".getBytes());
		
		ListValue root = (ListValue) parser.parse(is);
		for (Value<?> value : root) {
			if (value instanceof DictionaryValue) {
				DictionaryValue dict = (DictionaryValue) value;
				
				System.out.println('{');
				for (EntryValue pair : dict) {
					System.out.print("  \"" + new String(pair.getKey().resolve()) + "\" -> ");
					
					if (pair.getValue() instanceof IntegerValue) {
						System.out.println(pair.getValue().resolve().toString());
					} else if (pair.getValue() instanceof StringValue) {
						System.out.println('"' + new String(((StringValue) pair.getValue()).resolve()) + '"');
					} else {
						fail("Unrecognized type");
					}
				}
				System.out.println('}');
			} else if (value instanceof StringValue) {
				System.out.println(new String(((StringValue) value).resolve()));
			} else if (value instanceof ListValue) {
				System.out.println("[");
				for (Value<?> subValue : (ListValue) value) {
					System.out.println("  " + subValue.resolve().toString());
				}
				System.out.println("]");
			} else {
				fail("Unrecognized type");
			}
		}
	}
}
