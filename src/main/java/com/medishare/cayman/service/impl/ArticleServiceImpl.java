package com.medishare.cayman.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.medishare.cayman.common.JSONRet;
import com.medishare.cayman.common.JSONRet.Pager;
import com.medishare.cayman.dao.ArticleDAO;
import com.medishare.cayman.domain.ArticleDQ;
import com.medishare.cayman.service.ArticleService;
import com.medishare.cayman.utils.HttpHelper;
import com.medishare.cayman.utils.JSonUtils;
import com.medishare.cayman.wechat.conf.Configuration;
import com.medishare.cayman.wechat.entity.AccessToken;
import com.mongodb.BasicDBObject;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import org.mongodb.morphia.Datastore;
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

    @Autowired
    Datastore ds;

    @Override
    public JSONRet searchArticle(String condition, String creater, int page) {
        JSONRet ret = new JSONRet();
        Pager pager = new Pager();
        int pageStart = page <= 1 ? 0 : ((page - 1) * 11);
        List<ArticleDQ> list = articleDAO.searchArticle(condition, creater, pageStart, 111);
        list.forEach(l->{
            if(!StringUtils.isEmpty(l.getTag())){
                l.setTags(Arrays.asList(l.getTag().split(",")));
                l.setTag(null);
            }
        });
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
        if(!StringUtils.isEmpty(articleDQ.getTag())){
            articleDQ.setTags(Arrays.asList(articleDQ.getTag().split(",")));
            articleDQ.setTag(null);
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

    @Override
    public void saveWechatArticle(JSONObject jsonObject) {
        ds.save(jsonObject);
    }

//    DBCursor cursor = collection.find(filter_dbobject).
//            limit(10).sort(new BasicDBObject("create_time",-1));

    @Override
    public List<DBObject> findWechatArticle() {
        DBCollection col = ds.getMongo().getDB("daqiao").getCollection("article");
        DBCursor cursor = col.find().limit(1).sort(new BasicDBObject("_id",-1));
        List<DBObject> list = cursor.toArray();
        return list;
    }


}
