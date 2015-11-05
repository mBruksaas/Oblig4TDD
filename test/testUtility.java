import static org.junit.Assert.*;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class testUtility {
	
	Utility util = new Utility();
	
	@Rule
	public ExpectedException thrown = ExpectedException.none();
	
	@Test
	public void hexToInt_TooLongString_ShouldThrowIAE() {
		thrown.expectMessage("Hex input too long");
		thrown.expect(IllegalArgumentException.class);
		
		util.hexToInt("ae23h12efe");
	}
	
	@Test
	public void hexToInt_EmptyString_ShouldReturnZero() {
		assertEquals(0, util.hexToInt(""));
	}
	
	@Test
	public void bitStringToInt_StringWithNonBits_ShouldThrowIAE() {
		thrown.expectMessage("Second bit-string invalid");
		thrown.expect(IllegalArgumentException.class);
		
		util.bitStringToInt(1, "0101010101", "01010101231234234");
	}
	
	@Test
	public void bitStringToInt_EmptyString_ShouldReturnZero() {
		assertEquals(0, util.bitStringToInt(1, "", ""));
	}
	
	@Test
	public void bitStringToInt_TooLongString_ShouldThrowIAE() {
		thrown.expectMessage("Bit-string longer than 24 bits");
		thrown.expect(IllegalArgumentException.class);
		
		util.bitStringToInt(1, "101001010101010010101101001010101001", "01010101");
	}
}
