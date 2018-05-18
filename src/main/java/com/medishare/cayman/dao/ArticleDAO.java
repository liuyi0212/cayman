package com.medishare.cayman.dao;

import com.medishare.cayman.domain.ArticleDQ;
import com.medishare.cayman.message.resp.Article;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;

/**
 * Created by liuy on 2018/4/19.
 */
@Component
public interface ArticleDAO {

    @Results(value = {@Result(column = "id", property = "id", javaType = String.class),
            @Result(column = "title", property = "title", javaType = String.class),
            @Result(column = "created", property = "created", javaType = Date.class),
            @Result(column = "body", property = "body", javaType = String.class),
            @Result(column = "like", property = "like", javaType = Integer.class),
            @Result(column = "share", property = "share", javaType = Integer.class),
            @Result(column = "click", property = "click", javaType = Integer.class),
            @Result(column = "read", property = "read", javaType = String.class),
    })
    @Select({"<script>",
            "select dqa.id,dqa.title,dqa.created,dqa.body,dqa.like,dqa.share,dqa.click,dqa.read from daqiao_article dqa where 1=1"
                    + " order by dqa.id desc limit #{start}, #{limit};", "</script>"})
    List<ArticleDQ> searchArticle(@Param("start") int start, @Param("limit") int limit);

    @Results(value = {@Result(column = "id", property = "id", javaType = String.class),
            @Result(column = "title", property = "title", javaType = String.class),
            @Result(column = "created", property = "created", javaType = Date.class),
            @Result(column = "body", property = "body", javaType = String.class),
            @Result(column = "like", property = "like", javaType = Integer.class),
            @Result(column = "share", property = "share", javaType = Integer.class),
            @Result(column = "replycontent", property = "replycontent", javaType = String.class),
            @Result(column = "answerer", property = "answerer", javaType = String.class),
            @Result(column = "answerportrait", property = "answerportrait", javaType = String.class),
    })
    @Select({"<script>",
            "select dqa.* from daqiao_article dqa where dqa.id = ${id}", "</script>"})
    ArticleDQ getArticleBy(@Param("id") String id);


    @Insert(value = {"INSERT INTO daqiao_article (title,body,creater,picture,anonymous)"
            + " VALUES(#{title},#{body},#{creater},#{picture},#{anonymous})"})
    @SelectKey(statement = "select LAST_INSERT_ID()", keyProperty = "id", before = false, resultType = String.class)
    int saveArticle(ArticleDQ articleDQ);

    @Insert({"<script>", "update daqiao_article set updated = now() ",
            "<if test=\"answerer != null and answerer != '' \"> , answerer = #{answerer} </if>",
            "<if test=\"replycontent != null and replycontent != '' \"> , replycontent = #{replycontent} </if>",
            "where id = #{id}", "</script>"})
    int updateArticle(ArticleDQ articledq);

    @Update({"<script>", "update daqiao_article ea set ea.like = ea.like+1 where ea.id = ${id}", "</script>"})
    int articleLike(@Param("id") String id);

    @Update({"<script>", "update daqiao_article ea set ea.share = ea.share+1 where ea.id = ${id}", "</script>"})
    int articleShare(@Param("id") String id);

    @Update({"<script>", "update daqiao_article ea set ea.read = ea.read+1 where ea.id = ${id}", "</script>"})
    int articleRead(@Param("id") String id);

    @Update({"<script>", "update daqiao_article ea set ea.click = '1' where ea.id = ${id}", "</script>"})
    int articleClick(@Param("id") String id);
}