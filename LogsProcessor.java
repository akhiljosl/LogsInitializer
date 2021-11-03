package com.loggertool.inhouselogsanalyser;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import org.apache.commons.lang3.StringUtils;

public class LogsProcessor {
	
	 final static int LATENCY_THRESHOLD = 4;

	/**
	 * Method to read the input file and store id and timestamp in a List for further processing
	 * @throws FileNotFoundException
	 */
	public void fetchFileContent() throws FileNotFoundException {
		// Open the file
		FileInputStream fstream = new FileInputStream("C:\\Users\\Joshi\\Desktop\\updated.txt");
		BufferedReader br = new BufferedReader(new InputStreamReader(fstream));
		String strLine;
		List<SingleRowDescription> loggerLines = new ArrayList<>();
		//Read File Line By Line
		try {
			while ((strLine = br.readLine()) != null)   {
				System.out.println (strLine);
				SingleRowDescription singlerow = new SingleRowDescription();	
				String timestamp = StringUtils.getDigits(strLine);
				if(timestamp.length()==18) {
					timestamp=timestamp.substring(5);
				}
				String id = StringUtils.substringBetween(strLine, "id", "state");
				System.out.println("The value of id and timestamp is " + id + " "+ timestamp);
				BigInteger timeStamp=new BigInteger(timestamp);
				System.out.println("Value of timestamp aftr bigInt conversion is " + timeStamp);
				singlerow.setId(id);
				singlerow.setTimestamp(timeStamp);
				loggerLines.add(singlerow);
			}
			System.out.println("Logger Lines - " + loggerLines.toString());
			calculateLatencyPerId(loggerLines);
		} catch (IOException e) {
			System.out.println("The exception message : " + e.getMessage());
		}

	}

	/**
	 * Method to process the List and filter out ids with latecy greater than four
	 * @param loggerLines
	 */
	private void calculateLatencyPerId(List<SingleRowDescription> loggerLines) {
		HashMap<String, BigInteger> hm = new HashMap<>();	
		for (SingleRowDescription singleRow : loggerLines) {
			if(!hm.containsKey(singleRow.getId())) {
				hm.put(singleRow.getId(), singleRow.getTimestamp());
			}else {
				BigInteger startedEntry = hm.get(singleRow.getId());
				BigInteger latency = (singleRow.getTimestamp()).subtract(startedEntry);
				if (latency.signum()==-1) {
					latency= latency.negate();
				}
				System.out.println("value of latency : " +  latency);
				hm.put(singleRow.getId(), latency);
			}
		}
		System.out.println("The latency for each id is :  " + hm.toString());
		// Filter logic to get the ids with latency greater than 4
		Map<String, Object> collect = hm.entrySet().stream()
				.filter(map -> map.getValue().intValue()>LATENCY_THRESHOLD)
				.collect(Collectors.toMap(p -> p.getKey(), p -> p.getValue()));
		Set<String> idsOfConcern = collect.keySet();
		System.out.println("The ids where latency is high is - " + idsOfConcern);
		
		// Final step to insert the filtered ids to the DB
		// The idsOfConcern will be then passed on to the DAO Layer for DB insertion
	}

}
