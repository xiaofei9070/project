package com.star.dao;

import com.star.model.User;


public interface UserDao {
    int insert(User record);

    int insertSelective(User record);
    
    int updateUSER(User user);
}