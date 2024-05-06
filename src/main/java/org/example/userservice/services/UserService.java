package org.example.userservice.services;


import org.apache.commons.lang3.RandomStringUtils;
import org.example.userservice.model.Token;
import org.example.userservice.model.User;
import org.example.userservice.repositories.TokenRepository;
import org.example.userservice.repositories.UserRepository;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.Optional;

@Service
public class UserService {

    private UserRepository userRepository;
    private BCryptPasswordEncoder bCryptPasswordEncoder;
    private TokenRepository tokenRepository;
    public UserService(UserRepository userRepository,BCryptPasswordEncoder bCryptPasswordEncoder,TokenRepository tokenRepository) {
        this.userRepository =userRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.tokenRepository = tokenRepository;
    }


    public User signup(String fullname,String email,String password){
        User u = new User();
        u.setName(fullname);
        u.setEmail(email);
        u.setHashedPassword(bCryptPasswordEncoder.encode(password));

        /* To use the hashpwd need a bcrpty password*/


        User user = userRepository.save(u);
        return user;
    }

    public Token login(String email, String password){
        Optional<User> userOptional = userRepository.findbyEmail(email);

        if(userOptional.isEmpty()){
                    // throw user not exists exception
            return null;
        }

        User user = userOptional.get();

            if(!bCryptPasswordEncoder.matches(password,user.getHashedPassword()))
            {
                // throw password not matching exception
                return null;
            }

            //if both has matched , generate token
        LocalDate today = LocalDate.now();
        LocalDate thirtyDaysLater = today.plus(30, ChronoUnit.DAYS);

        // Convert LocalDate to Date
        Date expiryDate = Date.from(thirtyDaysLater.atStartOfDay(ZoneId.systemDefault()).toInstant());

        Token token = new Token();
            token.setUser(user);
            token.setExpiryAt(expiryDate);

            token.setValue(RandomStringUtils.randomAlphanumeric(128));

            Token savt = tokenRepository.save(token);

        return  savt;
    }




}
