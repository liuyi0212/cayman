package com.medishare.cayman;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.mongo.MongoAutoConfiguration;
import org.springframework.context.ApplicationListener;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.ContextClosedEvent;
import org.springframework.scheduling.annotation.EnableScheduling;

@Configuration
@ComponentScan(
        basePackages = {"com.medishare.cayman"}
)
@EnableScheduling
@SpringBootApplication(exclude = {MongoAutoConfiguration.class} )
public class Application {

	static Logger log = LoggerFactory.getLogger(Application.class);

	public static void main(String[] args) throws Exception {
		log.info("Cayman Starting ......");
		ConfigurableApplicationContext ctx = SpringApplication.run(Application.class, args);
		ctx.addApplicationListener(new ApplicationListener<ContextClosedEvent>() {
			@Override
			public void onApplicationEvent(ContextClosedEvent event) {
				log.info("byebye, shutting down .........");
			}
		});
	}
}
