package com.medishare.cayman.config;

import javax.servlet.MultipartConfigElement;
import org.springframework.boot.autoconfigure.web.WebMvcAutoConfiguration.WebMvcAutoConfigurationAdapter;
import org.springframework.boot.context.embedded.MultipartConfigFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;

@ComponentScan(basePackages = "com.medishare.cayman.view")
@Configuration
@EnableWebMvc
//@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SpringMVCConfig extends WebMvcAutoConfigurationAdapter {

	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		// registry.addInterceptor(new PermissionIntercepter());
	}

//    @Override
//    public void postHandle(HttpServletRequest request, HttpResponse response, Object handler){
//
//    }

	@Bean
    public MultipartConfigElement multipartConfigElement() {

        MultipartConfigFactory factory = new MultipartConfigFactory();

        factory.setMaxFileSize("20MB");

        factory.setMaxRequestSize("20MB");

        return factory.createMultipartConfig();

    }
	
//	@Override
//	public void addCorsMappings(CorsRegistry registry) {
//		registry.addMapping("/**").allowedOrigins("m.thedoc.cn").allowedMethods("GET", "POST");
//	}

}