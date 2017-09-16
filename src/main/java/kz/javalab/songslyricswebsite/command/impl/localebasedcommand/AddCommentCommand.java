package kz.javalab.songslyricswebsite.command.impl.localebasedcommand;

import kz.javalab.songslyricswebsite.command.LocaleBasedCommand;
import kz.javalab.songslyricswebsite.entity.comment.Comment;
import kz.javalab.songslyricswebsite.entity.user.User;
import kz.javalab.songslyricswebsite.exception.CommentAddingException;
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
 * Created by PaperPlane on 10.09.2017.
 */
public class AddCommentCommand extends LocaleBasedCommand {

    public AddCommentCommand() {
    }

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        User currentUser = (User) request.getSession().getAttribute("user");

        ResourceBundle resourceBundle = ResourceBundle.getBundle("labels", getLocaleFromRequest(request));
        Map<String, String> responseMap = new LinkedHashMap<>();

        CommentsManager commentsManager = new CommentsManager();

        int songID = Integer.valueOf(request.getParameter("songID"));
        String content = request.getParameter("content");

        Comment comment = new Comment();

        comment.setAuthor(currentUser);
        comment.setSongID(songID);
        comment.setContent(content);

        if (!validateComment(comment)) {
            responseMap.put("status", "FAILURE");
            responseMap.put("message", resourceBundle.getString("labels.errors.invalidcommentcontent"));
            sendJsonResponse(responseMap, response);
            return;
        }

        try {
            commentsManager.addCommentToDatabase(comment);
            responseMap.put("status", "SUCCESS");
            sendJsonResponse(responseMap, response);
        } catch (CommentAddingException e) {
            e.printStackTrace();
            responseMap.put("status", "FAILURE");
            responseMap.put("message", resourceBundle.getString("labels.errors.errorwhileaddingcomment"));
            sendJsonResponse(responseMap, response);
        }
    }

    private boolean validateComment(Comment comment) {
        return comment.getContent().length() <= 1000 && comment.getContent().length() >= 1;
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
