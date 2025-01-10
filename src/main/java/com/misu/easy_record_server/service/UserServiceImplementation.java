package com.misu.easy_record_server.service;

import com.misu.easy_record_server.mapper.UserMapper;
import com.misu.easy_record_server.pojo.User;
import com.misu.easy_record_server.repository.UserRepository;
import com.misu.easy_record_server.vo.UserVO;

import cn.hutool.crypto.SecureUtil;
import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import java.util.List;

/**
 * @author x
 */
@Service
@Slf4j
public class UserServiceImplementation implements UserService {

    final UserMapper userMapper;

    @Autowired
    private UserRepository userRepository;

    public UserServiceImplementation(UserMapper userMapper) {
        this.userMapper = userMapper;
    }

    @Override
    public User saveUser(User user) {
        return userMapper.save(user);
    }

    @Override
    public User getUserById(Integer id) {
        return userMapper.findById(id).orElse(null);
    }

    @Override
    public List<User> getAllUsers() {
        return userMapper.findAll();
    }

    @Override
    public User updateUser(User user) {
        return userMapper.save(user);
    }

    @Override
    public void deleteUser(Integer id) {
        userMapper.deleteById(id);
    }

    @Override
    public Page<User> findAllUsersPageable(Pageable pageable) {
        return userMapper.findAll(pageable);
    }

    @Override
    public List<User> findUsersByUsernameLike(String username) {

        return userMapper.findByUsernameContaining(username);
    }

    @Override
    public User registerUser(User user) throws Exception {
        // 简单的输入参数校验，可根据实际需求完善更严格的校验规则
        if (user.getUsername() == null || user.getPassword() == null) {
            throw new Exception("用户名和密码不能为空");
        }

        // 检查用户名是否已存在
        if (userRepository.findByUsername(user.getUsername()) != null) {
            throw new Exception("用户名已存在，请更换用户名");
        }

        // 对用户输入的密码进行加密
        String encodedPassword = SecureUtil.md5(user.getPassword());

        user.setPassword(encodedPassword);

        try {
            // 将用户保存到数据库
            return userRepository.save(user);
        } catch (Exception e) {
            // 捕获数据库保存过程中可能出现的异常并抛出，方便上层统一处理
            throw new Exception("用户注册保存到数据库时出错", e);
        }
    }

    @Override
    public UserVO loginUser(String username, String password) throws Exception {
        // 根据用户名从数据库获取用户信息
        User user = userRepository.findByUsername(username);

        if (user == null) {
            throw new Exception("用户不存在");
        }

        if (user.getPassword().equals(SecureUtil.md5(password))) {
            UserVO userVO = new UserVO();
            BeanUtils.copyProperties(user, userVO);
            return userVO;
        }

        throw new Exception("用户名或密码错误");
    }
}