package com.iminer.business.iminergolddata.common;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @ClassName CorsFilter
 * @Description TODO
 * @Author guowenbin
 * @Date 2018/11/19 14:32
 * @Version 1.0
 **/
public class CorsFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response,
                         FilterChain chain) throws IOException, ServletException {

        request.setCharacterEncoding("UTF-8");

        String origin = ((HttpServletRequest) request).getHeader("Origin");
        String methodType = ((HttpServletRequest) request).getMethod();
        if (origin == null) {
            ((HttpServletResponse) response).addHeader("Access-Control-Allow-Origin", "*");
        } else {
            ((HttpServletResponse) response).addHeader("Access-Control-Allow-Origin", origin);
        }
        ((HttpServletResponse) response).addHeader("Access-Control-Allow-Methods", "GET, OPTIONS, POST");
        ((HttpServletResponse) response).addHeader("Access-Control-Allow-Headers", "Authorization, X-Requested-With, Content-Type, Device-Info, Origin, Accept,token, MacCode, SecurityCode,RealIMEI,Cookie,point,value,ipc,funcID,UUID,token,clientType,incognitoMode,otype,oid");
        ((HttpServletResponse) response).addHeader("Access-Control-Allow-Credentials", "true");
        ((HttpServletResponse) response).addHeader("WWW-Authenticate", "xBasic realm=\"\"");
        ((HttpServletResponse) response).addHeader("Cache-Control", "no-cache");
        chain.doFilter(request, response);
    }

    @Override
    public void destroy() {

    }
}
