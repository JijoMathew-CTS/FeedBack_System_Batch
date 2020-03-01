package com.fms;

import org.springframework.batch.item.excel.RowMapper;
import org.springframework.batch.item.excel.support.rowset.RowSet;
 
public class SummaryEventRowMapper implements RowMapper<EventSummaryEntity> {
 
	@Override
    public EventSummaryEntity mapRow(RowSet rowSet) throws Exception {
    	EventSummaryEntity eventSummary = new EventSummaryEntity();
    	eventSummary.setEvent_Id(rowSet.getColumnValue(0));
    	eventSummary.setMonth(rowSet.getColumnValue(1));
    	eventSummary.setBase_location(rowSet.getColumnValue(2));
    	eventSummary.setBeneficiary_name(rowSet.getColumnValue(3));
    	eventSummary.setVenue_address(rowSet.getColumnValue(4));
    	eventSummary.setPoc_Id(rowSet.getColumnValue(19));
    	eventSummary.setPoc_Name(rowSet.getColumnValue(20));
        return eventSummary;
    }
}
