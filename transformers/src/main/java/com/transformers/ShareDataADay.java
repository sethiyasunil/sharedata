package com.transformers;

import java.time.LocalDate;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Builder
public class ShareDataADay {
	
	private String symbol;
	private String series;
	private LocalDate date;
	private Float prevClose;
	private Float openPrice;
	private Float highPrice;
	private Float lowPrice;
	private Float lastPrice;
	private Float closePrice;
	private Float averagePrice;
	private Long totalTradedQuantity;
	private Float turnover;
	private Long  numberOfTrades;
	private Long deliverableQty;
	private Float percentageDlyQtToTradedQty;
	private String isin;
	
}
