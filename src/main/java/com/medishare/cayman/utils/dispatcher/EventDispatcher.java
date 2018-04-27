package com.medishare.cayman.utils.dispatcher;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.medishare.cayman.config.WebConfig;
import com.medishare.cayman.utils.MessageUtil;
import com.medishare.cayman.utils.TemplateUtils;
//import com.medishare.manis.event.kafka.EventProducer;

/**
 * 事件消息业务分发器
 */
@Component
public class EventDispatcher {

	protected Logger log = LoggerFactory.getLogger(getClass());

	@Autowired
	WebConfig webConfig;
	@Autowired
	TemplateUtils templateUtils;

//	@Autowired
//	EventProducer eventProducer;

	public String processEvent(Map<String, String> map) {

		String openid = map.get("FromUserName"); //用户 openid

		if (map.get("Event").equals(MessageUtil.EVENT_TYPE_SUBSCRIBE)) { //关注事件
			templateUtils.sendTextMessage(openid,"你好，欢迎关注米喜医家！社区家庭医生与居民沟通的第一服务平台，为全家提供健康管理");
			if(map.get("EventKey")!=null){
				sendDoctorInfoToOpenId(map, openid);
			}
			return "";
		}

		if (map.get("Event").equals(MessageUtil.EVENT_TYPE_UNSUBSCRIBE)) { //取消关注事件

		}

		if (map.get("Event").equals(MessageUtil.EVENT_TYPE_SCAN)) { //扫描二维码事件
			if(map.get("EventKey")!=null){
				sendDoctorInfoToOpenId(map, openid);
			}
		}

		if (map.get("Event").equals(MessageUtil.EVENT_TYPE_LOCATION)) { //位置上报事件

		}

		if (map.get("Event").equals(MessageUtil.EVENT_TYPE_CLICK)) { //自定义菜单点击事件
			String eventKey = map.get("EventKey");
			if(eventKey == null) return null;
			if(eventKey.equals("V1001_GOOD_MESSAGE")){//好消息
				templateUtils.sendTextMessage(openid,"服务未上线，敬请期待...");
			}else if(eventKey.equals("V1002_MEDICAL_SERVICE")){//医疗服务
				templateUtils.sendTextMessage(openid,"服务未上线，敬请期待...");
			}else if(eventKey.equals("V1003_CUSTOMER_SERVICE")){//客服
				templateUtils.sendTextMessage(openid,"客服电话：4006-400-821");
			}else if(eventKey.equals("V1001_24_SOLAR")){
				Map<String,Object> mapNews = new HashMap<String, Object>();
                mapNews.put("title", "二十四节气--立夏");
                mapNews.put("description", "立夏，四月节。立字解见春。夏，假也。物至此时皆假大也。");
                mapNews.put("picurl","http://mmbiz.qpic.cn/mmbiz_jpg/PBWEER9a8WfvdEAJxZJGbZyPpPnqFF49ajXiciaLiaTib9Utj92WgaU3p0lXtqS9S9KPXEX9SiaTuACDFddUf4ibXrQw/0?wx_fmt=jpeg");
                mapNews.put("url","http://mp.weixin.qq.com/s?__biz=MzAwODM0NzkzMw==&mid=507815983&idx=3&sn=b89a7fa5827b07f7ff23ada08bb36053&chksm=00c0405d37b7c94bf09188b1da90f1c58555f36c21ae3f49598b59cb3999ac2344839d34c3b7#rd");
				templateUtils.sendChatMessage2(openid, Arrays.asList(mapNews));
			}else if(eventKey.equals("V1001_RECOMMEND")){//近期推荐
                Map<String,Object> mapNews = new HashMap<String, Object>();
                mapNews.put("title", "什么是签约家庭医生？");
                mapNews.put("description", "一分钟了解家庭医生签约制。");
                mapNews.put("picurl","http://mmbiz.qpic.cn/mmbiz_jpg/PBWEER9a8Wd3k3WV1ZXTHvFFWsLibibNt2fHiaZqFF5seB24CZ0gLY1SVTf6eS788ayiaTXGc8DSE0tj2bSBicSpzRQ/0?wx_fmt=jpeg");
                mapNews.put("url","http://mp.weixin.qq.com/s?__biz=MzAwODM0NzkzMw==&mid=507816014&idx=1&sn=fc0dfd90200c3bba3c0b15a80cab97ec&chksm=00c0403c37b7c92ab5a255e3ef67f0fb11bdf3ea45203a846ea5f29b5d92939ee3df5596b64b#rd");
                templateUtils.sendChatMessage2(openid, Arrays.asList(mapNews));
			}else if(eventKey.equals("V1001_ACTIVITY")){//医疗活动
                Map<String,Object> mapNews = new HashMap<String, Object>();
                mapNews.put("title", "世界红十字会日");
                mapNews.put("description", "急救小知识学习起来");
                mapNews.put("picurl","http://mmbiz.qpic.cn/mmbiz_jpg/PBWEER9a8WfvdEAJxZJGbZyPpPnqFF49RT7Aia3vJHSExIObBcqHibwgia9p9D4kjlL5ngpQzwCcQPhxl6DglbNjg/0?wx_fmt=jpeg");
                mapNews.put("url","http://mp.weixin.qq.com/s?__biz=MzAwODM0NzkzMw==&mid=507816003&idx=4&sn=2d24888570b10970473e31e3bb514236&chksm=00c0403137b7c92728ac7b3a79d9367ea0a7052ac4d485df9f7e3fa3a267b4a8ba6f9cba016a#rd");
                templateUtils.sendChatMessage2(openid, Arrays.asList(mapNews));
			}
		}

		if (map.get("Event").equals(MessageUtil.EVENT_TYPE_VIEW)) { //自定义菜单 View 事件

		}

		return null;
	}

