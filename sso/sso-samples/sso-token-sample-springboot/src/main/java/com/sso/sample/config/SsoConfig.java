package com.sso.sample.config;

import com.sso.core.conf.Conf;
import com.sso.core.filter.SsoTokenFilter;
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

    @Value("${lsl.sso.redis.address}")
    private String xxlSsoRedisAddress;

    @Value("${lsl-sso.excluded.paths}")
    private String xxlSsoExcludedPaths;


    @Bean
    public FilterRegistrationBean xxlSsoFilterRegistration() {

        JedisUtil.init(xxlSsoRedisAddress);

        FilterRegistrationBean registration = new FilterRegistrationBean();

        registration.setName("XxlSsoWebFilter");
        registration.setOrder(1);
        registration.addUrlPatterns("/*");
        registration.setFilter(new SsoTokenFilter());
        registration.addInitParameter(Conf.SSO_SERVER, xxlSsoServer);
        registration.addInitParameter(Conf.SSO_LOGOUT_PATH, xxlSsoLogoutPath);
        registration.addInitParameter(Conf.SSO_EXCLUDED_PATHS, xxlSsoExcludedPaths);

        return registration;
    }

    @Override
    public void destroy() throws Exception {

        JedisUtil.close();
    }

}
