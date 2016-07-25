package com.garage.config;

import org.hibernate.cfg.ImprovedNamingStrategy;
import org.hibernate.jpa.HibernatePersistenceProvider;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.AbstractJpaVendorAdapter;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;
import java.util.Properties;

import static com.garage.config.ApplicationConfig.*;

/**
 * @author Ruslan Yaniuk
 * @date July 2015
 */
@Configuration
@EnableTransactionManagement
@EnableJpaRepositories("com.garage.repositories")
public class JpaConfig {

    public static final String PACKAGES_TO_SCAN = "com.garage.models";

    @Bean
    public DataSource dataSource() {
        EmbeddedDatabaseBuilder builder = new EmbeddedDatabaseBuilder();
        return builder.setType(EmbeddedDatabaseType.H2).build();
    }

    @Bean
    public LocalContainerEntityManagerFactoryBean entityManagerFactory(Properties hibProperties,
                                                                       DataSource dataSource,
                                                                       AbstractJpaVendorAdapter jpaVendorAdapter) {
        LocalContainerEntityManagerFactoryBean entityManagerFactory = new LocalContainerEntityManagerFactoryBean();

        entityManagerFactory.setDataSource(dataSource);
        entityManagerFactory.setPersistenceProviderClass(HibernatePersistenceProvider.class);
        entityManagerFactory.setPackagesToScan(PACKAGES_TO_SCAN);
        entityManagerFactory.setJpaVendorAdapter(jpaVendorAdapter);
        entityManagerFactory.setJpaProperties(hibProperties);
        return entityManagerFactory;
    }

    @Bean
    public AbstractJpaVendorAdapter jpaVendorAdapter() {
        HibernateJpaVendorAdapter jpaVendorAdapter = new HibernateJpaVendorAdapter();

        jpaVendorAdapter.setGenerateDdl(false);
        return jpaVendorAdapter;
    }

    @Bean
    public JpaTransactionManager transactionManager(EntityManagerFactory emf) {
        JpaTransactionManager transactionManager = new JpaTransactionManager();

        transactionManager.setEntityManagerFactory(emf);
        return transactionManager;
    }

    @Bean
    public Properties hibProperties(ApplicationConfig applicationProperties) {
        Properties properties = new Properties();

        properties.put(PROPERTY_NAME_HIBERNATE_DIALECT, applicationProperties.getHibernateDialect());
        properties.put(PROPERTY_NAME_HIBERNATE_SHOW_SQL, applicationProperties.getHibernateShowSql());
        properties.put(PROPERTY_NAME_HIBERNATE_HBM2DDL_AUTO, applicationProperties.getHibernateHbm2ddlAuto());
        properties.put(PROPERTY_NAME_HIBERNATE_EJB_NAMING_STRATEGY, ImprovedNamingStrategy.class.getCanonicalName());
        return properties;
    }
}
