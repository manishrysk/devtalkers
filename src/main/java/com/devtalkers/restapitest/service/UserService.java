package com.devtalkers.restapitest.service;

import com.devtalkers.restapitest.entity.User;

public interface UserService {

    public User getUserById(Integer id);

    public User saveUser(User user);
}
