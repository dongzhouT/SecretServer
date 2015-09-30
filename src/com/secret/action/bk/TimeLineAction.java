package com.secret.action.bk;

import com.opensymphony.xwork2.Action;

public class TimeLineAction implements Action {
	private String result;

	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}

	@Override
	public String execute() throws Exception {
		result = "{\"status\":1,\"page\":1,\"perpage\":21,\"timeline\":"
				+ "[{\"msg\":\"this is the first msg1\",\"phone_num\":\"18661134212\",\"msgId\":\"123456\"},"
				+ "{\"msg\":\"this is the first msg2\",\"phone_num\":\"18661134212\",\"msgId\":\"123456\"},"
				+ "{\"msg\":\"this is the first msg3\",\"phone_num\":\"18661134212\",\"msgId\":\"123456\"},"
				+ "{\"msg\":\"this is the first msg4\",\"phone_num\":\"18661134212\",\"msgId\":\"123456\"}]}";
		return SUCCESS;
	}

}
