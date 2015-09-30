package com.secret.action.bk;

import com.opensymphony.xwork2.Action;

public class GetCommentAction implements Action {
	private String result;

	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}

	@Override
	public String execute() throws Exception {
		result = "{\"status\":1,\"page\":1,\"perpage\":21,\"comments\":["
				+ "{\"content\":\"HASHA1\",\"phoneNum\":\"18661134212\"}"
				+ ",{\"content\":\"HASHA1\",\"phoneNum\":\"18661134212\"}"
				+ ",{\"content\":\"HASHA1\",\"phoneNum\":\"18661134212\"}"
				+ ",{\"content\":\"HASHA1\",\"phoneNum\":\"18661134212\"}"
				+ ",{\"content\":\"HASHA1\",\"phoneNum\":\"18661134212\"}"
				+ "],\"msgid\":\"111222\"}";
		return SUCCESS;
	}

}
