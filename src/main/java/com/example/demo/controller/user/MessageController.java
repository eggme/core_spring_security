package com.example.demo.controller.user;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class MessageController {

    @GetMapping(value="/messages")
    public String mypage() throws Exception {

        return "user/messages";
    }

    @RequestMapping(value = "/api/messages")
    @ResponseBody
    public ResponseEntity apiMessage(){
        return ResponseEntity.ok().body("ok");
    }
}
