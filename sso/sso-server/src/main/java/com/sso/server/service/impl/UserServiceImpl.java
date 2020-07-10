package com.sso.server.service.impl;

import com.sso.server.core.model.UserInfo;
import com.sso.server.core.result.ReturnT;
import com.sso.server.service.UserService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
/**
 * <p>Title: UserServiceImpl</p>
 * Description：
 * date：2020/6/27 13:42
 */
@Service
public class UserServiceImpl implements UserService {

    private static List<UserInfo> mockUserList = new ArrayList<>();
    static {
        for (int i = 0; i <5; i++) {
            UserInfo userInfo = new UserInfo();
            userInfo.setUserid(1000+i);
            userInfo.setUsername("fire" + (i>0?String.valueOf(i):""));
            userInfo.setPassword("fire");
            mockUserList.add(userInfo);
        }
    }

    @Override
    public ReturnT<UserInfo> findUser(String username, String password) {

        if (username==null || username.trim().length()==0) {
            return new ReturnT<>(ReturnT.FAIL_CODE, "请输入用户名.");
        }
        if (password==null || password.trim().length()==0) {
            return new ReturnT<>(ReturnT.FAIL_CODE, "请输入密码.");
        }

        // mock user
        for (UserInfo mockUser: mockUserList) {
            if (mockUser.getUsername().equals(username) && mockUser.getPassword().equals(password)) {
                return new ReturnT<>(mockUser);
            }
        }

        return new ReturnT<>(ReturnT.FAIL_CODE, "用户名或密码错误.");
    }


}
