package com.codyperry.reading_tracker.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import javax.sql.DataSource;

@Configuration
public class DatasourceConfiguration {
    @Autowired
    private Environment environment;

    @Bean
    public DataSource dataSource() {
        final DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName(environment.getProperty("com.codyperry.reading_tracker.database.driverClass"));
        dataSource.setUrl(environment.getProperty("com.codyperry.reading_tracker.database.url"));
        dataSource.setUsername(environment.getProperty("com.codyperry.reading_tracker.database.username"));
        dataSource.setPassword(environment.getProperty("com.codyperry.reading_tracker.database.password"));

        return dataSource;
    }
}
