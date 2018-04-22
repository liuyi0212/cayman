package com.medishare.cayman.service.impl;

import com.medishare.cayman.common.JSONRet;
import com.medishare.cayman.dao.MemberDAO;
import com.medishare.cayman.domain.Member;
import com.medishare.cayman.service.MemberService;
import com.mongodb.util.JSON;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Created by liuy on 2018/4/20.
 */
@Component
public class MemberServiceImpl implements MemberService{

    @Autowired
    MemberDAO memberDAO;

    @Override
    public JSONRet insertMemberInfo(Member member) {
        JSONRet ret = new JSONRet();
        memberDAO.insertMemberInfo(member);
        return ret;
    }

    @Override
    public JSONRet getMemberInfoByOpenId(String openId) {
        return null;
    }
}
