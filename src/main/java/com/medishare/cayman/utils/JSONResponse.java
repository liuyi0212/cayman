package com.medishare.cayman.utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;


public class JSONResponse {

	final static ObjectMapper mapper = new ObjectMapper();

	static {

	}

	protected Boolean isSuccess = Boolean.TRUE;
	protected Map<String, Object> data;
	protected String type;
	protected String errorMsg;
	protected Boolean hasnextpage;

	public Boolean getHasnextpage() {
		return hasnextpage;
	}

	public void setHasnextpage(Boolean hasnextpage) {
		this.hasnextpage = hasnextpage;
	}

	public void setHasNextPage(List list){
		if (null != list && list.size() > 10 ) {
			list.remove(10);
			this.setHasnextpage(true);
		} else {
			this.setHasnextpage(false);
		}
	}

	public JSONResponse(Class type) {
		this.type = type.getSimpleName();
	}

	public Boolean getIsSuccess() {
		return isSuccess;
	}

	public void setIsSuccess(Boolean isSuccess) {
		this.isSuccess = isSuccess;
	}

	public Map<String, Object> getData() {
		return data;
	}

	public void setData(Map<String, Object> data) {
		this.data = data;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getErrorMsg() {
		return errorMsg;
	}

	public void setErrorMsg(String errorMsg) {
		this.errorMsg = errorMsg;
	}

	public String toString() {
		try {
			return mapper.writeValueAsString(this);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		return "{}";
	}

	public void addObject(Object o) {
		if (o == null) {
			return;
		}
		String key = o.getClass().getSimpleName();

		if (data == null) {
			data = new HashMap<String, Object>();
		}

		@SuppressWarnings("unchecked")
		List<Object> arr = (List<Object>) data.get(key);
		if (arr == null) {
			arr = new ArrayList<Object>();
			arr.add(o);
			data.put(key, arr);
		} else {
			arr.add(o);
		}
	}

}
