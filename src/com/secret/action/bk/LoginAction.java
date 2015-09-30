package com.secret.action.bk;

import org.junit.Test;

import com.opensymphony.xwork2.ActionSupport;

@SuppressWarnings("serial")
public class LoginAction extends ActionSupport {
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
		result = "{\"status\":1,\"token\":\"12u9u10joijo\"}";

		return SUCCESS;
	}

	public String test() throws Exception {
		// TODO Auto-generated method stub
		result = "{\"status\":1}22222";

		return SUCCESS;
	}

	@Test
	public void testunit() {
		System.out.println("test unit......");
	}

}
