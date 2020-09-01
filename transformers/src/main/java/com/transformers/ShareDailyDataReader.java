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

public class ShareDailyDataReader {

	public static List<ShareDataADay> readFile(String file) throws Exception {
		
		List<ShareDataADay> history = new ArrayList<ShareDataADay>();
		try(FileReader filereader = new FileReader(new File(file))){ 
			CSVReader csvReader = new CSVReaderBuilder(filereader).withSkipLines(1).build();
			List<String[]> allData = csvReader.readAll(); 
			for (String[] row : allData) { 
				history.add(ShareDataADay.builder()
						.symbol(row[0])
						.series(row[1])
						.openPrice(parseFloat(row[2]))
						.highPrice(parseFloat(row[3]))
						.lowPrice(parseFloat(row[4]))
						.closePrice(parseFloat(row[5]))
						.lastPrice(parseFloat(row[6]))
						.prevClose(parseFloat(row[7]))
						.totalTradedQuantity(parseLong(row[8]))
						.turnover(parseFloat(row[9]))
						.date(toDate(row[10],DateTimeFormatter.ofPattern("dd-MMM-yyyy")))
						.numberOfTrades(parseLong(row[11].trim()))
						.isin(row[12].trim())
						//.averagePrice(parseFloat(row[9]))
						//.deliverableQty(parseLong(row[13]))
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
		dt = dt.replace("AUG", "Aug");
		dt = dt.replace("SEP", "Sep");
		return LocalDate.parse(dt, dateTimeFormatter);
	};

}
