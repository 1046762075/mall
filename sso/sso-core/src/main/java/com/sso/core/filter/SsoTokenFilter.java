package com.sso.core.filter;

import com.sso.core.conf.Conf;
import com.sso.core.entity.ReturnT;
import com.sso.core.user.SsoUser;
import com.sso.core.login.SsoTokenLoginHelper;
import com.sso.core.path.impl.AntPathMatcher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.*;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * <p>Title: XxlSsoTokenFilter</p>
 * Description：
 * date：2020/6/27 13:42
 */
public class SsoTokenFilter extends HttpServlet implements Filter {
    private static Logger logger = LoggerFactory.getLogger(SsoTokenFilter.class);

    private static final AntPathMatcher antPathMatcher = new AntPathMatcher();

    private String ssoServer;
    private String logoutPath;
    private String excludedPaths;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

        ssoServer = filterConfig.getInitParameter(Conf.SSO_SERVER);
        logoutPath = filterConfig.getInitParameter(Conf.SSO_LOGOUT_PATH);
        excludedPaths = filterConfig.getInitParameter(Conf.SSO_EXCLUDED_PATHS);

        logger.info("SsoTokenFilter init.");
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse res = (HttpServletResponse) response;

        // make url
        String servletPath = req.getServletPath();

        // excluded path check
        if (excludedPaths!=null && excludedPaths.trim().length()>0) {
            for (String excludedPath:excludedPaths.split(",")) {
                String uriPattern = excludedPath.trim();

                // 支持ANT表达式
                if (antPathMatcher.match(uriPattern, servletPath)) {
                    // excluded path, allow
                    chain.doFilter(request, response);
                    return;
                }

            }
        }

        // logout filter
        if (logoutPath!=null
                && logoutPath.trim().length()>0
                && logoutPath.equals(servletPath)) {

            // logout
            SsoTokenLoginHelper.logout(req);

            // response
            res.setStatus(HttpServletResponse.SC_OK);
            res.setContentType("application/json;charset=UTF-8");
            res.getWriter().println("{\"code\":"+ ReturnT.SUCCESS_CODE+", \"msg\":\"\"}");

            return;
        }

        // login filter
        SsoUser xxlUser = SsoTokenLoginHelper.loginCheck(req);
        if (xxlUser == null) {

            // response
            res.setStatus(HttpServletResponse.SC_OK);
            res.setContentType("application/json;charset=UTF-8");
            res.getWriter().println("{\"code\":"+Conf.SSO_LOGIN_FAIL_RESULT.getCode()+", \"msg\":\""+ Conf.SSO_LOGIN_FAIL_RESULT.getMsg() +"\"}");
            return;
        }

        // ser sso user
        request.setAttribute(Conf.SSO_USER, xxlUser);


        // already login, allow
        chain.doFilter(request, response);
        return;
    }

}
