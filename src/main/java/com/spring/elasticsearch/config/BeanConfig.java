/**
 *BeanConfig.java
 *
 *@author Sagar Shrestha 
 **/
package com.spring.elasticsearch.config;

import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import com.spring.elasticsearch.DAO.ElasticSearchDAO;
import com.spring.elasticsearch.DAO.ElasticSearchDAOImpl;
import com.spring.elasticsearch.service.HighlightService;
import com.spring.elasticsearch.service.HighlightServiceImpl;

@EnableWebMvc
@Configuration
@PropertySource(value={"classpath:application.properties"})
public class BeanConfig extends WebMvcConfigurerAdapter{

	@Bean(name="elasticSearchDAO")
	public ElasticSearchDAO elasticSearchDAO(){
		return new ElasticSearchDAOImpl();
	}

	@Bean(name="highlightService")
	public HighlightService highlightService(){
		return new HighlightServiceImpl();
	}

	@Override
	public void addResourceHandlers(final ResourceHandlerRegistry registry) {
		registry.addResourceHandler("/resources/**").addResourceLocations("/resources/");
	}

	//To resolve ${} in @Value
	@Bean
	public static PropertySourcesPlaceholderConfigurer propertyConfigInDev() {
		return new PropertySourcesPlaceholderConfigurer();
	}


	/*
	 * Configure MessageSource to provide internationalized messages
	 *
	 */

	@Bean
	public MessageSource messageSource() {
		ResourceBundleMessageSource messageSource = new ResourceBundleMessageSource();
		messageSource.setBasename("messages_en");
		return messageSource;
	}

}
