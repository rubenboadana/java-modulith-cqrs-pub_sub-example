package com.iobuilders.domain;

import com.iobuilders.domain.dto.JwtToken;
import com.iobuilders.domain.dto.LoginRequest;
import com.iobuilders.domain.dto.User;
import com.iobuilders.domain.dto.UserID;

public interface UserService {

    UserID create(User user);

    void delete(Long id);

    User update(Long id, User user);

    JwtToken login(LoginRequest loginRequest);
}