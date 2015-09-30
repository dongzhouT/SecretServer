package com.secret.bean;

import java.sql.Timestamp;


/**
 * Comment entity. @author MyEclipse Persistence Tools
 */

public class Comment  implements java.io.Serializable {


    // Fields    

     private Integer commentid;
     private Integer msgid;
     private String phoneNum;
     private String content;
     private Timestamp cmttime;
     private Boolean yobiFlg;


    // Constructors

    /** default constructor */
    public Comment() {
    }

    
    /** full constructor */
    public Comment(Integer msgid, String phoneNum, String content, Timestamp cmttime, Boolean yobiFlg) {
        this.msgid = msgid;
        this.phoneNum = phoneNum;
        this.content = content;
        this.cmttime = cmttime;
        this.yobiFlg = yobiFlg;
    }

   
    // Property accessors

    public Integer getCommentid() {
        return this.commentid;
    }
    
    public void setCommentid(Integer commentid) {
        this.commentid = commentid;
    }

    public Integer getMsgid() {
        return this.msgid;
    }
    
    public void setMsgid(Integer msgid) {
        this.msgid = msgid;
    }

    public String getPhoneNum() {
        return this.phoneNum;
    }
    
    public void setPhoneNum(String phoneNum) {
        this.phoneNum = phoneNum;
    }

    public String getContent() {
        return this.content;
    }
    
    public void setContent(String content) {
        this.content = content;
    }

    public Timestamp getCmttime() {
        return this.cmttime;
    }
    
    public void setCmttime(Timestamp cmttime) {
        this.cmttime = cmttime;
    }

    public Boolean getYobiFlg() {
        return this.yobiFlg;
    }
    
    public void setYobiFlg(Boolean yobiFlg) {
        this.yobiFlg = yobiFlg;
    }
   








}