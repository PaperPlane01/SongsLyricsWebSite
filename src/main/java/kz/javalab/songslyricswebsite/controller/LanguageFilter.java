package kz.javalab.songslyricswebsite.controller;


import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * Created by PaperPlane on 24.08.2017.
 */
public class LanguageFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest httpServletRequest = (HttpServletRequest) servletRequest;

        String language = (String) httpServletRequest.getSession().getAttribute("language");

        if (language == null) {
            httpServletRequest.getSession().setAttribute("language", "en_US");
        } else if (language.isEmpty()) {
            httpServletRequest.getSession().setAttribute("language", "en_US");
        }

        filterChain.doFilter(httpServletRequest, servletResponse);
    }

    @Override
    public void destroy() {

    }
}
