package kz.javalab.songslyricswebsite.command.impl.localebasedcommand;

import kz.javalab.songslyricswebsite.command.LocaleBasedCommand;
import kz.javalab.songslyricswebsite.command.requestwrapper.RequestWrapper;
import kz.javalab.songslyricswebsite.constant.ResponseConstants;
import kz.javalab.songslyricswebsite.exception.InvalidUserIDException;
import kz.javalab.songslyricswebsite.exception.NoPermissionException;
import kz.javalab.songslyricswebsite.exception.UserUnblockingException;
import kz.javalab.songslyricswebsite.service.UsersManager;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;

/**
 * Created by PaperPlane on 24.09.2017.
 */
public class UnblockUserCommand extends LocaleBasedCommand {

    public UnblockUserCommand() {
    }

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        RequestWrapper requestWrapper = new RequestWrapper(request);
        Map<String, String> responseMap = new LinkedHashMap<>();
        ResourceBundle resourceBundle = ResourceBundle.getBundle(ResponseConstants.Messages.LABELS, getLocaleFromRequest(request));
        UsersManager usersManager = new UsersManager(requestWrapper);

        try {
            usersManager.unblockUser();
            responseMap.put(ResponseConstants.Status.STATUS, ResponseConstants.Status.SUCCESS);
            responseMap.put(ResponseConstants.Messages.MESSAGE, resourceBundle.getString(ResponseConstants.Messages.SUCCESSFUL_UNBLOCKING));
            sendJsonResponse(responseMap, response);
        } catch (InvalidUserIDException e) {
            responseMap.put(ResponseConstants.Status.STATUS, ResponseConstants.Status.FAILURE);
            responseMap.put(ResponseConstants.Messages.MESSAGE, resourceBundle.getString(ResponseConstants.Messages.NO_SUCH_USER));
            sendJsonResponse(responseMap, response);
        } catch (UserUnblockingException e) {
            responseMap.put(ResponseConstants.Status.STATUS, ResponseConstants.Status.FAILURE);
            responseMap.put(ResponseConstants.Messages.MESSAGE, resourceBundle.getString(ResponseConstants.Messages.ERROR_WHILE_UNBLOCKING));
            sendJsonResponse(responseMap, response);
        } catch (NoPermissionException e) {
            responseMap.put(ResponseConstants.Status.STATUS, ResponseConstants.Status.FAILURE);
            responseMap.put(ResponseConstants.Messages.MESSAGE, resourceBundle.getString(ResponseConstants.Messages.NO_PERMISSION));
            sendJsonResponse(responseMap, response);
        }
    }

    @Override
    protected Locale getLocaleFromRequest(HttpServletRequest request) {
        return super.getLocaleFromRequest(request);
    }

    @Override
    protected void sendJsonResponse(Object responseData, HttpServletResponse response) throws IOException {
        super.sendJsonResponse(responseData, response);
    }
}
