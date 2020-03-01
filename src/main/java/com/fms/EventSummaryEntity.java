package com.fms;


import java.util.Date;

public class EventSummaryEntity {

	private Long id;
	
	private String event_Id;
	private String poc_Id;
	private String poc_Name;
	private String month;
	private String base_location;
	private String beneficiary_name;
	private String venue_address;
	private String council_name;
	private String project;
	private String catagory;
	private String event_name;
	private String event_description;
	private Date event_date;
	private Integer total_vol_no;
	private Integer total_vol_hrs;
	private Integer total_travel_hrs;
	private Integer overall_vol_hrs;
	private Integer lives_impact;
	private String activity_type;
	private String status;
	private Integer poc_contact;
	
	public String getMonth() {
		return month;
	}

	public void setMonth(String month) {
		this.month = month;
	}

	public String getBase_location() {
		return base_location;
	}

	public void setBase_location(String base_location) {
		this.base_location = base_location;
	}

	public String getBeneficiary_name() {
		return beneficiary_name;
	}

	public void setBeneficiary_name(String beneficiary_name) {
		this.beneficiary_name = beneficiary_name;
	}

	

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	
	public String getVenue_address() {
		return venue_address;
	}

	public void setVenue_address(String venue_address) {
		this.venue_address = venue_address;
	}

	public String getCouncil_name() {
		return council_name;
	}

	public void setCouncil_name(String council_name) {
		this.council_name = council_name;
	}

	public String getProject() {
		return project;
	}

	public void setProject(String project) {
		this.project = project;
	}

	public String getCatagory() {
		return catagory;
	}

	public void setCatagory(String catagory) {
		this.catagory = catagory;
	}

	public String getEvent_name() {
		return event_name;
	}

	public void setEvent_name(String event_name) {
		this.event_name = event_name;
	}

	public String getEvent_description() {
		return event_description;
	}

	public void setEvent_description(String event_description) {
		this.event_description = event_description;
	}

	public Date getEvent_date() {
		return event_date;
	}

	public void setEvent_date(Date event_date) {
		this.event_date = event_date;
	}

	public Integer getTotal_vol_no() {
		return total_vol_no;
	}

	public void setTotal_vol_no(Integer total_vol_no) {
		this.total_vol_no = total_vol_no;
	}

	public Integer getTotal_vol_hrs() {
		return total_vol_hrs;
	}

	public void setTotal_vol_hrs(Integer total_vol_hrs) {
		this.total_vol_hrs = total_vol_hrs;
	}

	public Integer getTotal_travel_hrs() {
		return total_travel_hrs;
	}

	public void setTotal_travel_hrs(Integer total_travel_hrs) {
		this.total_travel_hrs = total_travel_hrs;
	}

	public Integer getOverall_vol_hrs() {
		return overall_vol_hrs;
	}

	public void setOverall_vol_hrs(Integer overall_vol_hrs) {
		this.overall_vol_hrs = overall_vol_hrs;
	}

	public Integer getLives_impact() {
		return lives_impact;
	}

	public void setLives_impact(Integer lives_impact) {
		this.lives_impact = lives_impact;
	}

	public String getActivity_type() {
		return activity_type;
	}

	public void setActivity_type(String activity_type) {
		this.activity_type = activity_type;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Integer getPoc_contact() {
		return poc_contact;
	}

	public void setPoc_contact(Integer poc_contact) {
		this.poc_contact = poc_contact;
	}

	public EventSummaryEntity() {
		super();
		// TODO Auto-generated constructor stub
	}

	public String getEvent_Id() {
		return event_Id;
	}

	public void setEvent_Id(String event_Id) {
		this.event_Id = event_Id;
	}

	public String getPoc_Id() {
		return poc_Id;
	}

	public void setPoc_Id(String poc_Id) {
		this.poc_Id = poc_Id;
	}

	public String getPoc_Name() {
		return poc_Name;
	}

	public void setPoc_Name(String poc_Name) {
		this.poc_Name = poc_Name;
	}

	public EventSummaryEntity(Long id, String event_Id, String poc_Id,
			String poc_Name, String month, String base_location,
			String beneficiary_name, String venue_address, String council_name,
			String project, String catagory, String event_name,
			String event_description, Date event_date, Integer total_vol_no,
			Integer total_vol_hrs, Integer total_travel_hrs,
			Integer overall_vol_hrs, Integer lives_impact,
			String activity_type, String status, Integer poc_contact) {
		super();
		this.id = id;
		this.event_Id = event_Id;
		this.poc_Id = poc_Id;
		this.poc_Name = poc_Name;
		this.month = month;
		this.base_location = base_location;
		this.beneficiary_name = beneficiary_name;
		this.venue_address = venue_address;
		this.council_name = council_name;
		this.project = project;
		this.catagory = catagory;
		this.event_name = event_name;
		this.event_description = event_description;
		this.event_date = event_date;
		this.total_vol_no = total_vol_no;
		this.total_vol_hrs = total_vol_hrs;
		this.total_travel_hrs = total_travel_hrs;
		this.overall_vol_hrs = overall_vol_hrs;
		this.lives_impact = lives_impact;
		this.activity_type = activity_type;
		this.status = status;
		this.poc_contact = poc_contact;
	}

	
	

	/*public EventSummaryEntity(String event_Id, String poc_Id, String poc_Name) {
		super();
		this.event_Id = event_Id;
		this.poc_Id = poc_Id;
		this.poc_Name = poc_Name;
	}
*/
	

}
