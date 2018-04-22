package com.medishare.cayman.config;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.datastax.driver.core.Cluster;

@Configuration
public class CassandraConfig {
	
	@Value("${cassandra.cluster.contact-points}")
	String contactPoints;
	
	@Bean
	public Cluster cluster() throws IOException {
		Cluster cluster = Cluster.builder().addContactPoints(contactPoints.split(",")).build();
		return cluster;
	}
}
