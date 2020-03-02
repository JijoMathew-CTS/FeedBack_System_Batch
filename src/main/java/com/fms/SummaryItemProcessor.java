package com.fms;

import org.springframework.batch.item.ItemProcessor;

public class SummaryItemProcessor implements ItemProcessor<EventSummaryEntity, EventSummaryEntity> {

	public EventSummaryEntity process(EventSummaryEntity eventSummaryEntity) throws Exception {
	 
  return eventSummaryEntity;
 }

} 