package com.medishare.cayman.view;

import com.medishare.cayman.common.JSONRet;
import com.medishare.cayman.domain.ArticleDQ;
import com.medishare.cayman.domain.Member;
import com.medishare.cayman.service.MemberService;
import com.medishare.cayman.utils.JSonUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by liuy on 2018/3/30.
 */
@Controller
public class MemberController extends BaseController{

    @Autowired
    MemberService memberService;

    @ResponseBody
    @RequestMapping(value = "/insert/member/info/", method = RequestMethod.POST)
    public String insertMemberInfo(HttpServletRequest request, @RequestBody Member member){
        Cookie[] cookies = request.getCookies();
        JSONRet ret = new JSONRet();
        if(null==cookies) {
            System.out.println("没有cookie==============");
        }else{
            for(Cookie cookie : cookies){
                if(cookie.getName().equals("OPENID")){
                    member.setOpenid(cookie.getValue());
                    ret = memberService.insertMemberInfo(member);
                }
            }
        }
        return JSonUtils.toJsonString(ret);
    }

    /**
     * 将cookie封装到Map里面
     * @param request
     * @return
     */
    private Map<String,Cookie> ReadCookieMap(HttpServletRequest request){
        Map<String,Cookie> cookieMap = new HashMap<String,Cookie>();
        Cookie[] cookies = request.getCookies();
        if(null!=cookies){
            for(Cookie cookie : cookies){
                cookieMap.put(cookie.getName(), cookie);
            }
        }
        return cookieMap;
    }

}
