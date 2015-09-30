package com.secret.action;

import com.opensymphony.xwork2.ActionSupport;
import com.secret.bean.User;
import com.secret.service.UserService;
import com.secret.util.Config;

public class UserAction extends ActionSupport {
	private String result;
	private String phoneNum;
	private String code;
	private String contacts;
	private String token;
	private String oldCode;

	public String getOldCode() {
		return oldCode;
	}

	public void setOldCode(String oldCode) {
		this.oldCode = oldCode;
	}

	private UserService service = new UserService();

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

	public String getContacts() {
		return contacts;
	}

	public void setContacts(String contacts) {
		this.contacts = contacts;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}

	public String login() throws Exception {
		// TODO Auto-generated method stub
		User user = new User();
		user.setPhoneNum(phoneNum);
		user.setCode(code);
		result = service.login(user);
		return SUCCESS;
	}

	public String upload_contacts() throws Exception {
		// TODO Auto-generated method stub
		User user = new User();
		user.setPhoneNum(phoneNum);
		user.setToken(token);
		user.setContact(contacts);
		result = service.upload_contacts(user);
		return SUCCESS;
	}

	public String get_contacts() throws Exception {
		User user = new User();
		user.setPhoneNum(phoneNum);
		user.setToken(token);
		try {
			result = service.get_contacts(user);
			Config.getWriter().println(result);
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println("get_contacts-->" + result);

		return NONE;
	}

	public String change_code() throws Exception {
		User user = new User();
		user.setPhoneNum(phoneNum);
		user.setToken(token);
		user.setCode(code);
		try {
			result = service.change_code(user, oldCode);
			Config.getWriter().println(result);
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println("change_pwd-->" + result);
		return NONE;
	}
}
