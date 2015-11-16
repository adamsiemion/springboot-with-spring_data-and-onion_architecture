package com.springboot.onionarch.jooq;

import org.jooq.*;
import org.jooq.impl.DataSourceConnectionProvider;
import org.jooq.impl.DefaultConfiguration;
import org.jooq.impl.DefaultDSLContext;
import org.jooq.impl.DefaultExecuteListenerProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.jdbc.datasource.TransactionAwareDataSourceProxy;

import javax.sql.DataSource;

@Configuration
@ComponentScan(basePackageClasses = JooqModule.class)
public class JooqConfig {

    @Bean
    public DataSourceTransactionManager transactionManager(DataSource dataSource) {
        return new DataSourceTransactionManager(dataSource);
    }

//    @Bean
//    public ConnectionProvider connectionProvider(DataSource dataSource) {
//        return new DataSourceConnectionProvider(new TransactionAwareDataSourceProxy(dataSource));
//    }

//    @Bean
//    public TransactionProvider transactionProvider() {
//        return new SpringTransactionProvider();
//    }

//    @Bean
//    public ExceptionTranslator exceptionTranslator() {
//        return new ExceptionTranslator();
//    }
//
//    @Bean
//    public ExecuteListenerProvider executeListenerProvider(ExceptionTranslator exceptionTranslator) {
//        return new DefaultExecuteListenerProvider(exceptionTranslator);
//    }
//
//    @Bean
//    @Autowired
//    public org.jooq.Configuration jooqConfig(ConnectionProvider connectionProvider,
//                                             TransactionProvider transactionProvider, ExecuteListenerProvider executeListenerProvider) {
//
//        return new DefaultConfiguration() //
//                .derive(connectionProvider) //
//                .derive(transactionProvider) //
//                .derive(executeListenerProvider) //
//                .derive(SQLDialect.H2);
//    }

    @Bean
    public DSLContext dsl(DataSource dataSource) {
        ConnectionProvider connectionProvider = new DataSourceConnectionProvider(new TransactionAwareDataSourceProxy(dataSource));
        org.jooq.Configuration config = new DefaultConfiguration() //
                .derive(connectionProvider) //
                .derive(new SpringTransactionProvider()) //
                .derive(new DefaultExecuteListenerProvider(new ExceptionTranslator())) //
                .derive(SQLDialect.H2);
        return new DefaultDSLContext(config);
    }
}
