package com.cga.weather_report.utils;

import java.util.Properties;

import javax.sql.DataSource;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceTransactionManagerAutoConfiguration;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.hibernate5.HibernateTransactionManager;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * @author Cesar Amadori
 *
 */
@Configuration
@EnableAutoConfiguration(exclude = {DataSourceAutoConfiguration.class, DataSourceTransactionManagerAutoConfiguration.class, HibernateJpaAutoConfiguration.class})
@EnableTransactionManagement
public class DataBaseConfiguration {
	
	/**
	 * <p><i>Generates the session bean to connect to database</i></p>
	 *
	 * @return the session bean
	 */
    @Bean
    public LocalSessionFactoryBean sessionFactory() {
        LocalSessionFactoryBean sessionFactory = new LocalSessionFactoryBean();
        sessionFactory.setDataSource(dataSource());
        sessionFactory.setPackagesToScan("com.cga.weather_report");
        sessionFactory.setHibernateProperties(hibernateProperties());
 
        return sessionFactory;
    }
    
	/**
	 * <p><i>Generates the data source bean with database configuration</i></p>
	 *
	 * @return the database configuration bean
	 */
    @Bean
    public DataSource dataSource() {
    	DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName("org.h2.Driver");
        dataSource.setUrl("jdbc:h2:mem:weatherDB;DB_CLOSE_DELAY=-1");
        dataSource.setUsername("WeatherReport");
        dataSource.setPassword("MeLi");
 
        return dataSource;
    }
    
	/**
	 * <p><i>Generates the hibernate bean for the session</i></p>
	 *
	 * @return the hibernate transaction bean
	 */
    @Bean
    public PlatformTransactionManager hibernateTransactionManager() {
        HibernateTransactionManager transactionManager
          = new HibernateTransactionManager();
        transactionManager.setSessionFactory(sessionFactory().getObject());
        return transactionManager;
    }
    
	/**
	 * <p><i>Defines the hibernate properties used in the session</i></p>
	 *
	 * @return the hibernate properties
	 */
    private final Properties hibernateProperties() {
        Properties hibernateProperties = new Properties();
        hibernateProperties.setProperty(
          "hibernate.hbm2ddl.auto", "create-drop");
        hibernateProperties.setProperty(
          "hibernate.dialect", "org.hibernate.dialect.H2Dialect");
 
        return hibernateProperties;
    }	
}