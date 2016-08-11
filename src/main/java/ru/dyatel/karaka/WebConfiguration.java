package ru.dyatel.karaka;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.GsonHttpMessageConverter;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import java.util.List;

@Configuration
@EnableWebMvc
public class WebConfiguration extends WebMvcConfigurerAdapter {

	@Autowired
	private GsonHttpMessageConverter gsonHttpMessageConverter;

	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		registry.addResourceHandler("/static/js/**").addResourceLocations("file:resources/js/");
		registry.addResourceHandler("/static/css/**").addResourceLocations("file:resources/css/");
	}

	@Override
	public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
		converters.add(gsonHttpMessageConverter);
	}

}
