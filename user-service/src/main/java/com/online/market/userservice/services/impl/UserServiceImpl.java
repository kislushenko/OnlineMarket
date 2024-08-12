package com.online.market.userservice.services.impl;

import com.online.market.userservice.dto.RegistrationRequest;
import com.online.market.userservice.dto.UserVO;
import com.online.market.userservice.model.User;
import com.online.market.userservice.repository.UserRepository;
import com.online.market.userservice.services.UserService;
import lombok.AllArgsConstructor;
import org.apache.sshd.common.config.keys.loader.openssh.kdf.BCrypt;
import org.springframework.stereotype.Service;


@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Override
    public void register(RegistrationRequest registrationRequest) {
        final User user = new User();
        user.setEmail(registrationRequest.getEmail());
        user.setPassword(BCrypt.hashpw(registrationRequest.getPassword(), BCrypt.gensalt()));
        user.setFirstName(registrationRequest.getFirstName());
        user.setLastName(registrationRequest.getLastName());
        userRepository.save(user);
    }

    @Override
    public UserVO getUserByEmail(String email) {
        final UserVO userVO = new UserVO();
        final User user = userRepository.findByEmail(email);
        if (user != null) {
            userVO.setEmail(user.getEmail());
            userVO.setPassword(user.getPassword());
            userVO.setRole(user.getRole());
        }
        return userVO;
    }
}
