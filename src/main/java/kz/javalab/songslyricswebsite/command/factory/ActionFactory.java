package kz.javalab.songslyricswebsite.command.factory;

import com.sun.deploy.net.HttpRequest;
import kz.javalab.songslyricswebsite.command.ActionCommand;
import kz.javalab.songslyricswebsite.command.CommandEnum;
import kz.javalab.songslyricswebsite.command.impl.EmptyCommand;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by PaperPlane on 07.08.2017.
 */
public class ActionFactory {

    public ActionCommand defineCommand(HttpServletRequest request) {
        ActionCommand current = new EmptyCommand();
        String action = request.getParameter("command");

        if (action == null || action.isEmpty()) {
            return current;
        }

        try {
            CommandEnum commandEnum = CommandEnum.valueOf(action.toUpperCase());
            current = commandEnum.getCurrentCommand();
        } catch (IllegalArgumentException ex) {
            ex.printStackTrace();
        }

        return current;
    }
}
