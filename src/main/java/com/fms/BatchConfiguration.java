
package com.fms;

import java.io.File;

import javax.sql.DataSource;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.database.BeanPropertyItemSqlParameterSourceProvider;
import org.springframework.batch.item.database.JdbcBatchItemWriter;
import org.springframework.batch.item.excel.RowMapper;
import org.springframework.batch.item.excel.poi.PoiItemReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.core.io.FileSystemResource;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

//**Added today 02/03/2020
import org.springframework.batch.core.launch.support.SimpleJobLauncher;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.scheduling.annotation.Scheduled;

@Configuration
@EnableBatchProcessing
public class BatchConfiguration {

	@Autowired
	public JobBuilderFactory jobBuilderFactory;

	@Autowired
	public StepBuilderFactory stepBuilderFactory;

	@Autowired
	public DataSource dataSource;
	
	//**Added today 02/03/2020
	@Autowired
	private SimpleJobLauncher jobLauncher;

	@Autowired
	private Environment environment;

	@Bean
	public DataSource dataSource() {
		final DriverManagerDataSource dataSource = new DriverManagerDataSource();
		//environment.getProperty("datasource.driver-class-name")
		dataSource.setDriverClassName("com.mysql.cj.jdbc.Driver");
		//environment.getProperty("datasource.url")
		dataSource.setUrl("jdbc:mysql://localhost:3306/outreachfeedback");
		//environment.getProperty("datasource.username")
		dataSource.setUsername("root");
		//environment.getProperty("datasource.password")
		dataSource.setPassword("root");

		return dataSource;
	}
	
	/*
	 * @Bean public FlatFileItemReader<User> reader(){ FlatFileItemReader<User>
	 * reader = new FlatFileItemReader<User>(); reader.setResource(new
	 * ClassPathResource("users.csv")); reader.setLineMapper(new
	 * DefaultLineMapper<User>() {{ setLineTokenizer(new DelimitedLineTokenizer() {{
	 * setNames(new String[] { "name" }); }}); setFieldSetMapper(new
	 * BeanWrapperFieldSetMapper<User>() {{ setTargetType(User.class); }});
	 * 
	 * }});
	 * 
	 * return reader; }
	 * 
	 * @Bean public UserItemProcessor processor(){ return new UserItemProcessor(); }
	 * 
	 * @Bean public JdbcBatchItemWriter<User> writer(){ JdbcBatchItemWriter<User>
	 * writer = new JdbcBatchItemWriter<User>();
	 * writer.setItemSqlParameterSourceProvider(new
	 * BeanPropertyItemSqlParameterSourceProvider<User>());
	 * writer.setSql("INSERT INTO outreachfeedback.user(name) VALUES (:name)");
	 * writer.setDataSource(dataSource);
	 * 
	 * return writer; }
	 * 
	 * @Bean public Step step1() { return stepBuilderFactory.get("step1").<User,
	 * User> chunk(3) .reader(reader()) .processor(processor()) .writer(writer())
	 * .build(); }
	 * 
	 * @Bean public Job importUserJob() { return
	 * jobBuilderFactory.get("importUserJob") .incrementer(new RunIdIncrementer())
	 * .flow(step1()) .end() .build(); }
	 */

	
	@Bean
	ItemReader<EventSummaryEntity> reader() {
		PoiItemReader<EventSummaryEntity> reader = new PoiItemReader();
		reader.setLinesToSkip(1);
		reader.setResource(new FileSystemResource(new File("C:/excel/input/" + "Outreach_Events_Summary.xlsx")));
		reader.setRowMapper(excelRowMapper());
		return reader;
	}

	RowMapper<EventSummaryEntity> excelRowMapper() {
		return new SummaryEventRowMapper();
	}

	@Bean
	public JdbcBatchItemWriter<EventSummaryEntity> writer() {
		JdbcBatchItemWriter<EventSummaryEntity> writer = new JdbcBatchItemWriter<EventSummaryEntity>();
		writer.setItemSqlParameterSourceProvider(new BeanPropertyItemSqlParameterSourceProvider<EventSummaryEntity>());
		String sql = "insert into event_summary(event_id,poc_id,poc_name,month,base_location,beneficiary_name,venue_address) "
				+ "values(:event_Id,:poc_Id,:poc_Name,:month,:base_location,:beneficiary_name,:venue_address)";

		writer.setSql(sql);
		writer.setDataSource(dataSource);

		return writer;
	}

	@Bean
	public SummaryItemProcessor processor() {
		return new SummaryItemProcessor();
	}

	@Bean
	public Step step1() {
		return stepBuilderFactory.get("step1").<EventSummaryEntity, EventSummaryEntity>chunk(3).reader(reader())
				.processor(processor()).writer(writer()).build();
	}

	@Bean
	public Job importSummaryJob() {
		return jobBuilderFactory.get("importSummaryJob").incrementer(new RunIdIncrementer()).flow(step1()).end().build();
	}
	
	//**Added today 02/03/2020
	@Scheduled(cron = "*/5 * * * * *")
	public void perform() throws Exception {

		System.out.println("Job Started at :" + new Date());
		JobParameters param = new JobParametersBuilder().addString("JobID", String.valueOf(System.currentTimeMillis()))
				.toJobParameters();

		JobExecution execution = jobLauncher.run(importSummaryJob(), param);

		System.out.println("Job finished with status :" + execution.getStatus());
	}

}