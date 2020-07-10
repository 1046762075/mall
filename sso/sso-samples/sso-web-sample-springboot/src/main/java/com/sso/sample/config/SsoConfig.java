package com.sso.sample.config;

import com.sso.core.conf.Conf;
import com.sso.core.filter.SsoWebFilter;
import com.sso.core.util.JedisUtil;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * <p>Title: SsoConfig</p>
 * Description：
 * date：2020/6/27 13:42
 */
@Configuration
public class SsoConfig implements DisposableBean {


    @Value("${lsl.sso.server}")
    private String xxlSsoServer;

    @Value("${lsl.sso.logout.path}")
    private String xxlSsoLogoutPath;

    @Value("${lsl-sso.excluded.paths}")
    private String xxlSsoExcludedPaths;

    @Value("${lsl.sso.redis.address}")
    private String xxlSsoRedisAddress;


    @Bean
    public FilterRegistrationBean xxlSsoFilterRegistration() {

        // xxl-sso, redis init
        JedisUtil.init(xxlSsoRedisAddress);

        // xxl-sso, filter init
        FilterRegistrationBean registration = new FilterRegistrationBean();

        registration.setName("XxlSsoWebFilter");
        registration.setOrder(1);
        registration.addUrlPatterns("/*");
        registration.setFilter(new SsoWebFilter());
        registration.addInitParameter(Conf.SSO_SERVER, xxlSsoServer);
        registration.addInitParameter(Conf.SSO_LOGOUT_PATH, xxlSsoLogoutPath);
        registration.addInitParameter(Conf.SSO_EXCLUDED_PATHS, xxlSsoExcludedPaths);

        return registration;
    }

    @Override
    public void destroy() throws Exception {

        // xxl-sso, redis close
        JedisUtil.close();
    }

}
