package com.transformers;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;

public class ShareHistoricDataReader {

	public static List<ShareDataADay> readFile(String file) throws Exception {
		
		List<ShareDataADay> history = new ArrayList<ShareDataADay>();
		try(FileReader filereader = new FileReader(new File(file))){
			CSVReader csvReader = new CSVReaderBuilder(filereader).withSkipLines(1).build();
			List<String[]> allData = csvReader.readAll(); 
			for (String[] row : allData) { 
				history.add(ShareDataADay.builder()
						.symbol(row[0])
						.series(row[1])
						.date(toDate(row[2],DateTimeFormatter.ofPattern("dd-MMM-yyyy")))
						.prevClose(parseFloat(row[3]))
						.openPrice(parseFloat(row[4]))
						.highPrice(parseFloat(row[5]))
						.lowPrice(parseFloat(row[6]))
						.lastPrice(parseFloat(row[7]))
						.closePrice(parseFloat(row[8]))
						.averagePrice(parseFloat(row[9]))
						.totalTradedQuantity(parseLong(row[10]))
						.numberOfTrades(parseLong(row[12].trim()))
						.deliverableQty(parseLong(row[13]))
						.build()
						);
	        }			
		}
	
		return history;
		
	}

	private static Long parseLong(String number) {
		if("-".equals(number))
			return null;
		else
			return Long.parseLong(number.trim());
	}
	
	private static Float parseFloat(String number) {
		if("-".equals(number))
			return null;
		else
			return Float.parseFloat(number.trim());
	}
	
	private static LocalDate toDate(String dt, DateTimeFormatter dateTimeFormatter) {
		return LocalDate.parse(dt, dateTimeFormatter);
	};

}