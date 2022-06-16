package com.hacg.community.mapper;

import com.hacg.community.model.User;
import org.apache.ibatis.annotations.*;

@Mapper
public interface UserMapper {
    @Options(keyProperty = "true",keyColumn = "id")
    @Insert("insert into users(account_id,name,token,gmt_create,gmt_modified) values(#{account_id},#{name},#{token},#{gmt_create},#{gmt_modified})")
    void insertUser(User user);

    @Select("select * from users where token = #{token}")
    User findByToken(@Param("token") String token);
}
