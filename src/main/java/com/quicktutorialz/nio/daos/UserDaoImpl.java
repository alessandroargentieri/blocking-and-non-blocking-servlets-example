package com.quicktutorialz.nio.daos;

import com.quicktutorialz.nio.entities.User;
import io.reactivex.Flowable;

import java.util.concurrent.Callable;

public class UserDaoImpl implements UserDao {

    @Override
    public Flowable<User> createUserNio(String nameSurname) {
        return userNIOCreation(nameSurname);
    }

    @Override
    public Callable<User> createUserAsync(String nameSurname) {
        return userAsyncCreation(nameSurname);
    }

    @Override
    public User createUserBlocking(String nameSurname) {
        return userBlockingCreation(nameSurname);
    }


    //simulates NIO database driver
    private Flowable userNIOCreation(String nameSurname){
        try {
            Thread.sleep(3000);
        }catch (InterruptedException ie){
            ie.printStackTrace();
        }
        return Flowable.just(new User(nameSurname));
    }


    //simulates async database driver
    private Callable userAsyncCreation(String nameSurname){

        return () -> {
            Thread.sleep(3000);
            return new User(nameSurname);
        };

    }

    //simulates blocking database driver
    private User userBlockingCreation(String nameSurname){
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return new User(nameSurname);
    }


}
