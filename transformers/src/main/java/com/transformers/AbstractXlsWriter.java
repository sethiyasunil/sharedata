package com.transformers;

import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.CreationHelper;
import org.apache.poi.ss.usermodel.Workbook;

public class AbstractXlsWriter  {
	
	
	CellStyle formatDecimalStyle(Workbook workbook, CreationHelper createHelper) {  
	    CellStyle style = workbook.createCellStyle();
	    style.setDataFormat(createHelper.createDataFormat().getFormat("0.00"));
	    return style;   
	}

}