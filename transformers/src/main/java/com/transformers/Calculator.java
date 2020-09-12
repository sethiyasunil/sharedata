package com.transformers;

import java.io.File;
import java.io.FileReader;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.stream.Collectors;

import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;

public class Calculator {

	public static Map<LocalDate,Float> calculatePricePercentageChange(List<ShareDataADay> history) throws Exception {
		
		//check all shares are same
		//Set<String> symbols = history.stream().map(sd-> sd.getSymbol()).collect(Collectors.toSet());
		//if(symbols.size()>1) throw new RuntimeException("All symbols should be same "+ symbols);
		
		
		Map<LocalDate,Float> percentageIncrease = new LinkedHashMap<LocalDate,Float>();
		for(ShareDataADay sd: history) {
			Float value = calculatePricePercentageChange(sd);
			percentageIncrease.put(sd.getDate(), value);
		}
		return percentageIncrease;
	}


	public static Float calculatePricePercentageChange(ShareDataADay sd) {
		Float value = null;
		if(sd.getClosePrice()!=null  && sd.getPrevClose()!=null) {
			value = (sd.getClosePrice()-sd.getPrevClose())*100/sd.getPrevClose();				
		}
		return value;
	}
	
	
	public static Map<LocalDate,Double> calculateTradedQuantityPercentageChange(List<ShareDataADay> history) throws Exception {
		
		//check all shares are same
		//Set<String> symbols = history.stream().map(sd-> sd.getSymbol()).collect(Collectors.toSet());
		//if(symbols.size()>1) throw new RuntimeException("All symbols should be same "+ symbols);
		
		Map<LocalDate,Double> tradedQuantityChange = new TreeMap<LocalDate,Double>();
		Iterator<ShareDataADay> itr = history.iterator();
		ShareDataADay d1 = itr.next();
		while(itr.hasNext()) {
			ShareDataADay d2 = itr.next();
			Double value = calculateTotalTradedQuantityPercentageChange(d1,d2);
			tradedQuantityChange.put(d2.getDate(), value);
			d1 = d2;
		}

		return tradedQuantityChange;
	}


	public static Double calculateTotalTradedQuantityPercentageChange(ShareDataADay d1, ShareDataADay d2) {
		Double value=null;
		if(d1.getTotalTradedQuantity()!=null  && d2.getTotalTradedQuantity()!=null) {
			value = (d2.getTotalTradedQuantity() - d1.getTotalTradedQuantity())*100.0/d1.getTotalTradedQuantity();	
		}
		return value;
	}
}
