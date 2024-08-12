package com.online.market.userservice.services;

import com.online.market.userservice.dto.RegistrationRequest;
import com.online.market.userservice.dto.UserVO;

public interface UserService {

    void register(RegistrationRequest registrationRequest);
    UserVO getUserByEmail(String email);

}
