package com.medishare.cayman.view;

import com.medishare.cayman.common.JSONRet;
import com.medishare.cayman.domain.ArticleDQ;
import com.medishare.cayman.domain.Member;
import com.medishare.cayman.service.MemberService;
import com.medishare.cayman.utils.JSonUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

/**
 * Created by liuy on 2018/3/30.
 */
@Controller
public class MemberController extends BaseController{

    @Autowired
    MemberService memberService;

    @ResponseBody
    @RequestMapping(value = "/insert/member/info/", method = RequestMethod.POST)
    public String insertMemberInfo(@RequestBody Member member){
        JSONRet ret= memberService.insertMemberInfo(member);
        return JSonUtils.toJsonString(ret);
    }

}
