package com.secret.action;

import java.net.URLDecoder;

import com.opensymphony.xwork2.ActionSupport;
import com.secret.bean.Message;
import com.secret.bean.User;
import com.secret.service.MessageService;
import com.secret.util.Config;

public class MessageAction extends ActionSupport {
	private String result;
	private String phoneNum;
	private String token;
	private int page;
	private int perpage;
	private String msg;
	private MessageService service = new MessageService();

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

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

	public MessageService getService() {
		return service;
	}

	public void setService(MessageService service) {
		this.service = service;
	}

	/**
	 * 获取消息
	 * 
	 * @return
	 * @throws Exception
	 */
	public String timeline() throws Exception {
		User user = new User();
		user.setPhoneNum(phoneNum);
		user.setToken(token);
		try {
			result = service.timeline(user, page, perpage);
			Config.getWriter().println(result);
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println("timeline-->" + result);

		return NONE;
	}

	/**
	 * 发布消息
	 * 
	 * @return
	 * @throws Exception
	 */
	public String publish() throws Exception {
		User user = new User();
		user.setPhoneNum(phoneNum);
		user.setToken(token);
		Message message = new Message();
		message.setPhoneNum(phoneNum);
		message.setMsg(msg);
		result = service.publish(user, message);
		try {
			result = URLDecoder.decode(result, Config.CHARSET);
			Config.getWriter().println(result);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return NONE;
	}

}
