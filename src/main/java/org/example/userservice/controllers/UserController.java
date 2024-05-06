package org.example.userservice.controllers;

import org.example.userservice.dtos.LoginRequestDto;
import org.example.userservice.dtos.SignUpRequestDto;
import org.example.userservice.model.Token;
import org.example.userservice.model.User;
import org.example.userservice.services.UserService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
public class UserController {

    private UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/login")
    public Token login(@RequestBody LoginRequestDto request){
        // check if email and password in db
        // if yes return user
        // else throw some error

        return userService.login(request.getEmail(), request.getPassword());
    }
    @PostMapping("/signup")
    //creation
    public User signup(@RequestBody SignUpRequestDto request ){
        String email = request.getEmail();
        String name = request.getName();
        String password = request.getPassword();

    return userService.signup(name,email,password);
    }

    //login will return a Token



}
