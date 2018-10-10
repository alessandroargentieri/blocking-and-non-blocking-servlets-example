package com.quicktutorialz.nio.services;

import com.quicktutorialz.nio.daos.UserDao;
import com.quicktutorialz.nio.daos.UserDaoImpl;
import com.quicktutorialz.nio.entities.User;
import com.quicktutorialz.nio.entities.UserData;
import io.reactivex.Flowable;

import java.util.concurrent.Callable;


public class ReactiveServiceImpl implements ReactiveService {

    //singleton service instance: used in all the servlets
    private static ReactiveServiceImpl instance = null;
    public synchronized static ReactiveService getInstance(){
        if(instance == null){
            instance = new ReactiveServiceImpl();
        }
        return instance;
    }


    UserDao userDao = new UserDaoImpl();

    @Override
    public Flowable<User> createUserCompletelyNIO(final UserData userData){
        return userDao.createUserNio(userData.getName()+" "+userData.getSurname());
    }

    @Override
    public Flowable<User> createUserNIOfromAsync(final UserData userData){
        return Flowable.fromCallable( userDao.createUserAsync(userData.getName()+" "+userData.getSurname()));
    }

    @Override
    public Callable<User> createUserAsync(final UserData userData){
        return userDao.createUserAsync(userData.getName()+" "+userData.getSurname());
    }

    @Override
    public User createUserBlocking(final UserData userData){
        return userDao.createUserBlocking(userData.getName()+" "+userData.getSurname());
    }

}
