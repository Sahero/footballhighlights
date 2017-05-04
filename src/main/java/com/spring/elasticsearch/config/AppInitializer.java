/**
 *Initializer.java
 *
 *@author Sagar Shrestha 
 **/
package com.spring.elasticsearch.config;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRegistration;

import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;

/*
 * This class is the replacement of web.xml
 * 
 */
public class AppInitializer implements WebApplicationInitializer {
	@Override
	public void onStartup(ServletContext container) throws ServletException {
		System.out.println("Application initializer");
		AnnotationConfigWebApplicationContext ctx = new AnnotationConfigWebApplicationContext();
		//ctx.setConfigLocation("com.spring.elasticsearch.config");
		ctx.register(AppConfig.class);
		ctx.setServletContext(container);

		ServletRegistration.Dynamic dispatcher = container.addServlet("DispatcherServlet", new DispatcherServlet(ctx));
		dispatcher.addMapping("/");
		dispatcher.setLoadOnStartup(1);
		
	}
}