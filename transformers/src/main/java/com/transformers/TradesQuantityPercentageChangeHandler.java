package com.transformers;

import java.time.LocalDate;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.function.Function;
import java.util.stream.Collectors;

public class TradesQuantityPercentageChangeHandler {
	
	
	
	
	public static void processHistoricData() {
		TradedQuantityChangeFileWriter tradedQuantityChangeFileWriter=null;
		try {
			tradedQuantityChangeFileWriter = new TradedQuantityChangeFileWriter();
			
			for(Share share: SharesManager.getAllShared()) {
				String symbol = share.getSymbol();
				System.err.println("processing "+ symbol);
				List<ShareDataADay> history = ShareHistoricDataReader.readFile(SharesManager.getSymbolFileHistoric(symbol));
				Map<LocalDate, Double> tradedQuantityPercentageChange = Calculator.calculateTradedQuantityPercentageChange(history);			
				tradedQuantityChangeFileWriter.writeDataForaSymbol(symbol,tradedQuantityPercentageChange);
				
			};
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			try {
				tradedQuantityChangeFileWriter.closeFile();
			}catch(Exception e) {
				e.printStackTrace();
			}
		}
		
	}

	public static void mergeDailyFile(String T_Minus2DayFile, String T_Minus1DayFile, LocalDate T_Minus1Date) {
		
		try {
			List<ShareDataADay> TMinus2SharesData = ShareDailyDataReader.readFile("C:\\Users\\sethi\\Desktop\\sharedata\\historic-data\\dailydata\\"+T_Minus2DayFile);
			Map<String, ShareDataADay> TMinus2DayTradedQuantity = TMinus2SharesData.stream().collect(Collectors.toMap(ShareDataADay::getSymbol, Function.identity()));
			
			List<ShareDataADay> TMinus1SharesData = ShareDailyDataReader.readFile("C:\\Users\\sethi\\Desktop\\sharedata\\historic-data\\dailydata\\"+T_Minus1DayFile);
			Map<String, ShareDataADay> TMinus1DayTradedQuantity = TMinus1SharesData.stream().collect(Collectors.toMap(ShareDataADay::getSymbol, Function.identity()));
			
			
			Map<String,Double> tradedQuantityPercentageChangeForSymbols = new LinkedHashMap<String,Double>();
			for(Entry<String, ShareDataADay> sd: TMinus2DayTradedQuantity.entrySet()) {
				Double tradedQuantityPercentageChange = Calculator.calculateTotalTradedQuantityPercentageChange(sd.getValue(),TMinus1DayTradedQuantity.get(sd.getKey()));
				tradedQuantityPercentageChangeForSymbols.put(sd.getKey(), tradedQuantityPercentageChange);
			}
			new TradedQuantityChangeFileWriter().writeDataForMultipleSymbolsOnADate(T_Minus1Date,tradedQuantityPercentageChangeForSymbols);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}

}
