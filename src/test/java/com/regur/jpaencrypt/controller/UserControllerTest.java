package com.regur.jpaencrypt.controller;

import com.regur.jpaencrypt.dto.UserRequestDto;
import com.regur.jpaencrypt.dto.UserResponseDto;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @author leeseungmin on 2019-09-17
 */
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
public class UserControllerTest {

    @Autowired
    TestRestTemplate restTemplate;

    @Test
    public void setUser() {
        //given
        UserRequestDto user = new UserRequestDto();
        user.setName("regur");
        user.setHpNo("01023182681");


        UserRequestDto user2 = new UserRequestDto();
        user2.setName("test");
        user2.setHpNo(user.getHpNo());

        System.out.println(user.toString());
        System.out.println(user2.toString());

        //when
        ResponseEntity<UserResponseDto> userResponseDto = restTemplate.postForEntity("http://localhost:8080/user", user, UserResponseDto.class);

        //then
        try {
            System.out.println(userResponseDto.getBody().getName());
            System.out.println(userResponseDto.getBody().getHpNo());
        } catch (Exception e){
            e.printStackTrace();
        }

    }

    @Test
    public void getUser(){

        //given
        UserRequestDto user = new UserRequestDto();
        user.setName("regur");
        user.setHpNo("01023182681");

        //when
        ResponseEntity<UserResponseDto> userResponseDto = restTemplate.postForEntity("http://localhost:8080/userInfo", user, UserResponseDto.class);

        //then
        try {
            System.out.println(userResponseDto.getBody().getName());
            System.out.println(userResponseDto.getBody().getHpNo());
        } catch (Exception e){
            e.printStackTrace();
        }
    }
}