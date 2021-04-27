package com.example.demo.controller.user;

import com.example.demo.domain.Account;
import com.example.demo.domain.AccountDTO;
import com.example.demo.service.impl.UserServiceImpl;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class UserController {

    @Autowired
    private UserServiceImpl userService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @GetMapping(value="/mypage")
    public String myPage() throws Exception {
        return "user/mypage";
    }

    @GetMapping(value = "/users")
    public String createUser(){
        return "user/login/register";
    }

    @PostMapping(value = "/users")
    public String createUser(@ModelAttribute AccountDTO accountDTO){

        ModelMapper modelMapper = new ModelMapper();
        Account account = modelMapper.map(accountDTO, Account.class);
        account.setPassword(passwordEncoder.encode(account.getPassword()));
        userService.createUser(account);

        return "redirect:/";
    }
}
