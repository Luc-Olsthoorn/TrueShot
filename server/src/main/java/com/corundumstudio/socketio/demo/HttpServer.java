package com.corundumstudio.socketio.demo;
import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;



import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.DefaultServlet;
import org.eclipse.jetty.servlet.ServletHolder;

import org.eclipse.jetty.server.Request;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.AbstractHandler;

public class HttpServer 
{
    public HttpServer(int port)
    {
        Server server = new Server(port);

        ServletContextHandler ctx = new ServletContextHandler();
        ctx.setContextPath("/");

        DefaultServlet defaultServlet = new DefaultServlet();
        ServletHolder holderPwd = new ServletHolder("default", defaultServlet);
        holderPwd.setInitParameter("resourceBase", "./src/webapp/");

        ctx.addServlet(holderPwd, "/*");
        //ctx.addServlet(InfoServiceSocketServlet.class, "/info");

        server.setHandler(ctx);
        try{
            server.start();
            server.join();
        }catch (Exception e) {
                System.out.println("Static server error");
                e.printStackTrace(); 
            }
    }
}