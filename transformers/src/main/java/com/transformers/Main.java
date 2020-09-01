package com.transformers;

import java.time.LocalDate;
import java.util.Map;

public class Main {
	
	
	public static void main(String[] args) {
		//PricePercentageChangeHandler.processHistoricData();
		PricePercentageChangeHandler.mergeDailyFile("cm31AUG2020bhav.csv");//"cm31AUG2020bhav.csv");		

		
		//TradesQuantityPercentageChangeHandler.processHistoricData();
		//TradesQuantityPercentageChangeHandler.mergeDailyFile("cm28AUG2020bhav.csv","cm31AUG2020bhav.csv", LocalDate.of(2020, 8,31));
	}
}
