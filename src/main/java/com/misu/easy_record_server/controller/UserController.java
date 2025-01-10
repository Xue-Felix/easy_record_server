package com.misu.easy_record_server.controller;

import com.misu.easy_record_server.common.ResponseResult;
import com.misu.easy_record_server.common.ResponseStatus;
import com.misu.easy_record_server.pojo.User;
import com.misu.easy_record_server.service.UserService;
import com.misu.easy_record_server.vo.UserVO;

import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

/**
 * @author x
 */
@RestController
@RequestMapping("/user")
@Slf4j
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    private ResponseResult<List<UserVO>> getListResponseResult(List<User> users) {
        List<UserVO> userViewOs = new ArrayList<>();

        for (User user : users) {
            UserVO userVO = new UserVO();
            BeanUtils.copyProperties(user, userVO);
            userViewOs.add(userVO);
        }
        return ResponseResult.success(userViewOs);
    }

    // 用户注册接口
    @PostMapping("/register")
    public ResponseEntity<ResponseResult<User>> register(@RequestBody User user) {
        try {
            User registeredUser = userService.registerUser(user);
            return ResponseEntity.ok(ResponseResult.success(registeredUser));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(ResponseResult.fail(ResponseStatus.FAILURE, e.getMessage()));
        }
    }

    // 用户登录接口
    @PostMapping("/login")
    public ResponseEntity<ResponseResult<UserVO>> login(@RequestParam String username, @RequestParam String password) {
        try {
            log.info("username: {}", username);
            log.info("password: {}", password);

            UserVO loggedInUser = userService.loginUser(username, password);

            return ResponseEntity.ok(ResponseResult.success(loggedInUser));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(ResponseResult.fail(ResponseStatus.FAILURE, e.getMessage()));
        }
    }

    // 创建用户的接口
    @PostMapping
    public ResponseResult<UserVO> createUser(@Validated @RequestBody User user) {
        User savedUser = userService.saveUser(user);
        UserVO userVO = new UserVO();
        BeanUtils.copyProperties(savedUser, userVO);
        return ResponseResult.success(userVO);
    }

    // 根据用户ID获取用户信息的接口
    @GetMapping("/{id}")
    public ResponseResult<UserVO> getUserById(@PathVariable Integer id) {
        log.info("获取用户信息 id: {}", id);

        User user = userService.getUserById(id);
        if (user == null) {
            return ResponseResult.fail(ResponseStatus.NOT_FOUND, "用户不存在");
        }
        UserVO userVO = new UserVO();
        BeanUtils.copyProperties(user, userVO);
        return ResponseResult.success(userVO);
    }

    // 获取所有用户信息的接口
    @GetMapping
    public ResponseResult<List<UserVO>> getUsers() {
        var users = userService.getAllUsers();
        return getListResponseResult(users);
    }

    // 更新用户信息的接口
    @PutMapping("/{id}")
    public ResponseResult<UserVO> updateUser(@PathVariable Integer id, @RequestBody User user) {
        user.setId(id);
        User updatedUser = userService.updateUser(user);
        if (updatedUser == null) {
            return ResponseResult.fail(ResponseStatus.NOT_FOUND, "用户不存在");
        }
        UserVO userVO = new UserVO();
        BeanUtils.copyProperties(updatedUser, userVO);
        return ResponseResult.success(userVO);
    }

    // 删除用户的接口
    @DeleteMapping("/{id}")
    public ResponseResult<Void> deleteUser(@PathVariable Integer id) {
        userService.deleteUser(id);
        return ResponseResult.success();
    }

    // 分页查询用户信息的接口
    @GetMapping("/page")
    public ResponseResult<List<UserVO>> getUsersPage(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<User> userPage = userService.findAllUsersPageable(pageable);
        List<UserVO> userViewOs = new ArrayList<>();
        for (User user : userPage.getContent()) {
            UserVO userVO = new UserVO();
            BeanUtils.copyProperties(user, userVO);
            userViewOs.add(userVO);
        }
        return ResponseResult.success(userViewOs);
    }

    // 根据用户名模糊查询用户信息的接口
    @GetMapping("/search")
    public ResponseResult<List<UserVO>> searchUsersByUsername(
            @RequestParam String username) {
        List<User> users = userService.findUsersByUsernameLike("%" + username + "%");
        return getListResponseResult(users);
    }
}