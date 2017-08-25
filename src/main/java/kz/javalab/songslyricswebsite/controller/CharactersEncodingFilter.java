package kz.javalab.songslyricswebsite.controller;

import javax.servlet.*;
import java.io.IOException;

/**
 * Created by PaperPlane on 24.08.2017.
 */
public class CharactersEncodingFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        servletResponse.setCharacterEncoding("UTF-8");
        filterChain.doFilter(servletRequest, servletResponse);
    }

    @Override
    public void destroy() {

    }
}
