public class Utility {
	String hexDigits = "0123456789ABCDEF";
	
	public int decToFromHex(String hex) {
		hex = hex.toUpperCase();
		
        //Check length
		if(hex.length() == 0)
			return 0;
		else if(hex.length() > 6)
			throw new IllegalArgumentException("Hex input too long");
		
		//Check for invalid characters
		for(char c : hex.toCharArray())
			if(hexDigits.indexOf(c) == -1) 
				throw new IllegalArgumentException("Invalid hex input");
		
        int dec = 0;
        for(int i = 0; i < hex.length(); i++)
            dec = (dec * 16) + hexDigits.indexOf(hex.charAt(i));
        
        return dec;
    }
	
	public String decToFromHex(int decimal) {
	    if (decimal == 0)
	    	return "0";
	    
	    String hex = "";
	    while(decimal > 0) {
	        hex = hexDigits.charAt(decimal % 16) + hex;
	        decimal = decimal / 16;
	    }
	    return hex;
    }
	
	public int decToFromBinary(String binary) {
		//Check length
		if(binary.length() == 0)
			return 0;
		else if(binary.length() > 24)
			throw new IllegalArgumentException("Bitstring longer than 24 bits");
		
		//Check for non-bits
		char[] binaryChars = binary.toCharArray();
		for(char c : binaryChars)
			if(c < '0' || c > '1')
				throw new IllegalArgumentException("Non-bits in bitstring");
		
		//Conversion
	    int result = 0;
	    for(int i = 0; i <= binary.length() - 1; i++)
	        if(binary.charAt(i) == '1')
	            result += Math.pow(2, (binary.length() - i -1));
	    
	    return result;
	}
	
	public String decToFromBinary(int decimal) {
		String binary = "";
		
		while(decimal > 0) {
			binary = (decimal % 2) + binary;
            decimal = decimal / 2;
		}
		
		//Add possible leading zeroes
		for(int i = binary.length(); i < 24; i++)
			binary = "0" + binary;
			
		return binary;
	}
	
	public String bitWiseAndOr(int o, String first, String second) {
		char[] firstChars = first.toCharArray();
		char[] secondChars = second.toCharArray();		
		
		int biggestString = Math.max(first.length(), second.length());
		char[] newBitString = new char[biggestString];
		
		if(o == 1) {  //BitWise AND
			for(int i = 0; i < biggestString; i++) {
				newBitString[i] = '0';
				
				//Bitwise = 0 if one bitstring has ended
				if(i >= first.length() || i >= second.length())
					continue;
				
				if(firstChars[i] == '1' && secondChars[i] == '1')
					newBitString[i] = '1';
			}
		} else if(o == 2) {  //BitWise OR
			for(int i = 0; i < biggestString; i++) {
				newBitString[i] = '1';
				
				if(i < first.length())
					if(firstChars[i] == '1')
						continue;
				
				if(i < second.length())
					if(secondChars[i] == '1')
						continue;
				
				newBitString[i] = '0';
			}
		}
		return String.valueOf(newBitString);
	}
}
