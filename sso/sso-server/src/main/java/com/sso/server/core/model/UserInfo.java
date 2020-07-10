package com.sso.server.core.model;

/**
 * <p>Title: UserInfo</p>
 * Description：
 * date：2020/6/27 13:42
 */
public class UserInfo {

    private int userid;
    private String username;
    private String password;

    public int getUserid() {
        return userid;
    }

    public void setUserid(int userid) {
        this.userid = userid;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

}
