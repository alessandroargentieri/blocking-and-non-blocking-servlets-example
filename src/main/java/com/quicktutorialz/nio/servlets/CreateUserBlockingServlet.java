package com.quicktutorialz.nio.servlets;

import com.quicktutorialz.nio.entities.User;
import com.quicktutorialz.nio.entities.UserData;
import com.quicktutorialz.nio.services.ReactiveService;
import com.quicktutorialz.nio.services.ReactiveServiceImpl;
import com.quicktutorialz.nio.utils.JsonConverter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * This servlet uses a completely blocking approach
 */
public class CreateUserBlockingServlet extends HttpServlet {

    ReactiveService service = ReactiveServiceImpl.getInstance();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        UserData userData = (UserData) JsonConverter.getInstance().getDataFromBodyRequest(request, UserData.class);
        User resp = service.createUserBlocking(userData);

        response.setContentType("application/json");
        response.setStatus(HttpServletResponse.SC_OK);
        response.getWriter().println(JsonConverter.getInstance().getJsonOf(resp));
    }

}