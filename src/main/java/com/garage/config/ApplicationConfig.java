package com.garage.config;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JSR310Module;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.text.SimpleDateFormat;

/**
 * @author Ruslan Yaniuk
 * @date September 2015
 */
@Configuration
@ComponentScan(basePackages = {
        "com.garage.services"
})
@PropertySource("classpath:application.properties")
public class ApplicationConfig {

    public static final String PROPERTY_NAME_HIBERNATE_DIALECT = "hibernate.dialect";
    public static final String PROPERTY_NAME_HIBERNATE_SHOW_SQL = "hibernate.show_sql";
    public static final String PROPERTY_NAME_HIBERNATE_HBM2DDL_AUTO = "hibernate.hbm2ddl.auto";
    public static final String PROPERTY_NAME_HIBERNATE_EJB_NAMING_STRATEGY = "hibernate.ejb.naming_strategy";

    @Resource
    private Environment env;

    private String hibernateDialect;
    private String hibernateShowSql;
    private String hibernateHbm2ddlAuto;

    @PostConstruct
    private void init() {
        this.hibernateDialect = env.getRequiredProperty(PROPERTY_NAME_HIBERNATE_DIALECT);
        this.hibernateShowSql = env.getRequiredProperty(PROPERTY_NAME_HIBERNATE_SHOW_SQL);
        this.hibernateHbm2ddlAuto = env.getRequiredProperty(PROPERTY_NAME_HIBERNATE_HBM2DDL_AUTO);
    }

    @Bean
    public ObjectMapper jacksonObjectMapper() {
        ObjectMapper objectMapper = new ObjectMapper();

        objectMapper.registerModule(new JSR310Module());
        objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        objectMapper.setDateFormat(new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ"));
        return objectMapper;
    }

    @Bean
    public PasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    public String getHibernateDialect() {
        return hibernateDialect;
    }

    public String getHibernateShowSql() {
        return hibernateShowSql;
    }

    public String getHibernateHbm2ddlAuto() {
        return hibernateHbm2ddlAuto;
    }
}
