package com.sso.server.service;

import com.sso.server.core.model.UserInfo;
import com.sso.server.core.result.ReturnT;
/**
 * <p>Title: UserService</p>
 * Description：
 * date：2020/6/27 13:42
 */
public interface UserService {

	ReturnT<UserInfo> findUser(String username, String password);

}
