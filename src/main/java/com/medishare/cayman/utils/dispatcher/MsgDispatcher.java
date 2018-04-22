package com.medishare.cayman.utils.dispatcher;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.medishare.cayman.utils.MessageUtil;
import com.medishare.cayman.wechat.api.Wechat;
import com.medishare.cayman.wechat.client.WechatFactory;
import com.medishare.cayman.wechat.entity.Response;

/**
 * 消息业务处理分发器
 */
@Component
public class MsgDispatcher {
	
	protected Logger log = LoggerFactory.getLogger(getClass());

	public String processMessage(Map<String, String> map) {

		String openid=map.get("FromUserName"); //用户 openid
		String mpid=map.get("ToUserName");   //公众号原始 ID


		/**
		 *  文本消息
		 */
		if (map.get("MsgType").equals(MessageUtil.REQ_MESSAGE_TYPE_TEXT)) {
			String content = map.get("Content");
			log.info(openid+"==>Content:"+content);
			if(content!=null && content.equals("表情包")){//20170322发送五张图片信息
				String [] images = {"QYZgu-5dyh0Y1p5sJLnpL1BuT_ZpjGC7kYM0QW6QUnA","QYZgu-5dyh0Y1p5sJLnpL73kxvC7_VcQdCoVSK7dkBA","QYZgu-5dyh0Y1p5sJLnpLwICVIk5nZimff6VhFMkXzI","QYZgu-5dyh0Y1p5sJLnpL5eYdRFxZ1KWQyBDy1wt5lc","QYZgu-5dyh0Y1p5sJLnpL50TSHuVrULT9qxJzSaMHak"};
				for(String mediaId:images){
					sendImageToOpenId(openid, mediaId);
				}
			}
		}

		/**
		 *  图片消息
		 */
		if (map.get("MsgType").equals(MessageUtil.REQ_MESSAGE_TYPE_IMAGE)) {
			
		}

		/**
		 *  链接消息
		 */
		if (map.get("MsgType").equals(MessageUtil.REQ_MESSAGE_TYPE_LINK)) {

		}

		/**
		 *  位置消息
		 */
		if (map.get("MsgType").equals(MessageUtil.REQ_MESSAGE_TYPE_LOCATION)) {

		}

		/**
		 *  视频消息
		 */
		if (map.get("MsgType").equals(MessageUtil.REQ_MESSAGE_TYPE_VIDEO)) {

		}


		/**
		 *  语音消息
		 */
		if (map.get("MsgType").equals(MessageUtil.REQ_MESSAGE_TYPE_VOICE)) {

		}

		return null;
	}

	/**
	 * 发送图片消息给用户
	 * @param openid
	 */
	private void sendImageToOpenId(String openid,String mediaId){
		Wechat wechat = WechatFactory.getInstance();
		Map<String, Object> msg = new HashMap<String, Object>() {
			{
				put("touser", openid);
				put("image", new HashMap<String, Object>() {
					{
						put("media_id", mediaId);
					}
				});
				put("msgtype", "image");
			}
		};
		try {
			Response response = wechat.sendMessage(msg);
			if (response.getErrCode() == 0) {
				log.info("发送图片信息成功" + response);
			} else {
				log.error("发送图片信息失败" + response);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	
	}
}
