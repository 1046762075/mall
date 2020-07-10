package com.firenay.mall.sso.entity;

import lombok.Data;
import lombok.ToString;

/**
 * <p>Title: User</p>
 * Description：
 * date：2020/6/27 15:56
 */
@ToString
@Data
public class User {

	private String user;

	private String username;

	private String password;

	private String url;
}
