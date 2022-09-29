package org.irisi.laboeasyseek.utils;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

@WebFilter(filterName = "AuthFilter", urlPatterns = { "*.xhtml" })
public class AuthorizationFilter implements Filter {

    public AuthorizationFilter() {
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response,
                         FilterChain chain) throws IOException, ServletException {
        try {

            HttpServletRequest httpServletRequest = (HttpServletRequest) request;
            HttpServletResponse httpServletResponse = (HttpServletResponse) response;
            HttpSession httpSession = httpServletRequest.getSession(false);

            String requestURI = httpServletRequest.getRequestURI();
            if (httpSession != null && httpSession.getAttribute("email") != null) {
                if (requestURI.contains("/login.xhtml")) {
                    httpServletResponse.sendRedirect(httpServletRequest.getContextPath() + "/index.xhtml");
                }
            }
//            else {
                if (requestURI.contains("/login.xhtml")
                    || (httpSession != null && httpSession.getAttribute("email") != null)
                    || requestURI.contains("/public/")
                    || requestURI.contains("javax.faces.resource")) {
                    chain.doFilter(request, response);
                }
            else {
                    httpServletResponse.sendRedirect(httpServletRequest.getContextPath() + "/login.xhtml");
                }
//            }

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void destroy() {

    }
}
