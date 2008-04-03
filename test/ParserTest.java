import java.io.ByteArrayInputStream;
import java.io.IOException;

import org.junit.Test;

import com.googlecode.jbencode.Parser;
import com.googlecode.jbencode.Value;
import com.googlecode.jbencode.composite.DictionaryValue;
import com.googlecode.jbencode.composite.EntryPair;
import com.googlecode.jbencode.composite.ListValue;

/*
 * Created on Apr 2, 2008
 */

/**
 * @author Daniel Spiewak
 */
public class ParserTest {

	@Test
	public void testParse() throws IOException {
		Parser parser = new Parser();
		ByteArrayInputStream is = new ByteArrayInputStream(
				"ld5:Helloi5e5:World7:Testing4:Fivei4ee10:Test Valueli123ei456ei7890eee".getBytes());
		
		ListValue root = (ListValue) parser.parse(is);
		for (Value<?> value : root) {
			if (value instanceof DictionaryValue) {
				DictionaryValue dict = (DictionaryValue) value;
				
				for (EntryPair pair : dict) {
					
				}
			}
		}
	}
}
