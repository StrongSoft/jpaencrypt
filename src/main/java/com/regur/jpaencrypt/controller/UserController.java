package com.regur.jpaencrypt.controller;

import com.regur.jpaencrypt.dto.UserRequestDto;
import com.regur.jpaencrypt.dto.UserResponseDto;
import com.regur.jpaencrypt.model.User;
import com.regur.jpaencrypt.repository.UserRepository;
import com.regur.jpaencrypt.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

/**
 * @author leeseungmin on 2019-09-17
 */
@Slf4j
@RestController
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
    public List<UserResponseDto> getUserList(@RequestBody UserRequestDto userRequestDto){
        List<UserResponseDto> userResponseDtoList = new ArrayList<>();
        log.info("userRequestDto : {}",userRequestDto.toString());
        List<User> userList = userService.findByHpNo(userRequestDto.getHpNo());
        for (User user: userList) {
            log.info(user.toString());
            UserResponseDto userResponseDto = modelMapper.map(user, UserResponseDto.class);
            userResponseDtoList.add(userResponseDto);
        }

        //List<User> userList = userRepository.findAll();

        //log.info("userList : {}",userList.get(0));
        //userResponseDto = modelMapper.map(userList.get(0), UserResponseDto.class);
        log.info("userResponseDto : {}",userResponseDtoList.toString());
        return userResponseDtoList;
    }
}
