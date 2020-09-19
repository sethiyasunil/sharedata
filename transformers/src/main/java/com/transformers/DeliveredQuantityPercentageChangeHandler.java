package com.transformers;

import java.time.LocalDate;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.function.Function;
import java.util.stream.Collectors;

public class DeliveredQuantityPercentageChangeHandler {

	public static void processHistoricData() {

		DeliveredQuantityChangeFileWriter deliveredQuantityChangeFileWriter=null;
		try {
			deliveredQuantityChangeFileWriter = new DeliveredQuantityChangeFileWriter();
			
			for(Share share: SharesManager.getAllShared()) {
				String symbol = share.getSymbol();
				System.err.println("processing "+ symbol);
				List<ShareDataADay> history = ShareHistoricDataReader.readFile(SharesManager.getSymbolFileHistoric(symbol));
				Map<LocalDate, Double> deliveredQuantityPercentageChange = Calculator.calculateDeliveredQuantityPercentageChange(history);			
				deliveredQuantityChangeFileWriter.writeDataForaSymbol(symbol,deliveredQuantityPercentageChange);
				
			};
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			try {
				deliveredQuantityChangeFileWriter.closeFile();
			}catch(Exception e) {
				e.printStackTrace();
			}
		}
		
	}
	
	public static void mergeDailyFile(String T_Minus1DayFile, String T_DayFile, LocalDate T_Date) {
		
		DeliveredQuantityChangeFileWriter deliveredQuantityChangeFileWriter=null;
		try {
			List<ShareDataADay> TMinus1SharesData = ShareDailyDataReader.readFile("C:\\Users\\sethi\\Desktop\\sharedata\\daily-data\\"+T_Minus1DayFile);
			Map<String, ShareDataADay> TMinus1DayTradedQuantity = TMinus1SharesData.stream().collect(Collectors.toMap(ShareDataADay::getSymbol, Function.identity()));
			
			List<ShareDataADay> TSharesData = ShareDailyDataReader.readFile("C:\\Users\\sethi\\Desktop\\sharedata\\daily-data\\"+T_DayFile);
			Map<String, ShareDataADay> TDayTradedQuantity = TSharesData.stream().collect(Collectors.toMap(ShareDataADay::getSymbol, Function.identity()));
			
			
			Map<String,Double> deliveredQuantityPercentageChangeForSymbols = new LinkedHashMap<String,Double>();
			for(Entry<String, ShareDataADay> sd: TMinus1DayTradedQuantity.entrySet()) {
				Double deliveredQuantityPercentageChange = Calculator.getDeliveredQtyPercentageChange(sd.getValue(),TDayTradedQuantity.get(sd.getKey()));
				deliveredQuantityPercentageChangeForSymbols.put(sd.getKey(), deliveredQuantityPercentageChange);
			}
			deliveredQuantityChangeFileWriter = new DeliveredQuantityChangeFileWriter();
			deliveredQuantityChangeFileWriter.writeDataForMultipleSymbolsOnADate(T_Date,deliveredQuantityPercentageChangeForSymbols);
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			try {
				if(deliveredQuantityChangeFileWriter!=null) deliveredQuantityChangeFileWriter.closeFile();
			}catch(Exception e) {
				e.printStackTrace();
			}
		}
		
		
	}	

}
