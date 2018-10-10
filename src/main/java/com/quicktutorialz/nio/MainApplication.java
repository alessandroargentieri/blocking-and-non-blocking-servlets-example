package com.quicktutorialz.nio;

import com.quicktutorialz.nio.jetty.Jetty;
import com.quicktutorialz.nio.servlets.*;

/**
 * class with the main
 * it initialize the Jetty embedded server and set all the endpoints to it
 */
public class MainApplication {

    public static void main(String[] args) throws Exception {

        new Jetty().port(8986)
                   .endpoint(StatusBlockingServlet.class, "/status")
                   .endpoint(CreateUserBlockingServlet.class, "/create/user/blocking")
                   .endpoint(CreateUserNioServlet.class, "/create/user/nio")
                   .endpoint(CreateUserNioFromAsyncDriverServlet.class, "/create/user/nio/driver/async")
                   .endpoint(CreateUserNioFromAsyncServiceServlet.class, "/create/user/nio/service/async")
                   .start();

    }
}
