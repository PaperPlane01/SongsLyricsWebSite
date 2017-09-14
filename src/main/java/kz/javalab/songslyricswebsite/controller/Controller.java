package kz.javalab.songslyricswebsite.controller;

import javax.servlet.http.HttpServlet;

import kz.javalab.songslyricswebsite.command.ActionCommand;
import kz.javalab.songslyricswebsite.command.factory.ActionFactory;
import kz.javalab.songslyricswebsite.conntectionpool.ConnectionPool;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
/**
 * Created by PaperPlane on 15.07.2017.
 */
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

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        processRequest(request, response);
    }

    private void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        ActionFactory actionFactory = new ActionFactory();

        ActionCommand actionCommand = actionFactory.defineCommand(request);

        actionCommand.execute(request, response);
    }

}
