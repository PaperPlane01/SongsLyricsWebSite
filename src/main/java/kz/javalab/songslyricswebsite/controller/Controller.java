package kz.javalab.songslyricswebsite.controller;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;

import kz.javalab.songslyricswebsite.command.ActionCommand;
import kz.javalab.songslyricswebsite.command.factory.ActionFactory;
import kz.javalab.songslyricswebsite.conntectionpool.ConnectionPool;
import kz.javalab.songslyricswebsite.model.song.Song;
import kz.javalab.songslyricswebsite.model.song.lyrics.SongLyrics;
import kz.javalab.songslyricswebsite.model.song.lyrics.SongLyricsComposite;
import kz.javalab.songslyricswebsite.resource.ConfigurationManager;
import kz.javalab.songslyricswebsite.service.SongsService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by PaperPlane on 15.07.2017.
 */
@WebServlet(name = "Controller")
public class Controller extends HttpServlet {

    @Override
    public void destroy() {
        ConnectionPool connectionPool = ConnectionPool.getInstance();
        connectionPool.closeConnections();
    }

    @Override
    public void init() throws ServletException {
        ConnectionPool.initConnectionPool();
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        processRequest(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        processRequest(request, response);
    }

    private void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        ActionFactory actionFactory = new ActionFactory();

        ActionCommand actionCommand = actionFactory.defineCommand(request);

        actionCommand.execute(request, response);
    }

}
