package kz.javalab.songslyricswebsite.command.requestwrapper;

import javax.servlet.http.HttpServletRequest;

/**
 * This class is designated for wrapping <Code>HttpServletRequest</Code> instance.
 * It contains methods for accessing request parameters and session attributes.
 */
public class RequestWrapper {

    /**
     * <Code>HttpServletRequest</Code>
     */
    private HttpServletRequest request;

    /**
     * Constructs <Code>RequestWrapper</Code> instance.
     */
    public RequestWrapper() {
    }

    /**
     * Constructs <Code>RequestWrapper</Code> instance with pre-defined request.
     * @param request <Code>HttpServletRequest</Code> instance to be wrapped.
     */
    public RequestWrapper(HttpServletRequest request) {
        this.request = request;
    }

    /**
     * Returns <Code>HttpServletRequest</Code> instance which has been wrapped.
     * @return <Code>HttpServletRequest</Code> instance which has been wrapped.
     */
    public HttpServletRequest getRequest() {
        return request;
    }

    /**
     * Sets new <Code>HttpServletRequest</Code> instance which is to be wrapped.
     * @param request New <Code>HttpServletRequest</Code> instance which is to be wrapped.
     */
    public void setRequest(HttpServletRequest request) {
        this.request = request;
    }

    /**
     * Returns the specified parameter of request.
     * @param parameterName Name of the parameter which is to be accessed.
     * @return Specified parameter of request.
     */
    public String getRequestParameter(String parameterName) {
        return request.getParameter(parameterName);
    }

    /**
     * Returns the specified attribute of session.
     * @param attributeName Name of the attribute which is to be accessed.
     * @return Specified attribute of session.
     */
    public Object getSessionAttribute(String attributeName) {
        return request.getSession().getAttribute(attributeName);
    }

    /**
     * Sets attribute to the session.
     * @param attributeName Name of the attribute to be set.
     * @param attribute Object which is to be set as attribute of the session.
     */
    public void setSessionAttribute(String attributeName, Object attribute) {
        request.getSession().setAttribute(attributeName, attribute);
    }
}
