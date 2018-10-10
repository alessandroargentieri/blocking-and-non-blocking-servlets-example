package com.quicktutorialz.nio.servlets;

import com.quicktutorialz.nio.entities.User;
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
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * This servlet is Non-Blocking and gets asynchronous blocking data from the service got from a blocking asynchronous database driver
 */
public class CreateUserNioFromAsyncServiceServlet extends HttpServlet {

    private final ReactiveService service = ReactiveServiceImpl.getInstance();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        //get input
        UserData userData = (UserData) JsonConverter.getInstance().getDataFromBodyRequest(request, UserData.class);

        //elaborate the async response from the service (Callable -> Future)
        ByteBuffer content = null;
        try {
            ExecutorService executorService = Executors.newSingleThreadExecutor();
            Future<User> future = executorService.submit(service.createUserAsync(userData));
            content = ByteBuffer.wrap(JsonConverter.getInstance().getJsonOf(future.get()).getBytes());
            executorService.shutdown();
        } catch (Exception e) {  e.printStackTrace();   }
        ByteBuffer finalContent = content;

        //arrange outputstream and set listener/callbacks
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
