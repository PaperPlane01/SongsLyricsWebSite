package kz.javalab.songslyricswebsite.filter;

import javax.servlet.*;
import java.io.IOException;

/**
 * This filter is responsible for setting "UTF-8" character encoding to all incoming requests
 * and outcoming responses.
 */
public class CharactersEncodingFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        String characterEncoding = "UTF-8";
        servletRequest.setCharacterEncoding(characterEncoding);
        servletResponse.setCharacterEncoding(characterEncoding);
        filterChain.doFilter(servletRequest, servletResponse);
    }

    @Override
    public void destroy() {

    }
}
