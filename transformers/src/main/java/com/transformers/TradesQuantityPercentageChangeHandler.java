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

	public static void mergeDailyFile(String T_Minus1DayFile, String T_DayFile, LocalDate T_Date) {
		
		TradedQuantityChangeFileWriter tradedQuantityChangeFileWriter=null;
		try {
			List<ShareDataADay> TMinus1SharesData = ShareDailyDataReader.readFile("C:\\Users\\sethi\\Desktop\\sharedata\\daily-data\\"+T_Minus1DayFile);
			Map<String, ShareDataADay> TMinus1DayTradedQuantity = TMinus1SharesData.stream().collect(Collectors.toMap(ShareDataADay::getSymbol, Function.identity()));
			
			List<ShareDataADay> TSharesData = ShareDailyDataReader.readFile("C:\\Users\\sethi\\Desktop\\sharedata\\daily-data\\"+T_DayFile);
			Map<String, ShareDataADay> TDayTradedQuantity = TSharesData.stream().collect(Collectors.toMap(ShareDataADay::getSymbol, Function.identity()));
			
			
			Map<String,Double> tradedQuantityPercentageChangeForSymbols = new LinkedHashMap<String,Double>();
			for(Entry<String, ShareDataADay> sd: TMinus1DayTradedQuantity.entrySet()) {
				Double tradedQuantityPercentageChange = Calculator.calculateTotalTradedQuantityPercentageChange(sd.getValue(),TDayTradedQuantity.get(sd.getKey()));
				tradedQuantityPercentageChangeForSymbols.put(sd.getKey(), tradedQuantityPercentageChange);
			}
			tradedQuantityChangeFileWriter = new TradedQuantityChangeFileWriter();
			tradedQuantityChangeFileWriter.writeDataForMultipleSymbolsOnADate(T_Date,tradedQuantityPercentageChangeForSymbols);
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			try {
				if(tradedQuantityChangeFileWriter!=null) tradedQuantityChangeFileWriter.closeFile();
			}catch(Exception e) {
				e.printStackTrace();
			}
		}
		
		
	}

}
