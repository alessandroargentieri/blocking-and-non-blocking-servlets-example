package com.quicktutorialz.nio.jetty;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletHandler;

/**
 * this class encapsulate the explicit and classic use of org.eclipse.jetty.server.Server libraries
 */
public class Jetty {

    private int port = 8080;
    private ServletHandler servletHandler= new ServletHandler();

    public Jetty port(int port){
        this.port = port;
        return this;
    }

    public Jetty endpoint(Class servletClass, String path){
        servletHandler.addServletWithMapping(servletClass, path);
        return this;
    }

    public void start() throws Exception {
        Server server = new Server(port);
        server.setHandler(servletHandler);
        server.start();
        server.join();
    }
}
