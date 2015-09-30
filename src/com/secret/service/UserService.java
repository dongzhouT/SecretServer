package com.secret.service;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.Query;
import org.hibernate.Transaction;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.secret.bean.User;
import com.secret.bean.UserDAO;
import com.secret.util.Config;
import com.secret.util.MD5Tool;

public class UserService {
	UserDAO dao;

	public UserService() {
		dao = new UserDAO();
	}

	public String login(User user) {
		boolean flag = false;
		int status = 0;
		long nowTime = System.currentTimeMillis();
		String result = "";
		Transaction ts = dao.getSession().beginTransaction();
		List userList = dao.findByPhoneNum(user.getPhoneNum());
		User u = null;
		String token;
		String code = "1111";
		Timestamp lastTime = new Timestamp(nowTime);
		if (userList.size() > 0) {
			// 用户已存在
			u = (User) userList.get(0);
			token = u.getToken();
			if (u.getCode().equals(user.getCode())) {
				status = Config.RESULT_STATUS_SUCCESS;
				u.setLastTime(lastTime);
			} else {
				status = Config.RESULT_STATUS_INVALID_CODE;
			}

		} else {
			//
			token = MD5Tool.md5(System.currentTimeMillis() + user.getPhoneNum()
					+ "");
			user.setToken(token);
			user.setLastTime(lastTime);
			user.setCode(user.getCode());
			user.setRegTime(lastTime);
			dao.save(user);
			status = Config.RESULT_STATUS_SUCCESS;
		}
		result = "{\"status\":" + status + ",\"token\":\"" + token + "\"}";
		ts.commit();
		dao.getSession().close();
		return result;
	}

	public String upload_contacts(User user) {
		String result = "";
		int status = 0;
		Transaction ts = dao.getSession().beginTransaction();
		Query query = dao.getSession().createQuery(
				"from User where  phoneNum=? and token=?");
		query.setString(0, user.getPhoneNum());
		query.setString(1, user.getToken());
		List list = query.list();

		if (list.size() > 0) {
			User u = (User) list.get(0);
			if (Config.isExpired(u)) {
				status = Config.RESULT_STATUS_INVALID_TOKEN;
			} else {
				status = Config.RESULT_STATUS_SUCCESS;
				JSONArray contactArray;
				try {
					contactArray = new JSONArray(user.getContact());

					StringBuffer contactBuffer = new StringBuffer();
					contactBuffer.append(u.getPhoneNum() + ",");
					for (int i = 0; i < contactArray.length(); i++) {
						JSONObject object = contactArray.getJSONObject(i);
						contactBuffer.append(object
								.getString(Config.ACTION_PHONE_MD5) + ",");
					}
					u.setContact(contactBuffer.toString().substring(0,
							contactBuffer.length() - 1));
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		} else {
			status = Config.RESULT_STATUS_FAIL;
		}
		ts.commit();
		dao.getSession().close();
		result = "{\"status\":" + status + "}";

		return result;
	}

	@SuppressWarnings("unchecked")
	public String get_contacts(User user) {
		String result = "";
		int status = 0;
		Transaction ts = dao.getSession().beginTransaction();
		Query query = dao.getSession().createQuery(
				"from User where  phoneNum=? and token=?");
		query.setString(0, user.getPhoneNum());
		query.setString(1, user.getToken());
		List list = query.list();
		JSONArray userJsonArray = new JSONArray();

		if (list.size() > 0) {
			User u = (User) list.get(0);
			if (Config.isExpired(u)) {
				status = Config.RESULT_STATUS_INVALID_TOKEN;
			} else {
				status = Config.RESULT_STATUS_SUCCESS;
				try {
					String contacts = u.getContact();
					String[] contactsSplit = contacts.split(",");
					Map<String, User> userMap = new HashMap<String, User>();
					for (int i = 0; i < contactsSplit.length; i++) {
						List<User> tmpUserList = dao
								.findByPhoneNum(contactsSplit[i]);

						if (tmpUserList.size() > 0) {
							User tmpUser = new User();
							tmpUser.setPhoneNum(tmpUserList.get(0)
									.getPhoneNum());
							tmpUser.setLastTime(tmpUserList.get(0)
									.getLastTime());
							tmpUser.setId(tmpUserList.get(0).getId());
							userMap.put(tmpUser.getPhoneNum(), tmpUser);
						}
					}
					Collection<User> values = userMap.values();
					List<User> userList = new ArrayList<User>(values);

					Collections.sort(userList, new Comparator<User>() {

						@Override
						public int compare(User o1, User o2) {
							if (o1.getLastTime().after(o2.getLastTime())) {
								return -1;
							} else {
								return 1;
							}
						}
					});
					userJsonArray = new JSONArray(userList);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		} else {
			status = Config.RESULT_STATUS_FAIL;
		}
		ts.commit();
		dao.getSession().close();
		result = "{\"status\":" + status + ",\"contacts\":"
				+ userJsonArray.toString() + "}";
		return result;
	}

	public String change_code(User user, String oldCode) {
		String result = "";
		int status = 0;
		Transaction ts = dao.getSession().beginTransaction();
		Query query = dao.getSession().createQuery(
				"from User where  phoneNum=? and token=?");
		query.setString(0, user.getPhoneNum());
		query.setString(1, user.getToken());
		List list = query.list();

		if (list.size() > 0) {
			User u = (User) list.get(0);
			if (Config.isExpired(u)) {
				status = Config.RESULT_STATUS_INVALID_TOKEN;
			} else {
				if (oldCode.equals(u.getCode())) {
					status = Config.RESULT_STATUS_SUCCESS;
					u.setCode(user.getCode());
				} else {
					status = Config.RESULT_STATUS_INVALID_CODE;
				}
			}
		} else {
			status = Config.RESULT_STATUS_FAIL;
		}
		ts.commit();
		dao.getSession().close();
		result = "{\"status\":" + status + "}";
		return result;
	}
}
