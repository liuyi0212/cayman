package com.medishare.cayman.dao;

import com.medishare.cayman.domain.ArticleDQ;
import com.medishare.cayman.domain.Member;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;

/**
 * Created by liuy on 2018/4/19.
 */
@Component
public interface MemberDAO {

    @Select({"<script>",
            "select dqm.* from daqiao_member dqm where dqm.openId = #{openId}", "</script>"})
    Member searchMember(@Param("openId") String openId);


    @Insert(value = {"INSERT INTO daqiao_member (realname,gender,birthday,openid,unionid)"
            + " VALUES(#{realname},#{gender},#{birthday},#{openid},#{unionid})"})
    @SelectKey(statement = "select LAST_INSERT_ID()", keyProperty = "id", before = false, resultType = String.class)
    int insertMemberInfo(Member member);

}