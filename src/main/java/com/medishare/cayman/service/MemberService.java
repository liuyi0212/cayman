package com.medishare.cayman.service;

import com.medishare.cayman.common.JSONRet;
import com.medishare.cayman.domain.ArticleDQ;
import com.medishare.cayman.domain.Member;

/**
 * Created by liuy on 2018/4/19.
 */
public interface MemberService {

    /**
     * 保存用户信息
     * @return
     */
    JSONRet insertMemberInfo(Member member);

    /**
     * 是否保存个人信息
     * @param openId
     * @return
     */
    JSONRet getMemberInfoByOpenId(String openId);

}
