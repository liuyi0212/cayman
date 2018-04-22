package com.medishare.cayman.service;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;

import org.springframework.util.StreamUtils;

import com.datastax.driver.core.Session;

public class CQLFileUtil {
	public static void run_cql_file(Session session, String file) throws IOException {
		InputStream in = CQLFileUtil.class.getClassLoader().getResourceAsStream(file);
		String readFileIntoString = StreamUtils.copyToString(in,Charset.forName("UTF-8"));
		
		String[] commands = readFileIntoString.split(";");
		
		for (String command : commands){
			
			String cql = command.trim();
			
			if (cql.isEmpty()){
				continue;
			}

			session.execute(cql);
		}
	}
}
