package com.quicktutorialz.nio.servlets;

import com.quicktutorialz.nio.entities.UserData;
import com.quicktutorialz.nio.services.ReactiveService;
import com.quicktutorialz.nio.services.ReactiveServiceImpl;
import com.quicktutorialz.nio.utils.JsonConverter;

import javax.servlet.AsyncContext;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.WriteListener;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.ByteBuffer;

/**
 * This servlet is Non-Blocking and gets Non-blocking data from the service but got from a blocking asynchronous database driver
 */
public class CreateUserNioFromAsyncDriverServlet extends HttpServlet {

    private final ReactiveService service = ReactiveServiceImpl.getInstance();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        //get input
        UserData userData = (UserData) JsonConverter.getInstance().getDataFromBodyRequest(request, UserData.class);

        //elaborate and subscribe to response
        service.createUserNIOfromAsync(userData).subscribe(res -> nioResponse(request, response, JsonConverter.getInstance().getJsonOf(res)));
    }

    private synchronized void nioResponse(HttpServletRequest request, HttpServletResponse response, String jsonResponse) throws IOException {
        ByteBuffer finalContent = ByteBuffer.wrap(jsonResponse.getBytes());
        AsyncContext async = request.startAsync();
        response.setContentType("application/json");
        ServletOutputStream out = response.getOutputStream();
        out.setWriteListener(new WriteListener() {

            @Override
            public void onWritePossible() throws IOException {
                while (out.isReady()) {
                    if (!finalContent.hasRemaining()) {
                        response.setStatus(200);
                        async.complete();
                        return;
                    }
                    out.write(finalContent.get());
                }
            }

            @Override
            public void onError(Throwable t) {
                getServletContext().log("Async Error", t);
                async.complete();
            }
        });
    }

}
