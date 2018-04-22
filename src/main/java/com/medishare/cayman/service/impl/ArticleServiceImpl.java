package com.medishare.cayman.service.impl;

import com.medishare.cayman.common.JSONRet;
import com.medishare.cayman.common.JSONRet.Pager;
import com.medishare.cayman.dao.ArticleDAO;
import com.medishare.cayman.domain.ArticleDQ;
import com.medishare.cayman.message.resp.Article;
import com.medishare.cayman.service.ArticleService;
import com.medishare.cayman.utils.JSonUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;


import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by liuy on 2018/4/19.
 */
@Component
public class ArticleServiceImpl implements ArticleService{

    @Autowired
    ArticleDAO articleDAO;

    @Override
    public JSONRet searchArticle(int page) {
        JSONRet ret = new JSONRet();
        Pager pager = new Pager();
        Map<String, Object> map = new HashMap<>();
        int pageStart = page <= 1 ? 0 : ((page - 1) * 11);
        List<ArticleDQ> list = articleDAO.searchArticle(pageStart, 111);
        ret.setData(list);
        pager.setHasNextPage(list);
        ret.setPager(pager);
        return ret;
    }

    @Override
    public ArticleDQ getArticleDQById(String id) {
        ArticleDQ articleDQ = articleDAO.getArticleBy(id);
        if(!StringUtils.isEmpty(articleDQ.getPicture())){
            List<String> l = Arrays.asList(articleDQ.getPicture().split(","));
            articleDQ.setPicture(null);
            articleDQ.setPictureset(l);
        }
        return articleDQ;
    }

    @Override
    public void saveArticle(ArticleDQ article) {
        articleDAO.saveArticle(article);
    }

    @Override
    public void updateArticle(ArticleDQ article) {
        articleDAO.updateArticle(article);
    }

    @Override
    public void articleLike(String id) {
        articleDAO.articleLike(id);
    }

    @Override
    public void articleShare(String id) {
        articleDAO.articleShare(id);
    }

    @Override
    public void articleRead(String id) {
        articleDAO.articleRead(id);
    }

    @Override
    public void articleClick(String id) {
        articleDAO.articleClick(id);
    }
}
