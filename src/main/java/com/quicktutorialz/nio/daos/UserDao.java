package com.quicktutorialz.nio.daos;

import com.quicktutorialz.nio.entities.User;
import io.reactivex.Flowable;
import java.util.concurrent.Callable;

/**
 * This class simulate the access to a Database using:
 * 1. synchronous (and blocking) driver connection to the database;
 * 2. asyncronous (and blocking) driver connection to the database;
 * 3. asyncronous (and non-blocking) driver connection to the database.
 */
public interface UserDao {

    /* 3 */
    Flowable<User> createUserNio(String nameSurname);
    /* 2 */
    Callable<User> createUserAsync(String nameSurname);
    /* 1 */
    User createUserBlocking(String nameSurname);
}
