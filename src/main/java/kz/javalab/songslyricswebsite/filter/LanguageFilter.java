package kz.javalab.songslyricswebsite.filter;


import kz.javalab.songslyricswebsite.constant.RequestConstants;

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

        String language = (String) httpServletRequest.getSession().getAttribute(RequestConstants.RequestParameters.LANGUAGE);

        if (language == null) {
            httpServletRequest.getSession().setAttribute(RequestConstants.RequestParameters.LANGUAGE, "en_US");
        } else if (language.isEmpty()) {
            httpServletRequest.getSession().setAttribute(RequestConstants.RequestParameters.LANGUAGE, "en_US");
        }

        filterChain.doFilter(httpServletRequest, servletResponse);
    }

    @Override
    public void destroy() {

    }
}
