package com.misu.easy_record_server.mapper;


import com.misu.easy_record_server.pojo.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author x
 */
@Repository
public interface UserMapper extends JpaRepository<User, Integer> {
    // 可以在这里自定义一些基于JPA规范的查询方法，例如按用户名查找用户等
    // 示例：User findByUsername(String username);

    User findByUsername(String username);

    // 自定义根据用户名模糊查询的方法
    List<User> findByUsernameContaining(String username);
}