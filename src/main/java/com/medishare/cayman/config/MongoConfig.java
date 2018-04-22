package com.medishare.cayman.config;

import java.net.UnknownHostException;

import javax.annotation.PostConstruct;

import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.Morphia;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.mongodb.MongoClient;


@Configuration
public class MongoConfig {
	Datastore ds;
	
	@Value("${mongo.host}")
	String mongohost;

	@Bean
	@PostConstruct
	public Datastore getDatastore() throws UnknownHostException {
		if (ds == null) {
			final Morphia morphia = new Morphia();

			// tell Morphia where to find your classes
			// can be called multiple times with different packages or classes
			morphia.mapPackage("com.medishare.mamba.dao");

			// create the Datastore connecting to the default port on the local
			// host
			ds = morphia.createDatastore(new MongoClient(mongohost), "liuliang");
			ds.ensureIndexes();
		}
		
		return ds;
	}
}
