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
 * This servlet has a completely Non-Blocking and asynchronous approach to all the stack
 */
public class CreateUserNioServlet extends HttpServlet {

    private volatile String jsonResponse;

    private final ReactiveService service = ReactiveServiceImpl.getInstance();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        //get input
        UserData userData = (UserData) JsonConverter.getInstance().getDataFromBodyRequest(request, UserData.class);

        //elaborate and subscribe to response
        service.createUserCompletelyNIO(userData).subscribe(res -> wrapResponse(JsonConverter.getInstance().getJsonOf(res)));

        //arrange outputstream and set listener/callbacks
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

    private synchronized void wrapResponse(String jsonResponse){
        this.jsonResponse = jsonResponse;
    }

}
