package com.medishare.cayman.view;

import com.medishare.cayman.common.JSONRet;
import com.medishare.cayman.domain.ArticleDQ;
import com.medishare.cayman.service.ArticleService;
import com.medishare.cayman.utils.JSonUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

/**
 * Created by liuy on 2018/3/30.
 */
@Controller
public class ArticleController {

    static Logger log = LoggerFactory.getLogger(ArticleController.class);


    @Autowired
    ArticleService articleService;

    /**
     * 大桥糖友会首页 -》获取提问列表
     *
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/search/article/", method = RequestMethod.GET)
    public String searchArticle(HttpServletRequest request, @RequestParam(value = "condition", required = false) String condition,
                                @RequestParam(value = "creater", required = false) String creater,
                                @RequestParam(value = "page", defaultValue = "0") int page) {
        JSONRet ret = new JSONRet();
        if (StringUtils.isNotBlank(creater)) {
            Cookie[] cookies = request.getCookies();
            if (null == cookies) {
                System.out.println("没有cookie==============");
                ret = articleService.searchArticle(condition, creater, page);
            } else {
                for (Cookie cookie : cookies) {
                    if (cookie.getName().equals("OPENID")) {
                        System.out.println("获取cookie==============" + cookie.getValue());
                        ret = articleService.searchArticle(condition, cookie.getValue(), page);
                    }
                }
            }
        } else {
            ret = articleService.searchArticle(condition, creater, page);
        }

        return JSonUtils.toJsonString(ret);
    }

    /**
     * 获取提问详情
     *
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/get/article/", method = RequestMethod.GET)
    public String getArticle(@RequestParam(value = "id") String id) {
        JSONRet ret = new JSONRet();
        ArticleDQ articleDQ = articleService.getArticleDQById(id);
        ret.setData(articleDQ);
        return JSonUtils.toJsonString(ret);
    }

    /**
     * 提问
     *
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/submit/article/", method = RequestMethod.POST, produces = "application/json; charset=utf-8")
    public String insertArticle(HttpServletRequest request, @RequestBody ArticleDQ article) {
        JSONRet ret = new JSONRet();
        Cookie[] cookies = request.getCookies();
        if(null==cookies) {
            System.out.println("没有cookie==============");
        }else{
            for(Cookie cookie : cookies){
                if(cookie.getName().equals("OPENID")){
                    article.setCreater(cookie.getValue());
                    articleService.saveArticle(article);
                }
            }
        }

        return JSonUtils.toJsonString(ret);
    }

    /**
     * 回复
     *
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/reply/article/", method = RequestMethod.POST)
    public String updateArticle(@RequestBody ArticleDQ article) {
        JSONRet ret = new JSONRet();
        articleService.updateArticle(article);
        return JSonUtils.toJsonString(ret);
    }


    /**
     * 点赞
     *
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/like/article/", method = RequestMethod.GET)
    public String likeArticle(@RequestParam(value = "id") String id) {
        JSONRet ret = new JSONRet();
        articleService.articleLike(id);
        return JSonUtils.toJsonString(ret);
    }

    /**
     * 点击次数
     *
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/click/link/", method = RequestMethod.GET)
    public String clickLink(@RequestParam(value = "id") String id) {
        JSONRet ret = new JSONRet();
        articleService.articleClick(id);
        return JSonUtils.toJsonString(ret);
    }

    /**
     * 分享
     *
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/share/link/", method = RequestMethod.GET)
    public String shareLink(@RequestParam(value = "id") String id) {
        JSONRet ret = new JSONRet();
        articleService.articleShare(id);
        return JSonUtils.toJsonString(ret);
    }


    /**
     * 获取微信固定素材
     *
     * @return
     */
    @RequestMapping(value = "/get/permanent/footage/", method = RequestMethod.GET)
    public String getPermanentFootage() {
        JSONRet ret = new JSONRet();
        return JSonUtils.toJsonString(ret);
    }


}
