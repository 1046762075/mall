package com.sso.server.config;

import com.sso.core.store.SsoLoginStore;
import com.sso.core.util.JedisUtil;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

/**
 * <p>Title: SsoConfig</p>
 * Description：
 * date：2020/6/27 13:42
 */
@Configuration
public class SsoConfig implements InitializingBean, DisposableBean {

    @Value("${lsl.sso.redis.address}")
    private String redisAddress;

    @Value("${lsl.sso.redis.expire.minite}")
    private int redisExpireMinite;

    @Override
    public void afterPropertiesSet() {
        SsoLoginStore.setRedisExpireMinite(redisExpireMinite);
        JedisUtil.init(redisAddress);
    }

    @Override
    public void destroy() throws Exception {
        JedisUtil.close();
    }

}
