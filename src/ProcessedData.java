public class ProcessedData {
	private int operation;
	private String firstBitstring;
	private String secondBitstring;
	private String processedBitstring;
	private int processedDecimal;
	
	public ProcessedData(int operation, String firstBitstring,
			String secondBitstring, String processedBitstring,
			int processedDecimal)
	{
		this.operation = operation;
		this.firstBitstring = firstBitstring;
		this.secondBitstring = secondBitstring;
		this.processedBitstring = processedBitstring;
		this.processedDecimal = processedDecimal;
	}
	
	public int getOperation() { return operation; }
	
	public String getfirstBitString() { return firstBitstring; }
	
	public String getSecondBitstring() { return secondBitstring; }
	
	public String getProcessedBitstring() { return processedBitstring; }
	
	public int getProcessedDecimal() { return processedDecimal; }
	
	public String toString() {
		return ("Bitwise " + ((operation == 1) ? "AND" : "OR") + " performed on " +
				firstBitstring + " and " + secondBitstring + ".\nResult: " +
				processedBitstring + " which is " + processedDecimal +
				" in decimal format");
	}
	
}
