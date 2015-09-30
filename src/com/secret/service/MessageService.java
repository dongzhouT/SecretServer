package com.secret.service;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Transaction;
import org.json.JSONArray;

import com.secret.bean.Message;
import com.secret.bean.MessageDAO;
import com.secret.bean.User;
import com.secret.util.Config;

public class MessageService {
	MessageDAO dao;

	public MessageService() {
		dao = new MessageDAO();
	}

	// 发布消息
	public String publish(User user, Message message) {
		Transaction ts = dao.getSession().beginTransaction();
		String result;
		int status = 0;
		Query query = dao.getSession().createQuery(
				"from User where  phoneNum=? and token=?");
		query.setString(0, user.getPhoneNum());
		query.setString(1, user.getToken());
		List userList = query.list();
		if (userList.size() > 0) {
			User u = (User) userList.get(0);
			if (!Config.isExpired(u)) {
				Timestamp msgtime = new Timestamp(System.currentTimeMillis());
				message.setMsgtime(msgtime);
				message.setCommentCount(0);

				dao.save(message);

				status = 1;

			} else {
				// token过期
				status = 2;
			}
		} else {
			// 用户不存在
			status = 2;
		}
		result = "{\"status\":" + status + "}";
		ts.commit();
		dao.getSession().close();
		return result;
	}

	/**
	 * 返回消息json
	 * 
	 * @param user
	 *            发送请求的用户
	 * @param page
	 *            页码
	 * @param perpage
	 *            每页显示数量
	 * @return result = "{\"status\":1,\"page\":1,\"perpage\":21,\"timeline\":"
	 *         +
	 *         "[{\"msg\":\"this is the first msg1\",\"phone_num\":\"18661134212\",\"msgId\":\"123456\"},"
	 *         +
	 *         "{\"msg\":\"this is the first msg2\",\"phone_num\":\"18661134212\",\"msgId\":\"123456\"},"
	 *         +
	 *         "{\"msg\":\"this is the first msg3\",\"phone_num\":\"18661134212\",\"msgId\":\"123456\"},"
	 *         +
	 *         "{\"msg\":\"this is the first msg4\",\"phone_num\":\"18661134212\",\"msgId\":\"123456\"}]}"
	 *         ;
	 */
	public String timeline(User user, int page, int perpage) {
		Transaction ts = dao.getSession().beginTransaction();
		String result;
		StringBuffer timelineBuffer = new StringBuffer();
		JSONArray array = new JSONArray();
		net.sf.json.JSONArray object = new net.sf.json.JSONArray();
		int status = 0;
		Query query = dao.getSession().createQuery(
				"from User where  phoneNum=? and token=? ");
		query.setString(0, user.getPhoneNum());
		query.setString(1, user.getToken());
		query.setCacheable(false);
		List userList = query.list();
		if (userList.size() > 0) {
			User u = (User) userList.get(0);
			if (!Config.isExpired(u)) {
				Timestamp msgtime = new Timestamp(System.currentTimeMillis());
				u.setLastTime(msgtime);
				String contacts = u.getContact();
				String[] contactSplit = contacts.split(",");
				List<String> contactList = new ArrayList<String>();
				Collections.addAll(contactList, contactSplit);

				String hql = "FROM Message WHERE phoneNum in ( :contacts )  ORDER BY msgtime DESC ";
				// String hql = "FROM Message";
				try {
					query = dao.getSession().createQuery(hql);
					query.setParameterList("contacts", contactList);
					query.setFirstResult(page * perpage);
					query.setMaxResults(perpage);
					List<Message> msgList = new ArrayList<Message>();
					for (int i = 0; i < query.list().size(); i++) {
						Message msg = (Message) query.list().get(i);
						msgList.add(msg);
					}
					array = new JSONArray(msgList);
				} catch (Exception e) {
					// TODO: handle exception
					e.printStackTrace();
				}

				status = 1;

			} else {
				// token过期
				status = 2;
			}
		} else {
			// 用户不存在
			status = 2;
		}
		ts.commit();
		dao.getSession().close();
		result = "{\"status\":" + status + ",\"page\":" + page
				+ ",\"perpage\":" + perpage + ",\"timeline\":"
				+ array.toString() + "}";
		return result;
	}

}
