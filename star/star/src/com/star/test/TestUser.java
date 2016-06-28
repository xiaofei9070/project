package com.star.test;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;

import com.star.dao.UserDao;
import com.star.model.User;

public class TestUser extends DbInt{
	
	private static UserDao userDao;
	private static SqlSession sqlSession;
	static SqlSessionFactory sqlSessionFactory = null;
	static {
		sqlSessionFactory = DbInt.getSqlSessionFactory();
	}
	public static void main(String[] args) {
		sqlSession = sqlSessionFactory.openSession();
		userDao = sqlSession.getMapper(UserDao.class);
		TestUser u = new TestUser();
		u.update();
	}
	public void addUSer() {
		try {
			User user =  new User();
			user.setAge(1);
			user.setId(1);
			user.setName("1231");
			userDao.insert(user);
			sqlSession.commit();
		} finally {
			sqlSession.close();
		}
	}
	
	public void update(){
		try {
			User user =  new User();
			user.setAge(1);
			user.setId(1);
			user.setName("1231aaaaaa");
			userDao.updateUSER(user);
			sqlSession.commit();
		} finally {
			sqlSession.close();
		}
	}
}
