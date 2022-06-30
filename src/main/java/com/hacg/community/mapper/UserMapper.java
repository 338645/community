package com.hacg.community.mapper;

import com.hacg.community.model.User;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface UserMapper {
    @Options(useGeneratedKeys = true, keyProperty = "id", keyColumn = "id")
    @Insert("insert into users(account_id,name,token,gmt_create,gmt_modified,bio,avatar_url) values(#{account_id},#{name},#{token},#{gmt_create},#{gmt_modified},#{bio},#{avatarUrl})")
    void insertUser(User user);

    @Select("select id,account_id,name,token,gmt_create,gmt_modified,bio,avatar_url avatarUrl from users where token = #{token}")
    User findByToken(@Param("token") String token);

    @Select("select id,account_id,name,token,gmt_create,gmt_modified,bio,avatar_url avatarUrl from users where id = #{id}")
    User selectById(@Param("id") int id);

    @ResultType(value = java.lang.Integer.class)
    @Select("select id from users where account_id = #{userId}")
    List<Integer> selectByAccountId(@Param("userId") String userId);
}
