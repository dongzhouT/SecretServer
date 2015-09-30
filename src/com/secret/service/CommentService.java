package com.secret.service;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Transaction;
import org.json.JSONArray;

import com.secret.bean.Comment;
import com.secret.bean.CommentDAO;
import com.secret.bean.Message;
import com.secret.bean.MessageDAO;
import com.secret.bean.User;
import com.secret.util.Config;

public class CommentService {
	CommentDAO dao;
	MessageDAO msgDao;

	public CommentService() {
		dao = new CommentDAO();
		msgDao = new MessageDAO();
	}

	//
	public String pub_comment(User user, Comment comment) {
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
				comment.setCmttime(msgtime);
				dao.save(comment);
				// 评论数+1
				List msgList = msgDao.findByProperty("msgid",
						comment.getMsgid());
				if (msgList.size() > 0) {
					Message msg = (Message) msgList.get(0);
					if (msg.getCommentCount() == null) {
						msg.setCommentCount(1);
					} else {
						msg.setCommentCount(msg.getCommentCount() + 1);
					}
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
		result = "{\"status\":" + status + "}";

		return result;
	}

	// 获取评论列表
	public String get_comment(User user, Comment comment, int page, int perpage) {
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

				// 发布者可以看到所有评论
				List msgList = msgDao.findByProperty("msgid",
						comment.getMsgid());
				String msgNum = "", hql = "";
				if (msgList.size() > 0) {
					Message msg = (Message) msgList.get(0);
					msgNum = msg.getPhoneNum();
				}
				if (u.getPhoneNum().equals(msgNum)) {
					// 发布者打开评论列表
					hql = "FROM Comment WHERE msgid=:msgid  ORDER BY cmttime DESC ";
					query = dao.getSession().createQuery(hql);
					query.setParameter("msgid", comment.getMsgid());
					query.setFirstResult(page * perpage);
					query.setMaxResults(perpage);
				} else {
					// 非发布者打开评论列表
					hql = "FROM Comment WHERE msgid=:msgid and  phoneNum in ( :contacts )  ORDER BY cmttime DESC ";
					query = dao.getSession().createQuery(hql);
					query.setParameter("msgid", comment.getMsgid());
					query.setParameterList("contacts", contactList);
					query.setParameter("msgid", comment.getMsgid());
					query.setFirstResult(page * perpage);
				}
				// String hql = "FROM Message";

				List<Comment> commentList = new ArrayList<Comment>();
				for (int i = 0; i < query.list().size(); i++) {
					Comment cmt = (Comment) query.list().get(i);
					commentList.add(cmt);
				}
				array = new JSONArray(commentList);

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
				+ ",\"perpage\":" + perpage + ",\"comments\":"
				+ array.toString() + ",\"msgid\":" + comment.getMsgid() + "}";
		System.out.println("getComment-->" + result);
		return result;
	}
}
