package com.fms;

import java.text.SimpleDateFormat;

import org.springframework.batch.item.excel.RowMapper;
import org.springframework.batch.item.excel.support.rowset.RowSet;
 
public class NotAttendedEventRowMapper implements RowMapper<VolunteerNotAttended> {
 
	SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yy");
	
	public VolunteerNotAttended mapRow(RowSet rowSet) throws Exception {
		
		VolunteerNotAttended noteAttended = new VolunteerNotAttended();
    	
    	String eventId 			= rowSet.getColumnValue(0);
		String eventname 		= rowSet.getColumnValue(1);
		String beneficiaryName	= rowSet.getColumnValue(2);
		String baseLocation		= rowSet.getColumnValue(3);
		String eventDate 		= rowSet.getColumnValue(4);
		String employeeId 		= rowSet.getColumnValue(5);
		
		noteAttended.setEventId(eventId);
		noteAttended.setEmployeeId(employeeId);
		noteAttended.setBaseLocation(baseLocation);
		noteAttended.setBeneficiaryName(beneficiaryName);
		noteAttended.setEventDate(dateFormat.parse(eventDate));
		noteAttended.setEventName(eventname);
    	
        return noteAttended;
    }
}
