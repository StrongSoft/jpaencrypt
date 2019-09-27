package com.regur.jpaencrypt.controller;

import com.regur.jpaencrypt.dto.UserRequestDto;
import com.regur.jpaencrypt.dto.UserResponseDto;
import com.regur.jpaencrypt.model.User;
import com.regur.jpaencrypt.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
public class UserController {
    @Autowired
    ModelMapper modelMapper;

    @Autowired
    private UserService userService;

    @PostMapping("/user")
    public UserResponseDto setUser(@RequestBody UserRequestDto userRequestDto){
        UserResponseDto userResponseDto = null;
        log.info("userRequestDto : {}",userRequestDto.toString());
        try{
            User user = new User();
            user.setName(userRequestDto.getName());
            user.setHpNo(userRequestDto.getHpNo());

            User resultUser = userService.save(user);
            userResponseDto = modelMapper.map(resultUser, UserResponseDto.class);
            log.info(userResponseDto.toString());
        } catch (Exception e){
            e.printStackTrace();
        }

        return userResponseDto;
    }

    @PostMapping("/userInfo")
    public UserResponseDto getUserList(@RequestBody UserRequestDto userRequestDto){
        UserResponseDto userResponseDto = (UserResponseDto) userService.findByHpNo(userRequestDto.getHpNo());
        return userResponseDto;
    }
}