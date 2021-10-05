package uz.rdu.nexign.hasinterface.service.datasources;

import com.zaxxer.hikari.HikariDataSource;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.domain.EntityScan;
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

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

@Configuration
@EntityScan("uz.rdu.nexign.hasinterface.model.DAO.tertiaryDS")
@EnableTransactionManagement
@EnableJpaRepositories(
        entityManagerFactoryRef = "tertiaryEntityManagerFactory",
        transactionManagerRef = "secondaryTransactionManager",
        basePackages = {"uz.rdu.nexign.hasinterface.repository.tertiaryDS"})
public class TertiaryDataSourceConfiguration {
    @Bean(name = "tertiaryDataSourceProperties")
    @ConfigurationProperties("spring.datasource-tertiary")
    public DataSourceProperties secondaryDataSourceProperties() {
        return new DataSourceProperties();
    }

    @Bean(name = "tertiaryDataSource")
    @ConfigurationProperties("spring.datasource-tertiary.configuration")
    public DataSource secondaryDataSource(@Qualifier("tertiaryDataSourceProperties") DataSourceProperties secondaryDataSourceProperties) {
        return secondaryDataSourceProperties.initializeDataSourceBuilder().type(HikariDataSource.class).build();
    }

    @Bean(name = "tertiaryEntityManagerFactory")
    public LocalContainerEntityManagerFactoryBean secondaryEntityManagerFactory(
            EntityManagerFactoryBuilder secondaryEntityManagerFactoryBuilder, @Qualifier("tertiaryDataSource") DataSource secondaryDataSource) {

        Map<String, String> secondaryJpaProperties = new HashMap<>();
        secondaryJpaProperties.put("hibernate.dialect", "org.hibernate.dialect.Oracle10gDialect");
        secondaryJpaProperties.put("hibernate.hbm2ddl.auto", "update");

        return secondaryEntityManagerFactoryBuilder
                .dataSource(secondaryDataSource)
                .packages("uz.rdu.nexign.hasinterface.model.DAO.tertiaryDS")
                .persistenceUnit("tertiaryDataSource")
                .properties(secondaryJpaProperties)
                .build();
    }

    @Bean(name = "tertiaryTransactionManager")
    public PlatformTransactionManager secondaryTransactionManager(
            @Qualifier("tertiaryEntityManagerFactory") EntityManagerFactory secondaryEntityManagerFactory) {

        return new JpaTransactionManager(secondaryEntityManagerFactory);
    }
}
