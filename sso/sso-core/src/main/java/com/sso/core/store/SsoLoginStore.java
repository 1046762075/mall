package com.sso.core.store;

import com.sso.core.conf.Conf;
import com.sso.core.user.SsoUser;
import com.sso.core.util.JedisUtil;

/**
 * <p>Title: SsoLoginStore</p>
 * Description：
 * date：2020/6/27 13:42
 */
public class SsoLoginStore {

    private static int redisExpireMinite = 1440;    // 1440 minite, 24 hour
    public static void setRedisExpireMinite(int redisExpireMinite) {
        if (redisExpireMinite < 30) {
            redisExpireMinite = 30;
        }
        SsoLoginStore.redisExpireMinite = redisExpireMinite;
    }
    public static int getRedisExpireMinite() {
        return redisExpireMinite;
    }

    /**
     * get
     *
     * @param storeKey
     * @return
     */
    public static SsoUser get(String storeKey) {

        String redisKey = redisKey(storeKey);
        Object objectValue = JedisUtil.getObjectValue(redisKey);
        if (objectValue != null) {
            SsoUser xxlUser = (SsoUser) objectValue;
            return xxlUser;
        }
        return null;
    }

    /**
     * remove
     *
     * @param storeKey
     */
    public static void remove(String storeKey) {
        String redisKey = redisKey(storeKey);
        JedisUtil.del(redisKey);
    }

    /**
     * put
     *
     * @param storeKey
     * @param xxlUser
     */
    public static void put(String storeKey, SsoUser xxlUser) {
        String redisKey = redisKey(storeKey);
        JedisUtil.setObjectValue(redisKey, xxlUser, redisExpireMinite * 60);  // minite to second
    }

    private static String redisKey(String sessionId){
        return Conf.SSO_SESSIONID.concat("#").concat(sessionId);
    }

}
