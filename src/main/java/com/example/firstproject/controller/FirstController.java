package com.example.firstproject.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class FirstController {
    @GetMapping("/hi")
    public String niceToMeetYou(Model model){
        model.addAttribute("username", "홍팍");
        return "greetings"; //templates/greetings.mustache -> 브라우저로 전송
    }

    @GetMapping("/bye")
    public String seeYouNext(Model model){
        model.addAttribute("username", "홍팍");
        return "goodbye"; //templates/greetings.mustache -> 브라우저로 전송
    }

    @GetMapping("/")
    @ResponseBody
    public String mainPage(){


        return "안녕";
    }

}
