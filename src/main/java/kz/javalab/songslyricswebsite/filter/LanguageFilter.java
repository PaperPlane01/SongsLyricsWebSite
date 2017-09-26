package kz.javalab.songslyricswebsite.filter;


import kz.javalab.songslyricswebsite.constant.RequestConstants;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Locale;

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

        if (httpServletRequest.getSession().getAttribute(RequestConstants.SessionAttributes.LANGUAGE) == null) {
            String language = "en";
            String country = "US";

            Locale english = new Locale(language, country);

            httpServletRequest.getSession().setAttribute(RequestConstants.SessionAttributes.LANGUAGE, english);
        }

        filterChain.doFilter(httpServletRequest, servletResponse);
    }

    @Override
    public void destroy() {

    }
}
