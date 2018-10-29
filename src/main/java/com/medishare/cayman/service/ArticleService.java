package com.medishare.cayman.service;

import com.alibaba.fastjson.JSONObject;
import com.medishare.cayman.common.JSONRet;
import com.medishare.cayman.domain.ArticleDQ;

/**
 * Created by liuy on 2018/4/19.
 */
public interface ArticleService {

    /**
     * 查询文章
     * @return
     */
    JSONRet searchArticle(String condition, String creater, int page);

    ArticleDQ getArticleDQById(String id);

    void saveArticle(ArticleDQ article);

    void updateArticle(ArticleDQ article);

    void articleLike(String id);

    void articleShare(String id);

    void articleRead(String id);

    void articleClick(String id);

}
