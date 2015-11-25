import java.io.*;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Date;
import java.util.Deque;
import java.util.HashMap;

public class DataCollector {
	private Utility util = new Utility();
	private HashMap<Integer, ProcessedData> map = new HashMap<>();
	private ArrayList<String> log = new ArrayList<>();
	private BufferedReader buf;
	private String lastLine;
	private Deque<String> stack = new ArrayDeque<>();
	
	public DataCollector(BufferedReader file) {
		buf = file;
	}
	
	public void clearLog() {
		log.clear();
	}
	
	public boolean hasLoggedErrors() {
		return log.size() > 0;
	}
	
	public ArrayList<String> getLog() {
		return log;
	}
	
	//Decimal key
	public ProcessedData getDataWithKey(int decKey) {
		return map.get(decKey);
	}
	
	public ProcessedData getDataWithKey(String hexKey) {
		return map.get(util.decToFromHex(hexKey));
	}
	
	//Decimal key
	public boolean mapContainsKey(int decKey) {
		return map.containsKey(decKey);
	}
	
	//Hex key
	public boolean mapContainsKey(String hexKey) {
		return map.containsKey(util.decToFromHex(hexKey));
	}
	
	public String readLine() throws IOException {
		//Checks if there are any lines in the queue from the .hasNext() method
		if(!stack.isEmpty()) {
			lastLine = stack.pop();
			processLine(lastLine);
		} else
			if(hasNext())
				readLine();
			else
				throw new IndexOutOfBoundsException("No more lines to read");
			
		return lastLine;
	}
	
	public Boolean hasNext() throws IOException {
		lastLine = buf.readLine();
		
		//Using stack because the .ready() method doesn't seem to work
		//with mocked BufferedReaders, only .readLine() works
		if(lastLine != null)
			stack.addLast(lastLine);
		
		return lastLine != null;
	}
	
	private void processLine(String line) throws IOException {
		int id;
		int operation;
		String firstString;
		String secondString;
		String processedString;
		int processedDecimal;
		
		//Splits line into arguments
		String[] segments = line.split(" ");
		if(segments.length != 4)
			throw new IllegalArgumentException(
					"Expected 4 arguments, but got " + segments.length);
		
		//Tries to use some of the utility methods and
		//catches and logs any exceptions
		try {
			id = util.decToFromHex(segments[0]);
			operation = Integer.parseInt(segments[1]);
			firstString = segments[2];
			secondString = segments[3];
			
			processedString =
				util.bitWiseAndOr(operation, firstString, secondString);
			
			processedDecimal =
				util.decToFromBinary(processedString);
			
		} catch (Exception e) {
			log.add(new Date().toString() + " (Invalid input): " + line);
			System.out.println("Error logged: " + e.getMessage());
			return;
		}
		
		//ProcessedData object to more easily store data in map
		ProcessedData data =
			new ProcessedData(operation, firstString,
				secondString, processedString, processedDecimal);
		
		//Checks if key already exists in map, if so, logs it as a duplicate
		if(!map.containsKey(id))
			map.put(id, data);
		else {
			log.add(new Date().toString() + " (Duplicate key): " + line);
			System.out.println("Duplicate key found, line logged");
		}
		
	}
}