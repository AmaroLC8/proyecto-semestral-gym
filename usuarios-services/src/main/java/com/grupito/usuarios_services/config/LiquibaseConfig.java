package com.grupito.usuarios_services.config;

import javax.sql.DataSource;

import org.springframework.context.annotation.Bean;

import liquibase.integration.spring.SpringLiquibase;

public class LiquibaseConfig {
    @Bean
    public SpringLiquibase liquibase(DataSource dataSource){
        SpringLiquibase liquibase = new SpringLiquibase();
        liquibase.setDataSource(dataSource);
        liquibase.setChangeLog("clashpath:db/changelog/db.changelog.sql");
        liquibase.setShouldRun(true);
        return liquibase;
    }
}
