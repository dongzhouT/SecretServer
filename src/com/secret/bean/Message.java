package com.secret.bean;

import java.sql.Timestamp;

/**
 * Message entity. @author MyEclipse Persistence Tools
 */

public class Message implements java.io.Serializable {

	// Fields

	private Integer msgid;
	private String phoneNum;
	private String msg;
	private Integer commentCount;
	private Timestamp msgtime;
	private Boolean yobiFlg;

	// Constructors

	/** default constructor */
	public Message() {
	}

	/** full constructor */
	public Message(String phoneNum, String msg, Integer commentCount,
			Timestamp msgtime, Boolean yobiFlg) {
		this.phoneNum = phoneNum;
		this.msg = msg;
		this.commentCount = commentCount;
		this.msgtime = msgtime;
		this.yobiFlg = yobiFlg;
	}

	// Property accessors

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

	public String getMsg() {
		return this.msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public Integer getCommentCount() {
		return this.commentCount;
	}

	public void setCommentCount(Integer commentCount) {
		this.commentCount = commentCount;
	}

	public Timestamp getMsgtime() {
		return this.msgtime;
	}

	public void setMsgtime(Timestamp msgtime) {
		this.msgtime = msgtime;
	}

	public Boolean getYobiFlg() {
		return this.yobiFlg;
	}

	public void setYobiFlg(Boolean yobiFlg) {
		this.yobiFlg = yobiFlg;
	}

}