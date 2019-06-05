package com.cga.weather_report.utils;

import java.util.Properties;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceTransactionManagerAutoConfiguration;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.hibernate5.HibernateTransactionManager;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableAutoConfiguration(exclude = {DataSourceAutoConfiguration.class, DataSourceTransactionManagerAutoConfiguration.class, HibernateJpaAutoConfiguration.class})
@EnableTransactionManagement
public class DataBaseConfiguration {
		
		@Bean
		public LocalSessionFactoryBean sessionFactory(){
			LocalSessionFactoryBean sessionFactoryBean = new LocalSessionFactoryBean();
			sessionFactoryBean.setDataSource(dataSource());
			sessionFactoryBean.setPackagesToScan("com.cga");
			sessionFactoryBean.setHibernateProperties(hibernateProperties());
			return sessionFactoryBean;
		}
		@Bean
		public DataSource dataSource(){
			DriverManagerDataSource dataSource = new DriverManagerDataSource();
			dataSource.setDriverClassName("org.h2.Driver");
			dataSource.setUrl("jdbc:h2:mem:weatherDB;INIT=RUNSCRIPT FROM 'classpath:data.sql'");
			dataSource.setUsername("WeatherReport");
			dataSource.setPassword("MeLi");
			
			
			return dataSource;
		}
		
		public Properties hibernateProperties(){
			Properties properties = new Properties();
			properties.put("hibernate.dialect", "org.hibernate.dialect.H2Dialect");
			properties.put("spring.datasource.initialization-mode", "always");
			return properties;
		}
		@Bean
		@Autowired
		public HibernateTransactionManager transactionManager(){
				HibernateTransactionManager hibernateTransactionManager = new HibernateTransactionManager();
				hibernateTransactionManager.setSessionFactory(sessionFactory().getObject());	
			return hibernateTransactionManager;
		}
		
}