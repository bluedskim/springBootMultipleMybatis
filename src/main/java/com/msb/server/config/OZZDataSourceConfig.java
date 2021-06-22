package com.msb.server.config;

import javax.sql.DataSource;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableTransactionManagement
@MapperScan(value = "com.msb.server.mapper.ozz", sqlSessionFactoryRef = "ozzSqlSessionFactory")
public class OZZDataSourceConfig {

	@Bean(name = "ozzDataSource")
	@Primary
	@ConfigurationProperties(prefix = "spring.datasource.hikari.ozz")
	public DataSource ozzDataSource() {
		return DataSourceBuilder.create().build();
	}

	@Bean(name = "ozzSqlSessionFactory")
	@Primary
	public SqlSessionFactory ozzSqlSessionFactory(@Qualifier("ozzDataSource") DataSource ozzDataSource,
			ApplicationContext applicationContext) throws Exception {

		SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
		sqlSessionFactoryBean.setDataSource(ozzDataSource);
		sqlSessionFactoryBean.setMapperLocations(applicationContext.getResources("classpath:mybatis/mapper/ozz/*.xml"));

		return sqlSessionFactoryBean.getObject();
	}

	@Bean(name = "ozzSqlSessionTemplate")
	@Primary
	public SqlSessionTemplate ozzSqlSessionTemplate(SqlSessionFactory ozzSqlSessionFactory) throws Exception {

		return new SqlSessionTemplate(ozzSqlSessionFactory);
	}
}
