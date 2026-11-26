package br.com.bkd.apiprinterapp.service;

import br.com.bkd.apiprinterapp.dto.user.request.CreateUserRequest;
import br.com.bkd.apiprinterapp.dto.user.response.CreatedUserResponse;
import br.com.bkd.apiprinterapp.model.User;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final User user;

    public UserService(User user) {
        this.user = user;
    }

    public CreatedUserResponse createUser(CreateUserRequest userDto) {

    }
}