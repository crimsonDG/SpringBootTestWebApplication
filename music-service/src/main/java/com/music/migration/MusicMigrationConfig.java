package com.music.migration;

import org.flywaydb.core.Flyway;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import javax.sql.DataSource;

@Configuration
public class MusicMigrationConfig {

    @Value("${migration.locations.label}")
    private String labelLocation;

    @Value("${migration.locations.song}")
    private String songLocation;

    @Bean
    public Flyway songFlyway(@Qualifier("songDataSource") DataSource dataSource) {
        Flyway flyway = Flyway.configure()
                .dataSource(dataSource)
                .locations(songLocation)
                .load();
        flyway.migrate();
        return flyway;
    }

    @Primary
    @Bean
    public Flyway labelFlyway(@Qualifier("labelDataSource") DataSource dataSource) {
        Flyway flyway = Flyway.configure()
                .dataSource(dataSource)
                .locations(labelLocation)
                .load();
        flyway.migrate();
        return flyway;
    }
}
