package com.medishare.cayman.utils;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.medishare.cayman.config.WebConfig;
import com.medishare.cayman.wechat.api.Wechat;
import com.medishare.cayman.wechat.client.WechatFactory;
import com.medishare.cayman.wechat.entity.Response;
import com.medishare.cayman.wechat.entity.ResponseMessage;
@Component
public class TemplateUtils {
	/**
	 * 订单提交消息模板id
	 */
	@Value("${order.submit}")
	String ORDER_SUBMIT_TEMPLATE_ID;
	/**
	 * 订单支付消息模板id
	 */
	@Value("${order.pay}")
	String ORDER_PAY_TEMPLATE_ID;

	/**
	 * 订单变更消息模板id
	 */
	@Value("${order.change}")
	String ORDER_CHANGE_TEMPLATE_ID;

	/**
	 * 订单取消消息模板id
	 */
	@Value("${order.cancel}")
	String ORDER_CANCEL_TEMPLATE_ID;
	/**
	 * 聊天提醒消息模板id
	 */
	@Value("${chat.remind}")
	String CHAT_REMIND_TEMPLATE_ID;
	@Autowired
	WebConfig webConfig;
	public static String CONFIRM_ORDER = "/swift/index.html#/confirmOrder?abstractid=";

	private static Logger log = LoggerFactory.getLogger(TemplateUtils.class);
	/**
	 * 微信发送订单提交模板消息
	 * @param openId 微信openId
	 * @param first 订单消息头
	 * @param orderID 订单ID
	 * @param orderMoneySum 订单金额
	 * @param url 详情链接
	 */
	public  boolean sendOrderSubmitTemplateMessage(String openId,String first, String orderID,String orderMoneySum,String orderId) throws IOException {
		String url = webConfig.getAuthorizationUrl(CONFIRM_ORDER + orderId);
		Map<String, Object> template = new HashMap<String, Object>() {
			{
				put("touser", openId);
				put("template_id", ORDER_SUBMIT_TEMPLATE_ID);
				put("url", url);
				put("data", new HashMap<String, Object>() {
					{
						put("first", new HashMap<String, Object>() {
							{
								put("value", first);
								put("color", "#BEBEBE");
							}
						});
						put("orderID", new HashMap<String, Object>() {
							{
								put("value", orderID);
								put("color", "#BEBEBE");
							}
						});
						put("orderMoneySum", new HashMap<String, Object>() {
							{
								put("value", orderMoneySum);
								put("color", "#BEBEBE");
							}
						});
					}
				});
			}
		};
		return sendTemplateMessage(template);
	}

	/**
	 * 微信发送订单支付模板消息
	 * @param openId 微信openId
	 * @param first 订单消息头
	 * @param orderMoneySum 订单金额
	 * @param orderProductName 订单商品信息
	 * @param url 详情链接
	 */
	public  boolean sendOrderPayTemplateMessage(String openId,String first, String orderMoneySum,String orderProductName,String url) throws IOException {
		log.info("openId="+openId+":ORDER_PAY_TEMPLATE_ID="+ORDER_PAY_TEMPLATE_ID);
		Map<String, Object> template = new HashMap<String, Object>() {
			{
				put("touser", openId);
				put("template_id", ORDER_PAY_TEMPLATE_ID);
				put("url", url);
				put("data", new HashMap<String, Object>() {
					{
						put("first", new HashMap<String, Object>() {
							{
								put("value", first);
								put("color", "#BEBEBE");
							}
						});
						put("orderMoneySum", new HashMap<String, Object>() {
							{
								put("value", orderMoneySum);
								put("color", "#BEBEBE");
							}
						});
						put("orderProductName", new HashMap<String, Object>() {
							{
								put("value", orderProductName);
								put("color", "#BEBEBE");
							}
						});
					}
				});
			}
		};
		return sendTemplateMessage(template);
	}

	/**
	 * 微信发送订单变更模板消息
	 * @param openId 微信openId
	 * @param first 订单消息头
	 * @param orderId 订单编号
	 * @param changeContent 变更内容
	 * @param url 详情链接
	 */
	public  boolean sendOrderChangeTemplateMessage(String openId,String first, String orderId,String changeContent) throws IOException {
		String url = webConfig.getAuthorizationUrl("/swift/#/order/detail?abstractid="+orderId);
		Map<String, Object> template = new HashMap<String, Object>() {
			{
				put("touser", openId);
				put("template_id", ORDER_CHANGE_TEMPLATE_ID);
				put("url", url);
				put("data", new HashMap<String, Object>() {
					{
						put("first", new HashMap<String, Object>() {
							{
								put("value", first);
								put("color", "#BEBEBE");
							}
						});
						put("keyword1", new HashMap<String, Object>() {
							{
								put("value", "米喜医家");
								put("color", "#BEBEBE");
							}
						});
						put("keyword2", new HashMap<String, Object>() {
							{
								put("value", orderId);
								put("color", "#BEBEBE");
							}
						});
						put("keyword3", new HashMap<String, Object>() {
							{
								put("value", changeContent);
								put("color", "#BEBEBE");
							}
						});
					}
				});
			}
		};
		return sendTemplateMessage(template);
	}

