package kz.javalab.songslyricswebsite.command.impl.localebasedcommand;

import kz.javalab.songslyricswebsite.command.LocaleBasedCommand;
import kz.javalab.songslyricswebsite.command.requestwrapper.RequestWrapper;
import kz.javalab.songslyricswebsite.constant.ResponseConstants;
import kz.javalab.songslyricswebsite.entity.password.Password;
import kz.javalab.songslyricswebsite.entity.user.User;
import kz.javalab.songslyricswebsite.exception.*;
import kz.javalab.songslyricswebsite.service.UsersManager;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * This class is responsible for registering a new user.
 */
public class RegisterCommand extends LocaleBasedCommand {

    public RegisterCommand() {
    }

    /**
     * Registers a new user and informs the user whether attempt of registering has been successful.
     * @param request Request to be handled.
     * @param response Response to be sent.
     * @throws ServletException Thrown if there is a server problem.
     * @throws IOException Thrown if some error occurred when attempted to send response.
     */
    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        Map<String, String> responseMap = new LinkedHashMap<>();

        ResourceBundle resourceBundle = ResourceBundle.getBundle("labels", getLocaleFromRequest(request));

        RequestWrapper requestWrapper = new RequestWrapper(request);

        UsersManager usersManager = new UsersManager(requestWrapper);

        try {
            usersManager.registerNewUser();
        } catch (SuchUserAlreadyExistsException e) {
            responseMap.put(ResponseConstants.Status.STATUS, ResponseConstants.Status.FAILURE);
            responseMap.put(ResponseConstants.Messages.MESSAGE, resourceBundle.getString(ResponseConstants.Messages.SUCH_USER_ALREADY_EXISTS));
            sendJsonResponse(responseMap, response);
        } catch (RegistrationFailedException e) {
            responseMap.put(ResponseConstants.Status.STATUS, ResponseConstants.Status.FAILURE);
            responseMap.put(ResponseConstants.Messages.MESSAGE, resourceBundle.getString(ResponseConstants.Messages.REGISTRATION_FAILED));
            sendJsonResponse(responseMap, response);
        } catch (InvalidUserNameException e) {
            responseMap.put(ResponseConstants.Status.STATUS, ResponseConstants.Status.FAILURE);
            responseMap.put(ResponseConstants.Messages.MESSAGE, resourceBundle.getString(ResponseConstants.Messages.INVALID_USERNAME));
            sendJsonResponse(responseMap, response);
        } catch (InvalidPasswordException e) {
            responseMap.put(ResponseConstants.Status.STATUS, ResponseConstants.Status.FAILURE);
            responseMap.put(ResponseConstants.Messages.MESSAGE, resourceBundle.getString(ResponseConstants.Messages.INVALID_PASSWORD));
            sendJsonResponse(responseMap, response);
        } catch (PasswordsAreNotEqualException e) {
            responseMap.put(ResponseConstants.Status.STATUS, ResponseConstants.Status.FAILURE);
            responseMap.put(ResponseConstants.Messages.MESSAGE, resourceBundle.getString(ResponseConstants.Messages.PASSWORDS_ARE_NOT_EQUAL));
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
