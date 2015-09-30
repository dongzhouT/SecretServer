package com.secret.action.bk;

import com.opensymphony.xwork2.Action;

public class PublishAction implements Action {
	private String result;

	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}

	@Override
	public String execute() throws Exception {
		// TODO Auto-generated method stub
		result = "{\"status\":1}";
		return SUCCESS;
	}

}