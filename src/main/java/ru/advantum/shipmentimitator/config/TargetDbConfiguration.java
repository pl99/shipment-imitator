package ru.advantum.shipmentimitator.config;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(entityManagerFactoryRef = "targetEntityManagerFactory",
        basePackages = {"ru.advantum.shipmentimitator.targetdb"})
@FieldDefaults(level = AccessLevel.PRIVATE)
public class TargetDbConfiguration {

    @Value("${targetdatasource.datasource.url}")
    String url;
    @Value("${targetdatasource.datasource.username}")
    String username;
    @Value("${targetdatasource.datasource.password}")
    String password;

    @Primary
    @Bean(name = "targetDataSource")
    public DataSource dataSource() {
        return DataSourceBuilder.create()
                .url(url)
                .password(password)
                .username(username)
                .build();
    }

    @Primary
    @Bean(name = "targetEntityManagerFactory")
    public LocalContainerEntityManagerFactoryBean entityManagerFactory(EntityManagerFactoryBuilder builder,
                                                                       @Qualifier("targetDataSource") DataSource dataSource) {
        return builder
                .dataSource(dataSource)
                .packages("ru.advantum.shipmentimitator.targetdb")
                .persistenceUnit("target")

                .build();
    }

    @Primary
    @Bean(name = "transactionManager")
    public PlatformTransactionManager transactionManager(@Qualifier("targetEntityManagerFactory") EntityManagerFactory entityManagerFactory) {
        return new JpaTransactionManager(entityManagerFactory);
    }
}
