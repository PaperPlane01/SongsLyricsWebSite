package kz.javalab.songslyricswebsite.command;


import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by PaperPlane on 07.08.2017.
 */
public interface ActionCommand {

    void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException;
}
