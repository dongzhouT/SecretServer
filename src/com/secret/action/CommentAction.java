package com.secret.action;

import com.opensymphony.xwork2.ActionSupport;
import com.secret.bean.Comment;
import com.secret.bean.User;
import com.secret.service.CommentService;
import com.secret.util.Config;

public class CommentAction extends ActionSupport {
	private String result;
	private String phoneNum;
	private String token;
	private String content;
	private int msgid;
	private int page;
	private int perpage;
	private CommentService service = new CommentService();

	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}

	public String getPhoneNum() {
		return phoneNum;
	}

	public void setPhoneNum(String phoneNum) {
		this.phoneNum = phoneNum;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public int getMsgid() {
		return msgid;
	}

	public void setMsgid(int msgid) {
		this.msgid = msgid;
	}

	public int getPage() {
		return page;
	}

	public void setPage(int page) {
		this.page = page;
	}

	public int getPerpage() {
		return perpage;
	}

	public void setPerpage(int perpage) {
		this.perpage = perpage;
	}

	public String get_comment() {
		User user = new User();
		user.setPhoneNum(phoneNum);
		user.setToken(token);
		Comment comment = new Comment();
		comment.setMsgid(msgid);
		try {
			result = service.get_comment(user, comment, page, perpage);
			Config.getWriter().println(result);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return NONE;

	}

	public String pub_comment() {
		User user = new User();
		user.setPhoneNum(phoneNum);
		user.setToken(token);
		Comment comment = new Comment();
		comment.setContent(content);
		comment.setPhoneNum(phoneNum);
		comment.setMsgid(msgid);
		try {
			result = service.pub_comment(user, comment);
			Config.getWriter().println(result);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return NONE;

	}
}
