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
@EnableJpaRepositories(
        entityManagerFactoryRef = "sourceEntityManagerFactory",
        basePackages = {"ru.advantum.shipmentimitator.sourcedb"}
)
@FieldDefaults(level = AccessLevel.PRIVATE)
public class SourceDbConfiguration {

    @Value("${srcdatasource.datasource.url}")
    String url;
    @Value("${srcdatasource.datasource.username}")
    String username;
    @Value("${srcdatasource.datasource.password}")
    String password;

    @Bean(name = "sourceDataSource")
    public DataSource dataSource() {
        return DataSourceBuilder.create()
                .url(url)
                .username(username)
                .password(password)
                .build();
    }

    @Bean(name = "sourceEntityManagerFactory")
    public LocalContainerEntityManagerFactoryBean
    entityManagerFactory(
            EntityManagerFactoryBuilder builder,
            @Qualifier("sourceDataSource") DataSource dataSource
    ) {
        return builder
                .dataSource(dataSource)
                .packages("ru.advantum.shipmentimitator.sourcedb")
                .persistenceUnit("source")
                .build();
    }

    @Bean(name = "sourceTransactionManager")
    public PlatformTransactionManager transactionManager(@Qualifier("sourceEntityManagerFactory") EntityManagerFactory entityManagerFactory) {
        return new JpaTransactionManager(entityManagerFactory);
    }
}
