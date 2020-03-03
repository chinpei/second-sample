package com.example.pgsample;

import javax.sql.DataSource;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.SingleConnectionDataSource;

@SpringBootApplication
@Configuration
public class PgsampleApplication {

	public static void main(String[] args) throws InterruptedException {
//		SpringApplication.run(PgsampleApplication.class, args);
		
        try (ConfigurableApplicationContext ctx = new AnnotationConfigApplicationContext(
        		PgsampleApplication.class)) {
        	AppMain appMain = (AppMain)ctx.getBean("appMain");
        	while(true) {
            	appMain.startMain();
            	Thread.sleep(2000L);
        	}
        }
	}
	
	@Bean("appMain")
	public AppMain getMain() {
		return new AppMain();
	}
	@Bean
	public DataSource dataSource() {
//		String url = "jdbc:postgresql://localhost:5432/postgres";
		String host = System.getenv("POSTGRESQL02_SERVICE_HOST");
		String port = System.getenv("POSTGRESQL02_SERVICE_PORT");
		String url = "jdbc:postgresql://" + host + ":" + port + "/postgres";
		return new SingleConnectionDataSource(url, "pguser", "pgpass", true);
	}
	
	@Bean
	public JdbcTemplate jdbcTemplate(DataSource dataSource) {
		return new JdbcTemplate(dataSource);
	}

}
