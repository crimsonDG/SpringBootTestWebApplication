package com.music.config;

import com.music.label.domain.LabelEntity;
import jakarta.persistence.EntityManagerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;

@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(basePackages = "com.music.label.repository",
        entityManagerFactoryRef = "labelEntityManagerFactory",
        transactionManagerRef = "labelTransactionManager")
public class LabelDataSourceConfig {

    @Primary
    @Bean(name = "labelProperties")
    @ConfigurationProperties("spring.datasource.label")
    public DataSourceProperties datasourceProperties() {
        return new DataSourceProperties();
    }

    @Primary
    @Bean(name = "labelDataSource")
    public DataSource dataSource(@Qualifier("labelProperties") DataSourceProperties properties) {
        return properties.initializeDataSourceBuilder().build();
    }

    @Primary
    @Bean(name = "labelEntityManagerFactory")
    public LocalContainerEntityManagerFactoryBean entityManagerFactoryBean(EntityManagerFactoryBuilder builder,
                                                                           @Qualifier("labelDataSource") DataSource dataSource) {
        return builder
                .dataSource(dataSource)
                .packages(LabelEntity.class)
                .build();
    }

    @Primary
    @Bean(name = "labelTransactionManager")
    public PlatformTransactionManager transactionManager(
            @Qualifier("labelEntityManagerFactory") EntityManagerFactory entityManagerFactory) {
        return new JpaTransactionManager(entityManagerFactory);
    }
}
