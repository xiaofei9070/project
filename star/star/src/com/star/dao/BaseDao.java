package com.star.dao;

import javax.annotation.Resource;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.stereotype.Repository;

import com.star.model.SuperModel;

@Repository
public class BaseDao {
	@Resource
	private SqlSessionTemplate sqlTemplate;
	
	public int save(SuperModel superModel){
		return sqlTemplate.insert("com.star.model.User.insert", superModel);
	}
	
}
