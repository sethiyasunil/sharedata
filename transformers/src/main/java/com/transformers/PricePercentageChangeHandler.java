package com.transformers;

import java.time.LocalDate;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class PricePercentageChangeHandler {
	
	public static void processHistoricData() {
		
		PercentageChangeFileWriter percentageIncreaseFileWriter = null;
		try {
			percentageIncreaseFileWriter = new PercentageChangeFileWriter();
			
			for(Share share: SharesManager.getAllShared()) {
				String symbol = share.getSymbol();
				System.err.println("processing "+ symbol);
				List<ShareDataADay> history = ShareHistoricDataReader.readFile(SharesManager.getSymbolFileHistoric(symbol));
				//Map<LocalDate, Float> pericePercentageChange = Calculator.calculatePricePercentageChange(history);			
				//percentageIncreaseFileWriter.writeDataForaSymbol(symbol,pericePercentageChange);
			};
		}catch(Exception e) {
			e.printStackTrace();
		}try {
			percentageIncreaseFileWriter.closeFile();
		}catch(Exception e) {
			e.printStackTrace();
		}
		
	}

	public static void mergeDailyFile(String fileName) {
		System.out.println("Processing file "+ fileName);
		
		PercentageChangeFileWriter percentageIncreaseFileWriter=null;
		
		try {
			percentageIncreaseFileWriter = new PercentageChangeFileWriter();
			List<ShareDataADay> dailySharesData = ShareDailyDataReader.readFile("C:\\Users\\sethi\\Desktop\\sharedata\\daily-data\\"+fileName);
			Map<String,Float> percentagePriceIncreaseForASymbol = new LinkedHashMap<String,Float>();
			for(ShareDataADay sd: dailySharesData) {
				Float pricePercentageChange = Calculator.calculatePricePercentageChange(sd);
				percentagePriceIncreaseForASymbol.put(sd.getSymbol(), pricePercentageChange);
			}
			percentageIncreaseFileWriter.writeDataForMultipleSymbolsOnADate(dailySharesData.get(0).getDate(),percentagePriceIncreaseForASymbol);
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			try {
				percentageIncreaseFileWriter.closeFile();
			}catch(Exception e) {
				e.printStackTrace();
			}
		}
		
		
	}

}
