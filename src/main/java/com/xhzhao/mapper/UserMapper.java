package com.xhzhao.mapper;

import com.xhzhao.domain.User;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface UserMapper {
    @Select(value = "select u.username,u.password from user u where u.username=#{username}")
    @Results({@Result(property = "username",column = "username"),
            @Result(property = "password",column = "password")})
    User findUserByName(@Param("username") String username);

    @Insert("insert into user values(#{id},#{username},#{password})")
    @Options(useGeneratedKeys = true,keyProperty = "id",keyColumn = "id")
    void register(User user);

    @Select("select u.id from user u where u.username = #{username} and password = #{password}")
    Integer login(User user);

}