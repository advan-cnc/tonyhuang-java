package com.ad.filter;

import com.ad.util.TestThreadLocalUtil;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Date;

@WebFilter
public class RequestFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig)  {
        System.out.println("RequestFilter init");
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        System.out.println("RequestFilter doFilter uri");
        HttpServletRequest request = (HttpServletRequest)servletRequest;
        final String contextPath = request.getContextPath();
        System.out.println("contextPath = " + contextPath);
        final StringBuffer requestURL = request.getRequestURL();
        System.out.println("requestURL = " + requestURL);
        System.out.println("requestURI = " + request.getRequestURI());
        TestThreadLocalUtil.set(new Date().toString());
        try{
            filterChain.doFilter(request,servletResponse);
        }finally {
            System.out.println(TestThreadLocalUtil.get());
            TestThreadLocalUtil.remove();
        }

    }

    @Override
    public void destroy() {

    }
}
