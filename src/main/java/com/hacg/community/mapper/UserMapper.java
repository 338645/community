package com.hacg.community.mapper;

import com.hacg.community.model.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserMapper {

    @Insert("insert into users(account_id,name,token,gmt_create,gmt_modified) values(#{account_id},#{name},#{token},#{gmt_create},#{gmt_modified})")
    void insertUser(User user);

}
