package kz.javalab.songslyricswebsite.command.localebasedcommand;

import com.google.gson.Gson;;

import kz.javalab.songslyricswebsite.entity.password.Password;
import kz.javalab.songslyricswebsite.entity.user.User;
import kz.javalab.songslyricswebsite.exception.WrongPasswordException;
import kz.javalab.songslyricswebsite.exception.WrongUsernameException;
import kz.javalab.songslyricswebsite.service.UserService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;

/**
 * Created by PaperPlane on 08.08.2017.
 */
public class LoginCommand extends LocaleBasedCommand {

    public LoginCommand() {

    }

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        ResourceBundle labels = ResourceBundle.getBundle("labels", getLocaleFromRequest(request));
        Map<String, String> responseMap = new LinkedHashMap<>();

        String userName = request.getParameter("username");
        Password password = new Password();
        password.encodePassword(request.getParameter("password"));

        User user = new User(userName, password);

        UserService userService = new UserService();

        try {
            userService.doLogin(user);
            user.setID(userService.getUserIDByUserName(user.getUsername()));
            user.setUserType(userService.getUserTypeByUserID(user.getID()));
            request.getSession().setAttribute("user", user);

            responseMap.put("status", "SUCCESS");
            responseMap.put("message", labels.getString("labels.loginsuccess"));

            String json = new Gson().toJson(responseMap);

            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");
            response.getWriter().write(json);
        } catch (WrongPasswordException e) {
            responseMap.put("status", "FAILURE");
            responseMap.put("message", labels.getString("labels.loginfailed"));
            responseMap.put("reason", labels.getString("labels.wrongpassword"));

            String json = new Gson().toJson(responseMap);

            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");
            response.getWriter().write(json);
        } catch (WrongUsernameException e) {
            responseMap.put("status", "FAILURE");
            responseMap.put("message", labels.getString("labels.loginfailed"));
            responseMap.put("reason", labels.getString("labels.wrongusername"));

            String json = new Gson().toJson(responseMap);

            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");
            response.getWriter().write(json);
        }

    }

    @Override
    protected Locale getLocaleFromRequest(HttpServletRequest request) {
        return super.getLocaleFromRequest(request);
    }
}
