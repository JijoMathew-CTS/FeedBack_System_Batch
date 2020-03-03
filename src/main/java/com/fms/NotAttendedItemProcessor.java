package com.fms;

import org.springframework.batch.item.ItemProcessor;

public class NotAttendedItemProcessor implements ItemProcessor<VolunteerNotAttended, VolunteerNotAttended> {

	public VolunteerNotAttended process(VolunteerNotAttended eventSummaryEntity) throws Exception {
	 
  return eventSummaryEntity;
 }

} 