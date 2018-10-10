package com.quicktutorialz.nio.services;

import com.quicktutorialz.nio.entities.User;
import com.quicktutorialz.nio.entities.UserData;
import io.reactivex.Flowable;

import java.util.concurrent.Callable;

/**
 * This interface contains 4 behaviour in relation to fetch some data:
 * 1. recover in a synchronously (and blocking) way some data from database and pass it up synchronously (and blocking)
 * 2. recover in a asynchronously (and blocking) way some data from database and pass it up asynchronously (and blocking)
 * 3. recover in a asynchronously (and blocking) way some data from database and pass it up asynchronously (and non-blocking)
 * 4. recover in a asynchronously (and non-blocking) way some data from database and pass it up asynchronously (and non-blocking)
 */
public interface ReactiveService {
    /* 4 */
    Flowable<User> createUserCompletelyNIO(final UserData userData);
    /* 3 */
    Flowable<User> createUserNIOfromAsync(final UserData userData);
    /* 2 */
    Callable<User> createUserAsync(final UserData userData);
    /* 1 */
    User createUserBlocking(final UserData userData);

}
