package kz.javalab.songslyricswebsite.command.impl.localebasedcommand;

import kz.javalab.songslyricswebsite.command.LocaleBasedCommand;
import kz.javalab.songslyricswebsite.command.requestwrapper.RequestWrapper;
import kz.javalab.songslyricswebsite.constant.ResponseConstants;
import kz.javalab.songslyricswebsite.exception.CommentDeletingException;
import kz.javalab.songslyricswebsite.exception.NoPermissionException;
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
 * This class is responsible for deleting comment.
 */
public class DeleteCommentCommand extends LocaleBasedCommand {

    public DeleteCommentCommand() {
    }

    /**
     * Deletes the specified comment and informs the moderator whether attempt of deleting the comment has been successful.
     * @param request Request to be handled.
     * @param response Response to be sent.
     * @throws ServletException Thrown if there is a server problem.
     * @throws IOException Thrown if some error occurred when attempted to send response.
     */
    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        RequestWrapper requestWrapper = new RequestWrapper(request);
        CommentsManager commentsManager = new CommentsManager(requestWrapper);
        ResourceBundle resourceBundle = ResourceBundle.getBundle(ResponseConstants.Messages.LABELS, getLocaleFromRequest(request));
        Map<String, String> responseMap = new LinkedHashMap<>();

        try {
            commentsManager.deleteComment();
            responseMap.put(ResponseConstants.Status.STATUS, ResponseConstants.Status.SUCCESS);
            responseMap.put(ResponseConstants.Messages.MESSAGE, resourceBundle.getString(ResponseConstants.Messages.SUCCESSFUL_COMMENT_DELETING));
            sendJsonResponse(responseMap, response);
        } catch (CommentDeletingException e) {
            responseMap.put(ResponseConstants.Status.STATUS, ResponseConstants.Status.FAILURE);
            responseMap.put(ResponseConstants.Messages.MESSAGE, resourceBundle.getString(ResponseConstants.Messages.ERROR_WHILE_DELETING_COMMENT));
            sendJsonResponse(responseMap, response);
        } catch (NoPermissionException e) {
            responseMap.put(ResponseConstants.Status.STATUS, ResponseConstants.Status.FAILURE);
            responseMap.put(ResponseConstants.Messages.MESSAGE, resourceBundle.getString(ResponseConstants.Messages.NO_PERMISSION));
            sendJsonResponse(responseMap, response);
        }
    }

}

