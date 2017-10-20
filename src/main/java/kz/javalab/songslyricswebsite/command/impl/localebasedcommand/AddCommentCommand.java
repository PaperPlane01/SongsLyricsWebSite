package kz.javalab.songslyricswebsite.command.impl.localebasedcommand;

import kz.javalab.songslyricswebsite.command.LocaleBasedCommand;
import kz.javalab.songslyricswebsite.command.requestwrapper.RequestWrapper;
import kz.javalab.songslyricswebsite.constant.ResponseConstants;
import kz.javalab.songslyricswebsite.exception.*;
import kz.javalab.songslyricswebsite.service.CommentsManager;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;

/**
 * This class is responsible for adding comment of the user.
 */
public class AddCommentCommand extends LocaleBasedCommand {

    public AddCommentCommand() {
    }

    /**
     * Adds new comment and informs user whether attempt of adding comment has been successful.
     * @param request Request to be handled.
     * @param response Response to be sent.
     * @throws ServletException Thrown if there is a server problem.
     * @throws IOException Thrown if some error occurred when attempted to send response.
     */
    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        ResourceBundle resourceBundle = ResourceBundle.getBundle(ResponseConstants.Messages.LABELS, getLocaleFromRequest(request));
        Map<String, String> responseMap = new LinkedHashMap<>();
        RequestWrapper requestWrapper = new RequestWrapper(request);

        CommentsManager commentsManager = new CommentsManager(requestWrapper);

        try {
            commentsManager.addCommentToDatabase();
            responseMap.put(ResponseConstants.Status.STATUS, ResponseConstants.Status.SUCCESS);
            sendJsonResponse(responseMap, response);
        } catch (CommentAddingException | DataAccessException e) {
            responseMap.put(ResponseConstants.Status.STATUS, ResponseConstants.Status.FAILURE);
            responseMap.put(ResponseConstants.Messages.MESSAGE, resourceBundle.getString(ResponseConstants.Messages.ERROR_WHILE_ADDING_COMMENT));
            sendJsonResponse(responseMap, response);
        } catch (InvalidCommentContentException e) {
            responseMap.put(ResponseConstants.Status.STATUS, ResponseConstants.Status.FAILURE);
            responseMap.put(ResponseConstants.Messages.MESSAGE, resourceBundle.getString(ResponseConstants.Messages.INVALID_COMMENT_CONTENT));
            sendJsonResponse(responseMap, response);
        } catch (UserNotLoggedInException e) {
            responseMap.put(ResponseConstants.Status.STATUS, ResponseConstants.Status.FAILURE);
            responseMap.put(ResponseConstants.Messages.MESSAGE, resourceBundle.getString(ResponseConstants.Messages.COMMENT_RECEIVED_FROM_NOT_LOGGED_IN_USER));
            sendJsonResponse(responseMap, response);
        } catch (UserIsBlockedException e) {
            responseMap.put(ResponseConstants.Status.STATUS, ResponseConstants.Status.FAILURE);
            responseMap.put(ResponseConstants.Messages.MESSAGE, resourceBundle.getString(ResponseConstants.Messages.USER_IS_BLOCKED));
            sendJsonResponse(responseMap, response);
        }
    }
}
