package com.iminer.business.iminergolddata.common;

import com.iminer.business.iminergolddata.domain.Log;
import com.iminer.business.iminergolddata.service.LogService;
import com.iminer.business.iminergolddata.util.HttpTools;
import org.joda.time.DateTime;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * @ClassName GoldDataFilter
 * @Description 记录日志filter
 * @Author guowenbin
 * @Date 2018/11/15 15:08
 * @Version 1.0
 **/
public class GoldDataFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)
            throws IOException, ServletException {
        HttpServletRequest httpServletRequest = (HttpServletRequest) servletRequest;
        String method = httpServletRequest.getMethod().toUpperCase();
        // 记录日志
        if ("POST".equals(method) || "GET".equals(method)) {
            String ipInfo = HttpTools.getRemoteHost((HttpServletRequest) servletRequest);
            String ipkey = ipInfo;
            try {
                ipkey = ipInfo.substring(0, ipInfo.lastIndexOf(".")) + ".0";
            } catch (Exception e) {
            }
            String token = httpServletRequest.getHeader("token");
            String userAgent = httpServletRequest.getHeader("User-Agent");
            String referer = httpServletRequest.getHeader("Referer");
            Log log = new Log();
            log.setIp(ipInfo);
            log.setCreateTime(new DateTime().toString("yyyy-MM-dd hh:mm:ss"));
            log.setUserAgent(userAgent);
            LogService logService = SpringContextUtil.getBean("logServiceImpl");
            if ("POST".equals(method)) {
                filterChain.doFilter(servletRequest, servletResponse);
                logService.add(log);
            } else if ("GET".equals(method)) {
                filterChain.doFilter(servletRequest, servletResponse);
//                logService.add(log);
            }
        }
    }

    @Override
    public void destroy() {

    }
}
