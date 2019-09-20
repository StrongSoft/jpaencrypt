package com.regur.jpaencrypt.controller;

import com.regur.jpaencrypt.dto.UserRequestDto;
import com.regur.jpaencrypt.dto.UserResponseDto;
import com.regur.jpaencrypt.model.User;
import com.regur.jpaencrypt.repository.UserRepository;
import com.regur.jpaencrypt.utils.CryptoUtil;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author leeseungmin on 2019-09-17
 */
@Slf4j
@RestController
public class UserController {
    @Autowired
    ModelMapper modelMapper;

    @Autowired
    private UserRepository userRepository;

    @PostMapping("/user")
    public UserResponseDto setUser(@RequestBody UserRequestDto userRequestDto){
        UserResponseDto userResponseDto = null;
        log.info("userRequestDto : {}",userRequestDto.toString());
        try{
            User user = new User();
            user.setName(userRequestDto.getName());
            user.setHpNo(userRequestDto.getHpNo());

            User resultUser = userRepository.save(user);
            userResponseDto = modelMapper.map(resultUser, UserResponseDto.class);
            log.info(userResponseDto.toString());
        } catch (Exception e){
            e.printStackTrace();
        }

        return userResponseDto;
    }
}
