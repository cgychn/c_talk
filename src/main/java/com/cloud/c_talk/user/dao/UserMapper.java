package com.cloud.c_talk.user.dao;

import com.cloud.c_talk.user.entity.C_TalkUser;
import org.apache.ibatis.annotations.*;

@Mapper
public interface UserMapper {

    @Select("select * from c_talk_user where username = #{username} and password = #{password}")
    C_TalkUser getUserByUsernameAndPassword(@Param("username") String username, @Param("password") String password);

    @Insert("insert into c_talk_user(username, password, description, age, sex) values(#{username}, #{password}, #{description}, #{age}, #{sex})")
    Integer addUser(C_TalkUser user);

    @Delete("delete from c_talk_user where username = #{username}")
    Integer removeUser(@Param("username") String username);

    @Update("update c_talk set password = #{password}, description = #{description}, age = #{age}, sex = #{sex} where username = #{username}")
    Integer updateUser(C_TalkUser user);

}
