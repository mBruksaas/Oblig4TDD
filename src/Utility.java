
public class Utility {

	public static void main(String[] args) {
		
	}
	
	public int hexToInt(String s) {
		if(s.length() == 0)
			return 0;
		else if(s.length() > 5)
			throw new IllegalArgumentException("Hex input too long");
		
        String hexDigits = "0123456789ABCDEF";
        s = s.toUpperCase();
        int dec = 0;
        for (int i = 0; i < s.length(); i++)
            dec = (dec * 16) + hexDigits.indexOf(s.charAt(i));
        
        return dec;
    }
	
	public int binaryToInt(String binary) {
	    int result = 0;
	    for(int i = 0; i <= binary.length() - 1; i++)
	        if(binary.charAt(i) == '1')
	            result += Math.pow(2, (binary.length() - i - 1));
	    
	    return result;
	}
	
	public int bitStringToInt(int o, String first, String second) {
		char[] firstChars = first.toCharArray();
		char[] secondChars = second.toCharArray();
		
		for(char c : firstChars)
			if(c < '0' || c > '1')
				throw new IllegalArgumentException("First bit-string invalid");
			
		for(char c : secondChars)
			if(c < '0' || c > '1')
				throw new IllegalArgumentException("Second bit-string invalid");
			
		if(first.length() == 0 || second.length() == 0)
			return 0;
		else if(first.length() > 24 || second.length() > 24)
			throw new IllegalArgumentException("Bit-string longer than 24 bits");
		
		int biggestString = Math.max(first.length(), second.length());
		char[] newBitString = new char[biggestString];
		
		if(o == 1) {
			for(int i = 0; i < biggestString; i++) {
				if(i > first.length() - 1 || i > second.length() - 1) {
					newBitString[i] = 0;
					break;
				}
				
				if(firstChars[i] == 1 && secondChars[i] == 1)
					newBitString[i] = 1;
				else
					newBitString[i] = 0;
			}
		} else if(o == 2) {
			for(int i = 0; i < biggestString; i++) {
				if(i < first.length() - 1)
					if(firstChars[i] == 1) {
						newBitString[i] = 1;
						break;
					}
				
				if(i < second.length() - 1)
					if(secondChars[i] == 1) {
						newBitString[i] = 1;
						break;
					}
				
				newBitString[i] = 0;
			}
		}
		return binaryToInt(newBitString.toString());
	}

}