	/**
	 * 微信发送订单取消模板消息
	 * @param openId 微信openId
	 * @param first 订单消息头
	 * @param orderId 订单编号
	 * @param orderMoneySum 订单金额
	 * @param orderTime 订单时间
	 * @param userInfo 用户信息
	 * @param url 详情链接
	 */
	public  boolean sendOrderCancelTemplateMessage(String openId,String first, String orderId,String orderMoneySum,String orderTime,String userInfo) throws IOException {
		String url = webConfig.getAuthorizationUrl(CONFIRM_ORDER + orderId);
		log.info("sendOrderCancelTemplateMessage "+ORDER_CANCEL_TEMPLATE_ID);
		Map<String, Object> template = new HashMap<String, Object>() {
			{
				put("touser", openId);
				put("template_id", ORDER_CANCEL_TEMPLATE_ID);
				put("url", url);
				put("data", new HashMap<String, Object>() {
					{
						put("first", new HashMap<String, Object>() {
							{
								put("value", first);
								put("color", "#BEBEBE");
							}
						});
						put("keyword1", new HashMap<String, Object>() {
							{
								put("value", orderId);
								put("color", "#BEBEBE");
							}
						});
						put("keyword2", new HashMap<String, Object>() {
							{
								put("value", "取消");
								put("color", "#BEBEBE");
							}
						});
						put("keyword3", new HashMap<String, Object>() {
							{
								put("value", orderMoneySum);
								put("color", "#BEBEBE");
							}
						});
						put("keyword4", new HashMap<String, Object>() {
							{
								put("value", orderTime);
								put("color", "#BEBEBE");
							}
						});
						put("keyword5", new HashMap<String, Object>() {
							{
								put("value", userInfo);
								put("color", "#BEBEBE");
							}
						});
					}
				});
			}
		};
		return sendTemplateMessage(template);
	}
	public  boolean sendChatRemindTemplateMessage(String openId,String orderId,String doctorId) throws IOException {
		String url = webConfig.getAuthorizationUrl("/swift/index.html#/chat?order=" + orderId+"&member=");
		Map<String, Object> template = new HashMap<String, Object>() {
			{
				put("touser", openId);
				put("template_id", CHAT_REMIND_TEMPLATE_ID);
				put("url", url);
				put("data", new HashMap<String, Object>() {
					{
						put("first", new HashMap<String, Object>() {
							{
								put("value", "您好，您咨询医生的问题已回复");
								put("color", "#BEBEBE");
							}
						});
						put("keyword1", new HashMap<String, Object>() {
							{
								put("value", "图文咨询");
								put("color", "#BEBEBE");
							}
						});
						put("keyword2", new HashMap<String, Object>() {
							{
								put("value", "请点击查看");
								put("color", "#BEBEBE");
							}
						});
					}
				});
			}
		};
		return sendTemplateMessage(template);
	}
	/**
	 * 发送模板信息
	 * @param template
	 * @return
	 */
	private static boolean sendTemplateMessage(Map<String,Object> template){
		Wechat wechat = WechatFactory.getInstance();
		ResponseMessage response = null;
		try {
			response = wechat.sendTemplateMessage(template);
		} catch (IOException e) {
			log.error("sendTemplateMessage error",e);
		}
		if (response!= null && response.getResponse()!= null && response.getResponse().getErrCode()!= null
				&& response.getResponse().getErrCode() == 0) {
			return true;
		} else {
			return false;
		}
	}
	public  void sendChatMessage(String openid,String title,String description,String url,String picurl){

		Map<String,Object> articlesMap = new HashMap<String, Object>();
		articlesMap.put("title", title);
		articlesMap.put("description", description);
		articlesMap.put("url", webConfig.getAuthorizationUrl(url));
		articlesMap.put("picurl", picurl);

		Map<String, Object> msg = new HashMap<String, Object>() {
			{
				put("touser", openid);
				put("news", new HashMap<String, Object>() {
					{
						put("articles", Arrays.asList(articlesMap));
					}
				});
				put("msgtype", "news");
			}
		};

		sendMsg(msg, "chat");
	}
	public  void sendChatMessage2(String openid,List<Map<String,Object>> articles){
		if(StringUtils.isEmpty(openid) || articles == null || articles.isEmpty()){
			return;
		}
		Map<String, Object> msg = new HashMap<String, Object>() {
			{
				put("touser", openid);
				put("news", new HashMap<String, Object>() {
					{
						put("articles", articles);
					}
				});
				put("msgtype", "news");
				put("articlecount", String.valueOf(articles.size()));
			}
		};
		sendMsg(msg, "chat2");
	}
	/**
	 * 发送文本消息给用户
	 * @param openid
	 */
	public void sendTextMessage(String openid,String content){
		Map<String, Object> msg = new HashMap<String, Object>() {
			{
				put("touser", openid);
				put("text", new HashMap<String, Object>() {
					{
						put("content", content);
					}
				});
				put("msgtype", "text");
			}
		};
		sendMsg(msg, "text");
	}
	/**
	 * 发送消息
	 * @param msg
	 * @param type
	 */
	private void sendMsg(Map<String, Object> msg,String type){
		try {
			Wechat wechat = WechatFactory.getInstance();
			Response response = wechat.sendMessage(msg);
			if (response.getErrCode() == 0) {
				log.info(String.format("发送%s信息成功", type) + response);
			} else {
				log.error("发送"+type+"信息失败" + response);
			}
		} catch (Exception e) {
			log.error("sendMsg error "+JSonUtils.toJsonString(msg),e);
		}
	}
}
