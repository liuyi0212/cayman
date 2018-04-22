package com.medishare.cayman.domain;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * Created by liuy on 2018/4/19.
 */
public class ArticleDQ implements Serializable {

    private static final long serialVersionUID = -8615796800796940794L;

    private String id;
    private String title;
    private String icon;
    private String body;
    /**
     * 创建人
     */
    private String creater;
    private Date created;
    private String status;
    private String picture;
    private List<String> pictureset;
    /**
     * 匿名
     */
    private String anonymous;
    /**
     * 点赞
     */
    private int like;
    /**
     * 分享
     */
    private int share;
    /**
     * 点击次数
     */
    private int click;
    /**
     * read
     */
    private String read;
    /**
     * 回复者
     */
    private String answerer;
    /**
     * 回复者
     */
    private String answerportrait;
    /**
     * 回复日期
     */
    private String replydate;

    private String replycontent;

    public String getAnswerportrait() {
        return answerportrait;
    }

    public void setAnswerportrait(String answerportrait) {
        this.answerportrait = answerportrait;
    }

    public List<String> getPictureset() {
        return pictureset;
    }

    public void setPictureset(List<String> pictureset) {
        this.pictureset = pictureset;
    }

    public String getRead() {
        return read;
    }

    public void setRead(String read) {
        this.read = read;
    }

    public int getClick() {
        return click;
    }

    public void setClick(int click) {
        this.click = click;
    }

    public String getReplycontent() {
        return replycontent;
    }

    public void setReplycontent(String replycontent) {
        this.replycontent = replycontent;
    }

    public String getAnswerer() {
        return answerer;
    }

    public void setAnswerer(String answerer) {
        this.answerer = answerer;
    }

    public String getReplydate() {
        return replydate;
    }

    public void setReplydate(String replydate) {
        this.replydate = replydate;
    }

    public String getCreater() {
        return creater;
    }

    public void setCreater(String creater) {
        this.creater = creater;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    public String getAnonymous() {
        return anonymous;
    }

    public void setAnonymous(String anonymous) {
        this.anonymous = anonymous;
    }

    public int getLike() {
        return like;
    }

    public void setLike(int like) {
        this.like = like;
    }

    public int getShare() {
        return share;
    }

    public void setShare(int share) {
        this.share = share;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
