package com.secret.util;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;

import com.secret.bean.User;

public class Config {
	public static final String SERVER_URL = "http://192.168.1.112:8080/SecretServer/";
	public static final String PACKAGENAME = "com.tao.secret";
	public static final String KEY_TOKEN = "token";
	public static final String CHARSET = "utf-8";
	public static final String KEY_ACTION = "action";
	public static final String KEY_CODE = "code";
	public static final String KEY_STATUS = "status";
	public static final String KEY_PHONE_NUM = "phone_num";
	public static final String KEY_CONTACTS = "contacts";
	public static final String KEY_PAGE = "page";
	public static final String KEY_PERPAGE = "perpage";
	public static final String KEY_UPLOAD_CONTACTS = "upload_contacts";
	public static final String KEY_TIMELINE = "timeline";
	public static final String KEY_MSG_ID = "msgId";
	public static final String KEY_MSG = "msg";
	public static final String KEY_CONTENT = "content";
	public static final String KEY_COMMENTS = "comments";
	public static final String ACTION_GET_CODE = "send_pass";
	public static final String ACTION_LOGIN = "login";
	public static final String ACTION_PHONE = "phone";
	public static final String ACTION_TIMELINE = "timeline";
	public static final String ACTION_PHONE_MD5 = "phone_md5";
	public static final String ACTION_GET_COMMENT = "get_comment";
	public static final String ACTION_PUB_COMMENT = "pub_comment";
	public static final String ACTION_PUBLISH = "publish";
	public static final int RESULT_STATUS_SUCCESS = 1;
	public static final int RESULT_STATUS_FAIL = 0;
	public static final int RESULT_STATUS_INVALID_TOKEN = 2;
	public static final int RESULT_STATUS_INVALID_CODE = 3;
	public static final int ATY_RESULT_NEED_REFRESH = 10000;

	//
	static public boolean isExpired(User user) {
		long duration = System.currentTimeMillis()
				- user.getLastTime().getTime();
		System.out.println("duration-->" + duration / 1000 + "s");
		return duration > 1000 * 60 * 60 * 24 * 7;// 七天不登录就得重新输入密码
	}

	static public PrintWriter getWriter() throws Exception {
		HttpServletResponse response = ServletActionContext.getResponse();
		response.setContentType("text/html;charset=utf-8");
		PrintWriter writer = response.getWriter();
		return writer;
	}

}
