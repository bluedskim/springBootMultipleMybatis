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
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableTransactionManagement
@MapperScan(value = "com.msb.server.mapper.ocms", sqlSessionFactoryRef = "ocmsSqlSessionFactory")
public class OCMSDataSourceConfig {

	@Bean(name = "ocmsDataSource")
	@ConfigurationProperties(prefix = "spring.datasource.hikari.ocms")
	public DataSource ocmsDataSource() {
		return DataSourceBuilder.create().build();
	}

	@Bean(name = "ocmsSqlSessionFactory")
	public SqlSessionFactory ocmsSqlSessionFactory(@Qualifier("ocmsDataSource") DataSource ocmsDataSource,
			ApplicationContext applicationContext) throws Exception {

		SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
		sqlSessionFactoryBean.setDataSource(ocmsDataSource);
		sqlSessionFactoryBean.setMapperLocations(applicationContext.getResources("classpath:mybatis/mapper/ocms/*.xml"));

		return sqlSessionFactoryBean.getObject();
	}

	@Bean(name = "ocmsSqlSessionTemplate")
	public SqlSessionTemplate ocmsSqlSessionTemplate(SqlSessionFactory ocmsSqlSessionFactory) throws Exception {

		return new SqlSessionTemplate(ocmsSqlSessionFactory);

	}

}
