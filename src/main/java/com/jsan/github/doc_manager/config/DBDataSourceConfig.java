package com.jsan.github.doc_manager.config;


import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

import javax.sql.DataSource;

/**
 * Created by Jihaotian on 17-8-14.
 * 数据库配置
 */
@Configuration
public class DBDataSourceConfig {


    @Primary
    @Bean(name = "dbDataSource")
    @ConfigurationProperties(prefix = "spring.datasource.db")
    public DataSource dbDataSource() {
        return DataSourceBuilder.create().build();
    }

    @Primary
    @Bean(name = "dbTransactionManager")
    public DataSourceTransactionManager dbTransactionManager() {
        return new DataSourceTransactionManager(dbDataSource());
    }
}
