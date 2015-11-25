import static org.junit.Assert.*;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import static org.mockito.Mockito.*;

import java.io.BufferedReader;
import java.io.IOException;

public class testDataCollector {
	
	BufferedReader file = mock(BufferedReader.class);
	DataCollector col = new DataCollector(file);
	
	@Rule
	public ExpectedException thrown = ExpectedException.none();
	
	@Test
	public void toStringInProcessedData_ValidBitstring_ShouldReturnCorrectInfo() throws IOException {
		when(file.readLine()).thenReturn(
				"03ac0f 1 110101000000110111001101 001000011110011101001111",
				"ac0e1e 2 001000011110011101001111 000101010101010101111001",
				null
			);
		
		col.readLine();
		col.readLine();
		
		assertEquals(col.getDataWithKey("03ac0f").toString(),
				"Bitwise AND performed on 110101000000110111001101 and 001000011110011101001111.\nResult: 000000000000010101001101 which is 1357 in decimal format");
	}
	
	@Test
	public void readLine_NoBitstrings_ShouldThrowIOBE() throws IOException {
		thrown.expectMessage("No more lines to read");
		thrown.expect(IndexOutOfBoundsException.class);
		
		col.readLine();
	}
	
	@Test
	public void readLine_ValidBitstrings_ShouldReturnSecondLine() throws IOException {
		when(file.readLine()).thenReturn(
				"03ac0f 1 110101000000110111001101 001000011110011101001111",
				"ac0e1e 2 001000011110011101001111 000101010101010101111001",
				null
			);
		
		col.readLine();
		
		assertEquals("ac0e1e 2 001000011110011101001111 000101010101010101111001", col.readLine());
	}
	
	@Test
	public void hasNext_NotBitstrings_ShouldReturnFalse() throws IOException {
		assertEquals(false, col.hasNext());
	}
	
	@Test
	public void hasNext_ValidBitstrings_ShouldReturnTrue() throws IOException {
		when(file.readLine()).thenReturn(
				"03ac0f 1 110101000000110111001101 001000011110011101001111",
				"ac0e1e 2 001000011110011101001111 000101010101010101111001",
				null
			);
		
		assertEquals(true, col.hasNext());
	}
	
	@Test
	public void getDataWithKey_ValidBitstringsAndDecKey_ShouldReturnCorrectValue() throws IOException {
		when(file.readLine()).thenReturn(
				"03ac0f 1 110101000000110111001101 001000011110011101001111",
				"ac0e1e 2 001000011110011101001111 000101010101010101111001",
				null
			);
			
		col.readLine();
		col.readLine();
		
		//Testing for bitstring #2
		assertEquals(3536767, col.getDataWithKey(11275806).getProcessedDecimal());
	}
	
	@Test
	public void getDataWithKey_ValidBitstringsAndHexKey_ShouldReturnCorrectValue() throws IOException {
		when(file.readLine()).thenReturn(
				"03ac0f 1 110101000000110111001101 001000011110011101001111",
				"ac0e1e 2 001000011110011101001111 000101010101010101111001",
				null
			);
			
		col.readLine();
		
		//Testing for bitstring #1
		assertEquals(1357, col.getDataWithKey("03ac0f").getProcessedDecimal());
	}
	
	@Test
	public void mapContainsKey_hexKey_MapShouldContainKey() throws IOException {
		when(file.readLine()).thenReturn(
				"03ac0f 1 110101000000110111001101 001000011110011101001111",
				"ac0e1e 2 001000011110011101001111 000101010101010101111001",
				null
			);
		
		col.readLine();
		col.readLine();
		
		//Testing for bitstring #2
		assertEquals(true, col.mapContainsKey("ac0e1e"));
	}
	
	@Test
	public void mapContainsKey_decKey_MapShouldContainKey() throws IOException {
		when(file.readLine()).thenReturn(
				"03ac0f 1 110101000000110111001101 001000011110011101001111",
				"ac0e1e 2 001000011110011101001111 000101010101010101111001",
				null
			);
		
		col.readLine();
		
		//Testing for bitstring #1
		assertEquals(true, col.mapContainsKey(240655));
	}
	
	@Test
	public void clearLog_duplicateBitstrings_ShouldClearLog() throws IOException {
		when(file.readLine()).thenReturn(
				"03ac0f 1 110101000000110111001101 001000011110011101001111",
				"03ac0f 1 110101000000110111001101 001000011110011101001111",
				null
			);
		
		col.readLine();
		col.readLine();
		
		col.clearLog();
		
		assertEquals(0, col.getLog().size());
	}
	
	@Test
	public void processLine_duplicateBitstrings_ShouldHaveLoggedError() throws IOException {
		when(file.readLine()).thenReturn(
				"03ac0f 1 110101000000110111001101 001000011110011101001111",
				"03ac0f 1 110101000000110111001101 001000011110011101001111",
				null
			);
		
		col.clearLog();
		col.readLine();
		col.readLine();
		
		assertEquals(true, col.hasLoggedErrors());
	}
	
	@Test
	public void processLine_bitstringWithInvalidOperator_ShouldHaveLoggedError() throws IOException {
		when(file.readLine()).thenReturn(
				"03ac0f 3 110101000000110111001101 001000011110011101001111",
				"ac0e1e 2 001000011110011101001111 000101010101010101111001",
				null
			);
		
		col.clearLog();
		col.readLine();
		col.readLine();
		
		assertEquals(true, col.hasLoggedErrors());
	}
	
	@Test
	public void processLine_bitstringWithInvalidHexId_ShouldHaveLoggedError() throws IOException {
		when(file.readLine()).thenReturn(
				"03ac0f3 1 110101000000110111001101 001000011110011101001111",
				"ac0e1e 2 001000011110011101001111 000101010101010101111001",
				null
			);
		
		col.clearLog();
		col.readLine();
		col.readLine();
		
		assertEquals(true, col.hasLoggedErrors());
	}
	
	@Test
	public void processLine_WrongNumberOfArguments_ShouldThrowIAE() throws IOException{
		when(file.readLine()).thenReturn(
				"03ac0f 1 110101000000110111001101",
				"ac0e1e 2 001000011110011101001111 000101010101010101111001",
				null
			);
		
		thrown.expectMessage("Expected 4 arguments, but got 3");
		thrown.expect(IllegalArgumentException.class);
		
		col.readLine();
	}
}
