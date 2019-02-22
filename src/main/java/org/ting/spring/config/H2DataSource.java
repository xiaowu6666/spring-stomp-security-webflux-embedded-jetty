package org.ting.spring.config;


import org.h2.tools.Server;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.jdbc.datasource.init.DataSourceInitializer;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;

@Configuration
@EnableTransactionManagement
public class H2DataSource {

    @Value("classpath:/db/sql/create-db.sql")
    private Resource schema;

    @Value("classpath:/db/sql/insert-data.sql")
    private Resource data;

    private DataSource dataSource;

    @Bean
    public DataSource dataSource() {
        if (dataSource == null) {
            DriverManagerDataSource dataSource = new DriverManagerDataSource();
            dataSource.setDriverClassName("org.h2.Driver");
            dataSource.setUsername("embedded");
            dataSource.setPassword("embedded");
            dataSource.setUrl("jdbc:h2:file:./data;DB_CLOSE_DELAY=-1;DB_CLOSE_ON_EXIT=false;");
            this.dataSource = dataSource;
        }
        return this.dataSource;
    }

    @Bean
    public ResourceDatabasePopulator databasePopulator() {
        ResourceDatabasePopulator populator = new ResourceDatabasePopulator();
        populator.addScript(schema);
        populator.addScripts(data);
        populator.setContinueOnError(true);
        return populator;
    }

    @Bean
    public DataSourceInitializer initializer() {
        DataSourceInitializer initializer = new DataSourceInitializer();
        initializer.setDatabasePopulator(databasePopulator());
        initializer.setDataSource(dataSource());
        return initializer;
    }


/*    @Bean  内存模式
    public DataSource  dataSource(){
        EmbeddedDatabaseBuilder builder = new EmbeddedDatabaseBuilder();
        EmbeddedDatabase build = builder.setType(EmbeddedDatabaseType.H2)
                .addScript("db/sql/create-db.sql")
                .addScript("db/sql/insert-data.sql")
                .build();
        return build;
    }*/

/*    @Bean(initMethod = "start",destroyMethod = "stop")
    public Server DatasourcesManager() throws SQLException {
        return Server.createWebServer("-web","-webAllowOthers","-webPort","8082");
    }*/

    @Bean
    public PlatformTransactionManager transactionManager() {
        PlatformTransactionManager manager = new DataSourceTransactionManager(dataSource());
        return manager;
    }
}
