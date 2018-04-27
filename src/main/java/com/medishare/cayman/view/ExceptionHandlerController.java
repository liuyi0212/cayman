package com.medishare.cayman.view;

import javax.servlet.http.HttpServletRequest;

import com.medishare.cayman.utils.JSONResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import com.medishare.cayman.common.JSONRet;
import com.medishare.cayman.utils.JSonUtils;


class ErrorResp extends JSONResponse {
	public ErrorResp(Class type) {
		super(type);
	}

	protected int code;

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}
}

@ControllerAdvice
public class ExceptionHandlerController {

	final static Logger error = LoggerFactory.getLogger(ExceptionHandlerController.class);

	@ExceptionHandler(value = { Exception.class, RuntimeException.class })
	@ResponseBody
	public String defaultErrorHandler(HttpServletRequest request, Exception e) {
		error.error(request.getRequestURL().toString(), e);
		ErrorResp ret = new ErrorResp(Exception.class);
		ret.setCode(-1);
		ret.setIsSuccess(false);
		ret.setHasnextpage(false);
		ret.setErrorMsg(e.getMessage() == null ? e.toString() : e.getMessage());
		return JSonUtils.toJsonString(ret);
	}
}