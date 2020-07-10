package com.sso.core.user;

import java.io.Serializable;
import java.util.Map;

/**
 * <p>Title: XxlSsoUser</p>
 * Description：
 * date：2020/6/27 13:42
 */
public class SsoUser implements Serializable {
    private static final long serialVersionUID = 42L;

    // field
    private String userid;
    private String username;
    private Map<String, String> plugininfo;

    private String version;
    private int expireMinite;
    private long expireFreshTime;


    // set get
    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Map<String, String> getPlugininfo() {
        return plugininfo;
    }

    public void setPlugininfo(Map<String, String> plugininfo) {
        this.plugininfo = plugininfo;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public int getExpireMinite() {
        return expireMinite;
    }

    public void setExpireMinite(int expireMinite) {
        this.expireMinite = expireMinite;
    }

    public long getExpireFreshTime() {
        return expireFreshTime;
    }

    public void setExpireFreshTime(long expireFreshTime) {
        this.expireFreshTime = expireFreshTime;
    }

}
