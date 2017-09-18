package kz.javalab.songslyricswebsite.command.requestwrapper;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by PaperPlane on 18.09.2017.
 */
public class RequestWrapper {

    private HttpServletRequest request;

    public RequestWrapper() {
    }

    public RequestWrapper(HttpServletRequest request) {
        this.request = request;
    }

    public HttpServletRequest getRequest() {
        return request;
    }

    public void setRequest(HttpServletRequest request) {
        this.request = request;
    }

    public String getRequestParameter(String parameterName) {
        return request.getParameter(parameterName);
    }

    public Object getRequestAttribute(String attributeName) {
        return request.getAttribute(attributeName);
    }

    public void setRequestAttribute(String attributeName, Object attribute) {
        request.setAttribute(attributeName, attribute);
    }

    public Object getSessionAttribute(String attributeName) {
        return request.getSession().getAttribute(attributeName);
    }

    public void setSessionAttribute(String attributeName, Object attribute) {
        request.getSession().setAttribute(attributeName, attribute);
    }
}
