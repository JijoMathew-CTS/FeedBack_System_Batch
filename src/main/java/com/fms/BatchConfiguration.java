
package com.fms;

import java.io.File;
import java.util.Date;

import javax.sql.DataSource;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.database.BeanPropertyItemSqlParameterSourceProvider;
import org.springframework.batch.item.database.JdbcBatchItemWriter;
import org.springframework.batch.item.excel.RowMapper;
import org.springframework.batch.item.excel.poi.PoiItemReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.EnvironmentAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.core.io.FileSystemResource;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

@Configuration
@EnableBatchProcessing
@EnableScheduling
//@PropertySource(value = { "classpath:application.properties" }, ignoreResourceNotFound = false)
public class BatchConfiguration
//implements EnvironmentAware 
{

	@Autowired
	public JobBuilderFactory jobBuilderFactory;

	@Autowired
	public StepBuilderFactory stepBuilderFactory;

	@Autowired
	public DataSource dataSource;
	
	
	@Autowired
    JobLauncher jobLauncher;

	@Autowired
	private Environment environment;
	

	@Bean
	public DataSource dataSource() {
		final DriverManagerDataSource dataSource = new DriverManagerDataSource();
		//environment.getProperty("datasource.driver-class-name");
		dataSource.setDriverClassName("com.mysql.cj.jdbc.Driver");
		//environment.getProperty("datasource.url")
		dataSource.setUrl("jdbc:mysql://localhost:3306/outreachfeedback");
		//environment.getProperty("datasource.username")
		dataSource.setUsername("root");
		//environment.getProperty("datasource.password")
		dataSource.setPassword("root");

		return dataSource;
	}
	

	
	@Bean
	ItemReader<EventSummaryEntity> eventSummaryReader() {
		PoiItemReader<EventSummaryEntity> reader = new PoiItemReader();
		reader.setLinesToSkip(1);
		reader.setResource(new FileSystemResource(new File("C:/excel/input/" + "Outreach_Events_Summary.xlsx")));
		reader.setRowMapper(eventSummaryRowMapper());
		return reader;
	}

	RowMapper<EventSummaryEntity> eventSummaryRowMapper() {
		return new SummaryEventRowMapper();
	}

	@Bean
	public JdbcBatchItemWriter<EventSummaryEntity> eventSummaryWriter() {
		JdbcBatchItemWriter<EventSummaryEntity> writer = new JdbcBatchItemWriter<EventSummaryEntity>();
		writer.setItemSqlParameterSourceProvider(new BeanPropertyItemSqlParameterSourceProvider<EventSummaryEntity>());
		String sql = "insert into event_summary(event_id,poc_id,poc_name,month,base_location,beneficiary_name,venue_address) "
				+ "values(:event_Id,:poc_Id,:poc_Name,:month,:base_location,:beneficiary_name,:venue_address)";

		writer.setSql(sql);
		writer.setDataSource(dataSource);

		return writer;
	}

	@Bean
	public SummaryItemProcessor summaryProcessor() {
		return new SummaryItemProcessor();
	}

	@Bean(name="summaryStep1")
	public Step step1() {
		return stepBuilderFactory.get("summaryStep1").<EventSummaryEntity, EventSummaryEntity>chunk(3).reader(eventSummaryReader())
				.processor(summaryProcessor()).writer(eventSummaryWriter()).build();
	}

	@Bean(name="importSummaryJob")
	public Job importSummaryJob() {
		return jobBuilderFactory.get("importSummaryJob").incrementer(new RunIdIncrementer()).flow(step1()).end().build();
	}
	
	@Scheduled(cron = "* */10 * * * *")
	public void performImportSummary() throws Exception {

		System.out.println("Job Started at :" + new Date());
		JobParameters param = new JobParametersBuilder().addString("JobID", String.valueOf(System.currentTimeMillis()))
				.toJobParameters();
		JobExecution execution = jobLauncher.run(importSummaryJob(), param);

		System.out.println("Job finished with status :" + execution.getStatus());
	}
	
	

	@Bean
	ItemReader<VolunteerNotAttended> enrollmentNotAttendedReader() {
		PoiItemReader<VolunteerNotAttended> reader = new PoiItemReader();
		reader.setLinesToSkip(1);
		reader.setResource(new FileSystemResource(new File("C:/excel/input/" + "Volunteer_Enorrlement_Details_Not_Attend.xlsx")));
		reader.setRowMapper(enrollmentNotAttendedRowMapper());
		return reader;
	}

	RowMapper<VolunteerNotAttended> enrollmentNotAttendedRowMapper() {
		return new NotAttendedEventRowMapper();
	}

	@Bean
	public JdbcBatchItemWriter<VolunteerNotAttended> enrollmentNotAttendedWriter() {
		JdbcBatchItemWriter<VolunteerNotAttended> writer = new JdbcBatchItemWriter<VolunteerNotAttended>();
		writer.setItemSqlParameterSourceProvider(new BeanPropertyItemSqlParameterSourceProvider<VolunteerNotAttended>());
		String sql = "insert into vol_event_not_attended(event_id,employee_id,base_location,beneficiary_name,event_name,employee_name,email_status,month,council_name,event_date) "
				+ "values(:eventId,:employeeId,:baseLocation,:beneficiaryName,:eventName,:employeeName,:emailStatus,:month,:councilName,:eventDate)";

		writer.setSql(sql);
		writer.setDataSource(dataSource);

		return writer;
	}

	@Bean
	public NotAttendedItemProcessor enrollmentNotAttendedProcessor() {
		return new NotAttendedItemProcessor();
	}

	@Bean(name="enrollmentNotAttendedStep1")
	public Step enrollmentNotAttendedStep1() {
		return stepBuilderFactory.get("enrollmentNotAttendedStep1").<VolunteerNotAttended, VolunteerNotAttended>chunk(3).reader(enrollmentNotAttendedReader())
				.processor(enrollmentNotAttendedProcessor()).writer(enrollmentNotAttendedWriter()).build();
	}

	@Bean(name="enrollmentNotAttendedJob")
	public Job enrollmentNotAttendedJob() {
		return jobBuilderFactory.get("enrollmentNotAttendedJob").incrementer(new RunIdIncrementer()).flow(enrollmentNotAttendedStep1()).end().build();
	}
	
	@Scheduled(cron = "*/10 * * * * *")
	public void performEnrollmentNotAttended() throws Exception {

		System.out.println("Job Started at :" + new Date());
		JobParameters param = new JobParametersBuilder().addString("JobID", String.valueOf(System.currentTimeMillis()))
				.toJobParameters();
		JobExecution execution = jobLauncher.run(importSummaryJob(), param);

		System.out.println("Job finished with status :" + execution.getStatus());
	}



	
	

}