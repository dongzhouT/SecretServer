package com.secret.bean;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.json.JSONArray;
import org.junit.Test;

import com.secret.service.UserService;

public class App {
	static SessionFactory sf = null;
	static {
		Configuration config = new Configuration();
		config.configure("hibernate.cfg.xml");
		sf = config.buildSessionFactory();
	}

	@Test
	public void save() {
		Session session = sf.openSession();
		Transaction ts = session.beginTransaction();
		User user = new User();
		user.setPhoneNum("12343233333");
		session.save(user);
		ts.commit();
		session.close();
	}

	@Test
	public void daosave() {

		User user = new User();
		user.setPhoneNum("12343233333ff");
		UserDAO dao = new UserDAO();
		Session session = dao.getSession();
		Transaction ts = session.beginTransaction();

		dao.save(user);
		ts.commit();
		session.close();
	}

	@Test
	public void change() {
		Session session = sf.openSession();
		Transaction ts = session.beginTransaction();
		User u = (User) session.get(User.class, 1);

		u.setPhoneNum("111");
		ts.commit();
		session.close();
	}

	@Test
	public void testId() {
		Session session = sf.openSession();
		Transaction ts = session.beginTransaction();
		User u = new User();
		u.setPhoneNum("222222");
		System.out.println("����ǰ" + u.getId());
		session.save(u);
		System.out.println("�����" + u.getId());
		ts.commit();
		session.close();
	}

	@Test
	public void queryId() {
		Session session = sf.openSession();
		Transaction ts = session.beginTransaction();
		User u = new User();
		Query query = session.createQuery("from User where id=?");
		query.setInteger(0, 1);
		List list = query.list();
		if (list.size() > 0) {
			u = (User) list.get(0);
			System.out.println("u-->" + u.toString());
		} else {
			System.out.println("no record");
		}

		ts.commit();
		session.close();
	}

	@Test
	public void testMsg() {
		MessageDAO dao = new MessageDAO();
		Message msg = dao.findById(4);
		System.out.println("msg-->" + msg.toString());
	}

	@Test
	public void commentsave() {

		Comment comment = new Comment();
		comment.setContent("content");
		comment.setPhoneNum("123");
		comment.setMsgid(1);
		CommentDAO dao = new CommentDAO();
		Session session = dao.getSession();
		Transaction ts = session.beginTransaction();

		dao.save(comment);
		ts.commit();
		session.close();
	}

	@Test
	public void testDateTime() {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		// SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		SimpleDateFormat sdfD = new SimpleDateFormat("yyyy-MM-dd");
		// Date date = new Date();
		// System.out.println(date);
		// String dateString = sdf.format(date) + ".2";
		// System.out.println(dateString);
		// long time = date.getTime();
		// System.out.println(time + "QQQ");
		// try {
		// Date date2 = sdf.parse(dateString);
		// System.out.println(date2);
		// } catch (ParseException e) {
		// // TODO Auto-generated catch block
		// e.printStackTrace();
		// }
		// Date d = new Date(time);

		long timeMillis = System.currentTimeMillis();
		try {
			// long time2 = sdfD.parse(sdfD.format(new Date(timeMillis)))
			// .getTime();
			String testTime = "2015-09-25 22:20:23.02";
			// if (testTime.contains(".")) {
			// int index = testTime.lastIndexOf(".");
			// testTime = testTime.substring(0, index);
			// System.out.println("point-->" + testTime);
			// }
			Date testDate = sdf.parse(testTime);
			System.out.println("testdate-->" + testDate);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	@Test
	public void testDate2() {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String time = "2015-09-25 22:20:23";
		try {
			Date date = sdf.parse(time);
			System.out.println("date->" + date);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Test
	public void list2JsonArray() {
		List<Message> msList = new ArrayList<Message>();
		Message msg = new Message("123", "11", 23, new Timestamp(
				System.currentTimeMillis()), true);
		msList.add(msg);
		msList.add(msg);
		msList.add(msg);
		JSONArray array = new JSONArray(msList);
		System.out.println(array.toString());
	}

	@Test
	public void cmt2JsonArray() {
		List<Comment> msList = new ArrayList<Comment>();
		Comment cmt = new Comment(12, "123321", "content  for me",
				new Timestamp(System.currentTimeMillis()), true);
		msList.add(cmt);
		msList.add(cmt);
		msList.add(cmt);
		JSONArray array = new JSONArray(msList);
		System.out.println(array.toString());
	}

	@Test
	public void testUserContacts() {
		UserDAO dao = new UserDAO();
		User user = dao.findById(9);
		UserService service = new UserService();
		String get_contacts = service.get_contacts(user);
		System.out.println(user.toString());
		System.out.println(get_contacts);
	}
}
