package com.transformers;

import java.time.LocalDate;
import java.util.Map;

public class Main {
	
	
	public static void main(String[] args) {
		
		System.err.println(SharesManager.getAllShared().size());
		PricePercentageChangeHandler.processHistoricData();
		PricePercentageChangeHandler.mergeDailyFile("09-sep-2020sec_bhavdata_full.csv");	
		PricePercentageChangeHandler.mergeDailyFile("10-sep-2020sec_bhavdata_full.csv");	
		PricePercentageChangeHandler.mergeDailyFile("11-sep-2020sec_bhavdata_full.csv");
		
		
		//TradesQuantityPercentageChangeHandler.processHistoricData();
		//TradesQuantityPercentageChangeHandler.mergeDailyFile("09-sep-2020sec_bhavdata_full.csv", "10-sep-2020sec_bhavdata_full.csv",LocalDate.of(2020, 9, 10));	
	}
}
