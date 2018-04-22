//package com.medishare.cayman.wechat.internal.utils;
//
//public class StringUtils {
//    public static String unEscapeJSON(String source) {
//        StringBuilder sb = new StringBuilder();
//        for (int i=0; i<source.length(); i++) {
//            char c = source.charAt(i);
//            if (source.charAt(i)=='\\' && source.charAt(i+1)=='u') {
//                char[] chars = new char[4];
//                i = i+2;
//                for (int j=i; j<i+4&&j<source.length(); j++) {
//                    chars[j-i] = source.charAt(j);
//                }
//                sb.append((char)Integer.parseInt(new String(chars), 16));
//                i=i+4;
//            } else {
//                sb.append(c);
//            }
//        }
//        return sb.toString();
//    }
//
//    public static void main(String[] args) {
//        String json = unEscapeJSON(
//                "{\"news\":{\"articles\":[{\"title\":\"[VCE 10A 啸宇 陶]\\u201c批评\\u201d@10.1\",\"description\":\"描述信息\",\"picurl\":\"http://172.16.31.108:80/rfocus/style/jxt/wechat/images/notice-criticism.png\",\"url\":\"http://172.16.31.108:80/rfocus/jxtWechatQY.getNotice.do?noticeID=646&parentID=321&studentID=3141\"}]},\"msgtype\":\"news\",\"touser\":\"d6693997-a010-4a81-a030-a7d1f08af248\",\"agentid\":\"3\"}"
//        );
//        System.out.println(json);
//    }
//
//	private StringUtils() {
//		super();
//	}
//    
//}
