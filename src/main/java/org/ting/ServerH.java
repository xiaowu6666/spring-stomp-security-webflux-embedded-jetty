package org.ting;


import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.ServerConnector;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.springframework.http.server.reactive.HttpHandler;
import org.springframework.http.server.reactive.JettyHttpHandlerAdapter;
import org.springframework.web.reactive.DispatcherHandler;
import org.springframework.web.server.adapter.HttpWebHandlerAdapter;

import javax.servlet.Servlet;

public class ServerH {

     public static void main(String[] args) throws Exception {
         HttpHandler handler = new HttpWebHandlerAdapter(new DispatcherHandler(null));
         Servlet servlet = new JettyHttpHandlerAdapter(handler);

         Server server = new Server();
         ServletContextHandler contextHandler = new ServletContextHandler(server, "");
         contextHandler.addServlet(new ServletHolder(servlet), "/");
         contextHandler.start();

         ServerConnector connector = new ServerConnector(server);
         connector.setPort(9999);
         server.addConnector(connector);
         server.start();
    }
}
