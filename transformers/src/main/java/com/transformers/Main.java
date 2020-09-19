package com.transformers;

import java.time.LocalDate;
import java.util.Map;

public class Main {
	
	
	public static void main(String[] args) {
		
		//PricePercentageChangeHandler.processHistoricData();
		//PricePercentageChangeHandler.mergeDailyFile("18-sep-2020sec_bhavdata_full.csv");	
		
		
		//TradedQuantityPercentageChangeHandler.processHistoricData();
		//TradedQuantityPercentageChangeHandler.mergeDailyFile("17-sep-2020sec_bhavdata_full.csv", "18-sep-2020sec_bhavdata_full.csv",LocalDate.of(2020, 9, 18));
		
		DeliveredQuantityPercentageChangeHandler.processHistoricData();
		//DeliveredQuantityPercentageChangeHandler.mergeDailyFile("09-sep-2020sec_bhavdata_full.csv", "10-sep-2020sec_bhavdata_full.csv",LocalDate.of(2020, 9, 10));

	}
}
