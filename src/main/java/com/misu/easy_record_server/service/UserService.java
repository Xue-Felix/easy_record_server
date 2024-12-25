package com.misu.easy_record_server.service;

import com.misu.easy_record_server.pojo.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

/**
 * @author x
 */
public interface UserService {
    User saveUser(User user);
    User getUserById(Integer id);
    List<User> getAllUsers();
    User updateUser(User user);
    void deleteUser(Integer id);

    User registerUser(User user) throws Exception;

    Optional<User> loginUser(String username, String password) throws Exception;

    // 分页查询用户
    Page<User> findAllUsersPageable(Pageable pageable);

    // 根据条件查询用户（示例以用户名模糊查询为例，可根据实际需求扩展更多条件）
    List<User> findUsersByUsernameLike(String username);

}
