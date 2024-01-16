package com.example.firstproject.api;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController //RestAPI용 컨트롤러! JSON을반환
public class JavaApiController {

    @GetMapping("/api/hello")
    public String hello(){
        return "hello world!"; //텍스트 반환 가능
    }


}
