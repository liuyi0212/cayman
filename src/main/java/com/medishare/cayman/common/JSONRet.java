package com.medishare.cayman.common;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.List;

/**
 * Created by lyfpcy on 10/11/16.
 */
public class JSONRet {

	final static ObjectMapper mapper = new ObjectMapper();

	public final static int RESULT_CODE_SUCCESS = 0;
	public final static int RESULT_CODE_ERROR = 1;
	public final static int RESULT_CODE_NEED_LOGIN = 2;

	public JSONRet() {

	}

	public JSONRet (int resultCode) {
		this.code  = resultCode;
	}

	/**
	 * 分页信息类
	 */
	public static class Pager {
		/**
		 * 当前页
		 */
		int current;

		/**
		 * 页大小
		 */
		int pageSize;

		/**
		 * 是否有下一页
		 */
		boolean hasNext;

		/**
		 * 总条数
		 */
		int count;

		/**
		 * 开始记录数
		 */
		int start;

		public int getCurrent() {
			return current;
		}

		public void setCurrent(int current) {
			this.current = current;
		}

		public int getPageSize() {
			return pageSize;
		}

		public void setPageSize(int pageSize) {
			this.pageSize = pageSize;
		}

		public boolean getHasNext() {
			return hasNext;
		}

		public void setHasNext(boolean hasNext) {
			this.hasNext = hasNext;
		}

		public int getCount() {
			return count;
		}

		public void setCount(int count) {
			this.count = count;
		}

		public void setHasNextPage(List list){
			if (null != list && list.size() > 10 ) {
				list.remove(10);
				this.setHasNext(true);
			} else {
				this.setHasNext(false);
			}
		}

		public int getStart(){
			return (current - 1) * pageSize;
		}
	}

	/**
	 * 0: 成功, 1: 其他错误, 2: 需要登陆
	 */
	protected int code;

	/**
	 * 序列号,唯一标示一次请求
	 */
	protected String serialID;

	/**
	 * 携带数据
	 */
	protected Object data;

	/**
	 * 错误信息
	 */
	protected String errorMsg;

	/**
	 * 分页信息
	 */
	protected Pager pager;

	@Override
	public String toString() {
		try {
			return mapper.writeValueAsString(this);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		return "{}";
	}

	public static ObjectMapper getMapper() {
		return mapper;
	}

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public String getSerialID() {
		return serialID;
	}

	public void setSerialID(String serialID) {
		this.serialID = serialID;
	}

	public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	}

	public String getErrorMsg() {
		return errorMsg;
	}

	public void setErrorMsg(String errorMsg) {
		this.errorMsg = errorMsg;
	}

	public Pager getPager() {
		return pager;
	}

	public void setPager(Pager pager) {
		this.pager = pager;
	}
}
