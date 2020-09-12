package com.transformers;

import static com.transformers.DateUtil.dateAsString;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.CreationHelper;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

public class TradedQuantityChangeFileWriter extends AbstractXlsWriter{
	
	private final String TEMP_FILE = "C:\\Users\\sethi\\Desktop\\sharedata\\historic-data\\_Temp.xls";
	private final String SUMMARY_FILE = "C:\\Users\\sethi\\Desktop\\sharedata\\_TradedQuantityPercentageChange.xlsx";
	static Workbook workbook=null;
	static Sheet tradedQuantityPercentageChange =null;
	Map<String, Integer> headers=null;
	FileInputStream excelFile=null;
	CellStyle doubleStyle=null;
	
	public TradedQuantityChangeFileWriter() throws Exception {
		excelFile = new FileInputStream(new File(SUMMARY_FILE));
		workbook = WorkbookFactory.create(excelFile);
        tradedQuantityPercentageChange = workbook.getSheet("TradedQuantityPercentageChange");	
        headers = getHeaders();        
        
        doubleStyle = formatDecimalStyle(workbook, tradedQuantityPercentageChange.getWorkbook().getCreationHelper());
	}
	

	
	public void writeDataForaSymbol(String symbol, Map<LocalDate, Double> tradedQuantityPercentageChange) throws Exception {

        Row row= getRow(symbol);
        if(row==null) row = addRow(symbol);
        
        for(Entry<LocalDate,Double> e: tradedQuantityPercentageChange.entrySet()) {
        	Integer cellIndex = headers.get(dateAsString(e.getKey()));
			Cell cell = row.getCell(cellIndex);
			cell.setCellStyle(doubleStyle);
			cell.setCellValue(e.getValue());
			
        }
	}
	
	public void closeFile() throws Exception{
			excelFile.close();
			FileOutputStream outputStream = new FileOutputStream(TEMP_FILE);
            workbook.write(outputStream);
            outputStream.close();
            workbook.close();
			Files.delete(Paths.get(SUMMARY_FILE));
			Files.move(Paths.get(TEMP_FILE),Paths.get(SUMMARY_FILE));		
	}

	private Row addRow(String symbol) {
		int i=0;
		for(Row row: tradedQuantityPercentageChange) i++;
		Row row = tradedQuantityPercentageChange.createRow(i);
		row.createCell(0).setCellValue(symbol);
		for(i=1;i<headers.size();i++) row.createCell(i);
		return row;
	}

	private Map<String,Integer> getHeaders() {
		Map<String,Integer> keyIndex = new HashMap<String,Integer>();
        int i=0;
        for(Cell c : tradedQuantityPercentageChange.getRow(0)) { 
        	keyIndex.put(c.getStringCellValue(), i++);
        }
        return keyIndex;
	}

	private Row getRow(String symbol) {
		Row row=null;
		for(Row r: tradedQuantityPercentageChange) {
        	if(r.getCell(0).getStringCellValue().equals(symbol)) {row= r;break;}
        }
		return row;
	}

	//date - (symbol - tradedQuantityPercentageChange)
	public void writeDataForMultipleSymbolsOnADate(LocalDate date, Map<String, Double> tradedQuantityPercentageChange) throws IOException {
		
		Integer cellIndex = headers.get(dateAsString(date));
		for(Entry<String,Double> e: tradedQuantityPercentageChange.entrySet()) {
	        Row row= getRow(e.getKey());
	        if(row==null) row = addRow(e.getKey());
	        Cell cell = row.getCell(cellIndex);
	        if(cell==null) {
	        	cell = row.createCell(cellIndex);
		        cell.setCellStyle(doubleStyle);
	        }	        
			cell.setCellValue(e.getValue());	
		}
	}
	

}
