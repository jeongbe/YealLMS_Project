package com.example.YealLMS.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class testController {

    @RequestMapping("/hello")
    public String hello(){
        return "layout/test";
    }

    @RequestMapping("/hello2")
    public String hello2(){
        return "layout/PContent";
    }

    @GetMapping("/hello3")
    public String hello3(){
        return "professor/Plecture";
    }

    @GetMapping("/hello4")
    public String hello4(){
        return "layout/SInfo";
    }
}
