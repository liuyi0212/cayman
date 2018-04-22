package com.medishare.cayman.utils;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * 处理Object与String的表现
 * 
 * @author laurence
 *
 */
public class JSonUtils {

	final static ObjectMapper mapper = new ObjectMapper();
	static Logger log = LoggerFactory.getLogger(JSonUtils.class);

	static {
		mapper.setSerializationInclusion(Include.NON_NULL);
	}

	public static String toJsonString(Object obj) {
		try {
			return mapper.writeValueAsString(obj);
		} catch (JsonProcessingException e) {
			log.error("toJsonString error",e);
		}
		return null;
	}

	public static String toTinyJsonString(Object obj) {
		try {
			return mapper.setSerializationInclusion(Include.NON_NULL).writeValueAsString(obj);
		} catch (JsonProcessingException e) {
			log.error("toTinyJsonString error",e);
		}
		return "{}";
	}

	public static <T> T toObject(String json, Class<T> clazz) {
		try {
			return mapper.readValue(json, clazz);
		} catch (IOException e) {
			log.error("toObject error",e);
		}
		return null;
	}

	private JSonUtils() {
		super();
	}
	
}
