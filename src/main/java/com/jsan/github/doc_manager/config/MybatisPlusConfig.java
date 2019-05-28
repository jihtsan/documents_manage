package com.jsan.github.doc_manager.config;

import com.baomidou.mybatisplus.core.parser.ISqlParser;
import com.baomidou.mybatisplus.extension.parsers.BlockAttackSqlParser;
import com.baomidou.mybatisplus.extension.plugins.PaginationInterceptor;
import com.baomidou.mybatisplus.extension.plugins.PerformanceInterceptor;
import com.baomidou.mybatisplus.extension.plugins.SqlExplainInterceptor;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.List;

/**
 * MybatisPlus配置文件
 *
 * @author nieqiurong 2018/8/11 21:10.
 */
@Configuration
@EnableTransactionManagement
@MapperScan("com.jsan.github.doc_manager.mapper")
public class MybatisPlusConfig {

    @Bean
    public SqlExplainInterceptor sqlExplainInterceptor() {
        SqlExplainInterceptor sqlExplainInterceptor = new SqlExplainInterceptor();
        List<ISqlParser> sqlParserList = new ArrayList<>();
        sqlParserList.add(new BlockAttackSqlParser());
        sqlExplainInterceptor.setSqlParserList(sqlParserList);
        return sqlExplainInterceptor;
    }

    @Bean
    public PerformanceInterceptor performanceInterceptor() {
        //启用性能分析插件
        return new PerformanceInterceptor();
    }

    @Bean
    public PaginationInterceptor paginationInterceptor() {
        //paginationInterceptor.setLocalPage(true);// 开启 PageHelper 的支持
        return new PaginationInterceptor();
    }

    @Bean
    public SqlSessionFactory sqlSessionFactory(@Qualifier("dbDataSource") DataSource adsDataSource) throws Exception {
        final SqlSessionFactoryBean sessionFactory = new SqlSessionFactoryBean();
        sessionFactory.setDataSource(adsDataSource);
        return sessionFactory.getObject();
    }


}
