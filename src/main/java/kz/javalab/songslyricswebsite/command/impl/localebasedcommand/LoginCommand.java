package kz.javalab.songslyricswebsite.command.impl.localebasedcommand;

import kz.javalab.songslyricswebsite.command.LocaleBasedCommand;
import kz.javalab.songslyricswebsite.command.requestwrapper.RequestWrapper;
import kz.javalab.songslyricswebsite.constant.ResponseConstants;
import kz.javalab.songslyricswebsite.entity.password.Password;
import kz.javalab.songslyricswebsite.entity.user.User;
import kz.javalab.songslyricswebsite.exception.WrongPasswordException;
import kz.javalab.songslyricswebsite.exception.WrongUsernameException;
import kz.javalab.songslyricswebsite.service.UsersManager;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;

/**
 * This class is responsible for login.
 */
public class LoginCommand extends LocaleBasedCommand {

    public LoginCommand() {
    }

    /**
     * Does login and inform the user whether attempt of login has been successful.
     * @param request Request to be handled.
     * @param response Response to be sent.
     * @throws ServletException Thrown if there is a server problem.
     * @throws IOException Thrown if some error occurred when attempted to send response.
     */
    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        ResourceBundle labels = ResourceBundle.getBundle("labels", getLocaleFromRequest(request));
        Map<String, String> responseMap = new LinkedHashMap<>();

        RequestWrapper requestWrapper = new RequestWrapper(request);

        UsersManager usersManager = new UsersManager(requestWrapper);

        try {
            usersManager.doLogin();

            responseMap.put(ResponseConstants.Status.STATUS, ResponseConstants.Status.SUCCESS);
            responseMap.put(ResponseConstants.Messages.MESSAGE, labels.getString(ResponseConstants.Messages.LOGIN_SUCCESS));

            sendJsonResponse(responseMap, response);
        } catch (WrongPasswordException e) {
            responseMap.put(ResponseConstants.Status.STATUS, ResponseConstants.Status.FAILURE);
            responseMap.put(ResponseConstants.Messages.MESSAGE, labels.getString(ResponseConstants.Messages.LOGIN_FAILED));
            responseMap.put(ResponseConstants.Messages.REASON, labels.getString(ResponseConstants.Messages.WRONG_PASSWORD));

            sendJsonResponse(responseMap, response);
        } catch (WrongUsernameException e) {
            responseMap.put(ResponseConstants.Status.STATUS, ResponseConstants.Status.FAILURE);
            responseMap.put(ResponseConstants.Messages.MESSAGE, labels.getString(ResponseConstants.Messages.LOGIN_FAILED));
            responseMap.put(ResponseConstants.Messages.REASON, labels.getString(ResponseConstants.Messages.WRONG_USERNAME));

            sendJsonResponse(responseMap, response);
        }

    }

    @Override
    protected Locale getLocaleFromRequest(HttpServletRequest request) {
        return super.getLocaleFromRequest(request);
    }

    @Override
    protected void sendJsonResponse(Object responseObject, HttpServletResponse response) throws IOException {
        super.sendJsonResponse(responseObject, response);
    }
}
