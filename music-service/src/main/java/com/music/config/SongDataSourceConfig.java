package com.music.config;

import com.music.song.domain.SongEntity;
import jakarta.persistence.EntityManagerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;

@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(basePackages = "com.music.song.repository",
        entityManagerFactoryRef = "songEntityManagerFactory",
        transactionManagerRef = "songTransactionManager")
public class SongDataSourceConfig {

    @Bean(name = "songProperties")
    @ConfigurationProperties("spring.datasource.song")
    public DataSourceProperties datasourceProperties() {
        return new DataSourceProperties();
    }

    @Bean(name = "songDataSource")
    public DataSource dataSource(@Qualifier("songProperties") DataSourceProperties properties) {
        return properties.initializeDataSourceBuilder().build();
    }

    @Bean(name = "songEntityManagerFactory")
    public LocalContainerEntityManagerFactoryBean entityManagerFactoryBean(EntityManagerFactoryBuilder builder,
                                                                           @Qualifier("songDataSource") DataSource dataSource) {
        return builder
                .dataSource(dataSource)
                .packages(SongEntity.class)
                .build();
    }

    @Bean(name = "songTransactionManager")
    public PlatformTransactionManager transactionManager(
            @Qualifier("songEntityManagerFactory") EntityManagerFactory entityManagerFactory) {
        return new JpaTransactionManager(entityManagerFactory);
    }

}
