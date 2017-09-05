package kz.javalab.songslyricswebsite.command.localebasedcommand;

import com.google.gson.Gson;
import kz.javalab.songslyricswebsite.entity.password.Password;
import kz.javalab.songslyricswebsite.entity.user.User;
import kz.javalab.songslyricswebsite.exception.RegistrationFailedException;
import kz.javalab.songslyricswebsite.exception.SuchUserAlreadyExistsException;
import kz.javalab.songslyricswebsite.service.UserService;

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
 * Created by PaperPlane on 12.08.2017.
 */
public class RegisterCommand extends LocaleBasedCommand {

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        Map<String, String> responseMap = new LinkedHashMap<>();

        ResourceBundle resourceBundle = ResourceBundle.getBundle("labels", getLocaleFromRequest(request));

        String userName = request.getParameter("userName");

        String unEncodedPassword = request.getParameter("password");
        String secondPassword = request.getParameter("secondPassword");

        if (!validateUserName(userName)) {
            responseMap.put("status", "FAILURE");
            responseMap.put("message", resourceBundle.getString("labels.errors.invalidusername.general"));
            sendJsonResponse(responseMap, response);
        }

        if (!validatePassword(unEncodedPassword)) {
            responseMap.put("status", "FAILURE");
            responseMap.put("message", resourceBundle.getString("labels.errors.invalidpassword.general"));
            sendJsonResponse(responseMap, response);
        }

        if (!unEncodedPassword.equals(secondPassword)) {
            responseMap.put("status", "FAILURE");
            responseMap.put("message", resourceBundle.getString("labels.errors.passwordsarentequal"));
            sendJsonResponse(responseMap, response);
        }

        User user = new User();
        user.setUsername(userName);
        Password password = new Password();
        password.encodePassword(unEncodedPassword);
        user.setPassword(password);

        UserService userService = new UserService();

        try {
            userService.registerNewUser(user);
            responseMap.put("status", "SUCCESS");
            responseMap.put("message", resourceBundle.getString("labels.registrationsuccess"));
            sendJsonResponse(responseMap, response);
        } catch (SuchUserAlreadyExistsException e) {
            responseMap.put("status", "FAILURE");
            responseMap.put("message", resourceBundle.getString("labels.suchuseraleadyexists"));
            sendJsonResponse(responseMap, response);
        } catch (RegistrationFailedException e) {
            responseMap.put("status", "FAILURE");
            responseMap.put("message", resourceBundle.getString("labels.registrationfailed"));
            sendJsonResponse(responseMap, response);
        }

    }

    private boolean validateUserName(String userName) {
        int minSize = 3;
        int maxSize = 20;

        if (userName == null) {
            return false;
        }

        if (userName.isEmpty()) {
            return false;
        }

        if (userName.length() < minSize) {
            return false;
        }

        if (userName.length() > maxSize) {
            return  false;
        }

        Pattern pattern = Pattern.compile("[a-zA-Zа-яА-Я_.#$%^&]*");
        Matcher matcher = pattern.matcher(userName);

        if (!matcher.matches()) {
            return false;
        }

        return true;
    }

    private boolean validatePassword(String unEncodedPassword) {
        int minSize = 5;
        int maxSize = 25;

        if (unEncodedPassword == null) {
            return false;
        }

        if (unEncodedPassword.isEmpty()) {
            return false;
        }

        if (unEncodedPassword.length() < minSize) {
            return false;
        }

        if (unEncodedPassword.length() > maxSize) {
            return false;
        }

        Pattern pattern = Pattern.compile("[a-zA-Zа-яА-Я_.#$%^&]*");
        Matcher matcher = pattern.matcher(unEncodedPassword);

        if (!(matcher.matches())) {
            return false;
        }

        return true;
    }

    private void sendJsonResponse(Map<String, String> responseMap, HttpServletResponse response) throws IOException {
        String json = new Gson().toJson(responseMap);

        response.setContentType("application/json");
        response.getWriter().write(json);
    }

    @Override
    protected Locale getLocaleFromRequest(HttpServletRequest request) {
        return super.getLocaleFromRequest(request);
    }
}
