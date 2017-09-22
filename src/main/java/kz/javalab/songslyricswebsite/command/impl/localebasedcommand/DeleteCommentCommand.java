package kz.javalab.songslyricswebsite.command.impl.localebasedcommand;

import kz.javalab.songslyricswebsite.command.LocaleBasedCommand;
import kz.javalab.songslyricswebsite.command.requestwrapper.RequestWrapper;
import kz.javalab.songslyricswebsite.constant.ResponseConstants;
import kz.javalab.songslyricswebsite.exception.CommentAlteringException;
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
 * Created by PaperPlane on 22.09.2017.
 */
public class DeleteCommentCommand extends LocaleBasedCommand {

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
        } catch (CommentAlteringException e) {
            e.printStackTrace();
            responseMap.put(ResponseConstants.Status.STATUS, ResponseConstants.Status.FAILURE);
            responseMap.put(ResponseConstants.Messages.MESSAGE, resourceBundle.getString(ResponseConstants.Messages.ERROR_WHILE_DELETING_COMMENT));
            sendJsonResponse(responseMap, response);
        } catch (NoPermissionException e) {
            e.printStackTrace();
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

