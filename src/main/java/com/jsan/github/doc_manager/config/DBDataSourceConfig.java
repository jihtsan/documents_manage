package com.jsan.github.doc_manager.config;


import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

import javax.sql.DataSource;
import java.util.Objects;

/**
 * Created by Jihaotian on 17-8-14.
 * 数据库配置
 */
@Configuration
@MapperScan(basePackages = DBDataSourceConfig.PACKAGE, sqlSessionFactoryRef = "dbSqlSessionFactory")
public class DBDataSourceConfig {

    static final String PACKAGE = "com.jsan.github.doc_manager.mapper";

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

    @Primary
    @Bean(name = "dbSqlSessionFactory")
    public SqlSessionFactory dbSqlSessionFactory(@Qualifier("dbDataSource") DataSource dbDataSource) throws Exception {
        final SqlSessionFactoryBean sessionFactory = new SqlSessionFactoryBean();
        sessionFactory.setDataSource(dbDataSource);
        Objects.requireNonNull(sessionFactory.getObject()).getConfiguration().setMapUnderscoreToCamelCase(true);
        return sessionFactory.getObject();
    }
}
