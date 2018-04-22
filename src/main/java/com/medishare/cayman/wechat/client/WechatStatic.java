package com.medishare.cayman.wechat.client;

public class WechatStatic {
	
	private static String jsApiTicket;

	public static String getJsApiTicket() {
		return jsApiTicket;
	}

	public static void setJsApiTicket(String jsApiTicket) {
		WechatStatic.jsApiTicket = jsApiTicket;
	}
	
}
