import static org.junit.Assert.*;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class testUtility {
	Utility util = new Utility();
	
	@Rule
	public ExpectedException thrown = ExpectedException.none();
	
	@Test
	public void decToFromHex_ValidHex_ShouldReturnCorrectDecimal() {
		assertEquals(11412449, util.decToFromHex("ae23e1"));
	}
	
	@Test
	public void decToFromHex_ValidDecimal_ShouldReturnCorrectHex() {
		assertEquals("AE23E1", util.decToFromHex(11412449));
	}
	
	@Test
	public void decToFromHex_TooLongInput_ShouldThrowIAE() {
		thrown.expectMessage("Hex input too long");
		thrown.expect(IllegalArgumentException.class);
		
		util.decToFromHex("ae23h13");
	}
	
	@Test
	public void decToFromHex_EmptyInput_ShouldReturnZero() {
		assertEquals(0, util.decToFromHex(""));
	}
	
	@Test
	public void decToFromHex_InvalidHexInput_ShouldThrowIAE() {
		thrown.expectMessage("Invalid hex input");
		thrown.expect(IllegalArgumentException.class);
		
		util.decToFromHex("ae23h1");
	}
	
	@Test
	public void decToFromBinary_StringWithNonBits_ShouldThrowIAE() {
		thrown.expectMessage("Non-bits in bitstring");
		thrown.expect(IllegalArgumentException.class);
		
		util.decToFromBinary("01010101231234234");
	}
	
	@Test
	public void decToFromBinary_EmptyString_ShouldReturnZero() {
		assertEquals(0, util.decToFromBinary(""));
	}
	
	@Test
	public void decToFromBinary_TooLongString_ShouldThrowIAE() {
		thrown.expectMessage("Bitstring longer than 24 bits");
		thrown.expect(IllegalArgumentException.class);
		
		util.decToFromBinary("101001010101010010101101001010101001");
	}
	
	@Test
	public void decToFromBinary_ValidBitstring_ShouldReturnCorrectValue() {
		assertEquals(1280, util.decToFromBinary("000000000000010100000000"));
	}
	
	@Test
	public void decToFromBinary_bitstringWithOnlyOnes_ShouldReturnCorrectValue() {
		assertEquals(16777215, util.decToFromBinary("111111111111111111111111"));
	}
	
	@Test
	public void decToFromBinary_bitstringWithOnlyZeroes_ShouldReturnCorrectValue() {
		assertEquals(0, util.decToFromBinary("000000000000000000000000"));
	}
	
	@Test
	public void decToFromBinary_AnyDecimal_ShouldReturn24bitString() {
		assertEquals("000000000000000000010111", util.decToFromBinary(23));
	}
	
	@Test
	public void bitWiseAndOr_NonEqualLengthBitstrings_ShouldStillCompute() {
		assertEquals("000000000000010101000000", util.bitWiseAndOr(1,
						"110101000000110111001101", "00100001111001110100"));
	}
	
	@Test
	public void bitWiseAndOr_ValidBitstringsBitwiseAND_ShouldReturnCorrectBitstring() {
		assertEquals("000000000000010101001101", util.bitWiseAndOr(1,
						"110101000000110111001101", "001000011110011101001111"));
	}
	
	@Test
	public void bitWiseAndOr_ValidBitstringsBitwiseOR_ShouldReturnCorrectBitstring() {
		assertEquals("001101011111011101111111", util.bitWiseAndOr(2,
						"001000011110011101001111", "000101010101010101111001"));
	}
}