	/**
	 * 发送医生信息消息给用户
	 * @param map
	 * @param openid
	 */
	private void sendDoctorInfoToOpenId(Map<String, String> map,String openid){
//		String eventKey = map.get("EventKey");
//		String doctorId = "";
//		if(eventKey.contains("doctor_")){
//			doctorId = eventKey.substring(eventKey.lastIndexOf("_")+1);
//		}
//		Doctor doctor = doctorService.findDoctor(doctorId, false);
//
//		String description = ""; //医生医院+擅长
//		//医生二维码
//		if(doctor==null){
//			return;
//		}else{
//			Patient patient = patientService.findPatientByOpenId(openid);
//			/**
//			 *
//			 */
//			ActEvent we = new ActEvent();
//			we.doctorId = doctorId;
//			we.openid = openid;
//			we.eventType=ActEventTypes.EVENT_SCAN_CODE;
//			if(patient != null){
//				/**
//				 * 如果未关注，则自动关注
//				 */
//				boolean hasFollowed = followService.isPatFollowDoc(patient.getId(), doctorId);
//				if(!hasFollowed){
//					Patient pat = new Patient();
//					pat.setId(patient.getId());
//					followService.follow(pat, doctor, false);
//
//					// 发送医生关注事件
//					ActEvent wef = new ActEvent();
//					wef.openid = openid;
//					wef.patientId = pat.getId();
//					wef.doctorId = doctorId;
//					wef.eventType=ActEventTypes.EVENT_FOLLOW;
//					log.info("send ActEvent"+ JSonUtils.toJsonString(wef));
//					eventProducer.send(wef);
//				}
//				we.patientId = patient.getId();
//			}else{
//				mogo.saveQrDoctorRelation(doctorId, openid);
//			}
//			log.info("send ActEvent"+ JSonUtils.toJsonString(we));
//			eventProducer.send(we);
//			//医生描述
//			description = doctor.getHospitalName();
//			StringBuffer sprcialtyStrB = new StringBuffer();
//			if(doctor.getSpecialty()!=null && doctor.getSpecialty().size()>0){
//				for(Disease disease : doctor.getSpecialty()){
//					if(disease!=null && disease.getName()!=null && !disease.getName().equals("")){
//						sprcialtyStrB.append(disease.getName() + ",");
//					}
//				}
//			}
//			if(sprcialtyStrB.length()>0){
//				String sprcialtyStr = "";
//				sprcialtyStr = sprcialtyStrB.substring(0, sprcialtyStrB.length()-1);
//				description = description + " | " + sprcialtyStr;
//			}
//
//		}
//
//		if(DoctorTypeUtils.titleType.get(doctor.getTitleType()) !=  null){
//			description = DoctorTypeUtils.titleType.get(doctor.getTitleType()) + "|" + description;
//		}
//
//		templateUtils.sendChatMessage(openid, String.format("%s个人诊室", doctor.getRealname()), description, "/matrix/doctor.html#/home?&doctorId="+doctor.getId(), "http://upyun.thedoc.cn/img/f0325892-38aa-40e1-8d61-0a70ad78a80b.png");
//
	}

}
