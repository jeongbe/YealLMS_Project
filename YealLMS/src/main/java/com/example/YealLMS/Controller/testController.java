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
    
     @GetMapping("/certi")
    public String certi(){
        return "join,login/Certi";
    }

    @GetMapping("/join")
    public String join(){
        return "join,login/join";
    }

    @GetMapping("/login")
    public String login(){
        return "join,login/login";
    }

    @GetMapping("/stumain")
    public String stumain(){
        return "student/stuMain";
    }

    @GetMapping("/stuinfo")
    public String stuinfo(){
        return "student/stuInfo";
    }

    @GetMapping("/lecpur")
    public String lecpurr(){
        return "student/lecpur";
    }

    @GetMapping("/delec")
    public String delecc(){
        return "student/delec";
    }

    @GetMapping("/lecdenot")
    public String lecdenott(){

        return "student/lecdenot";
    }

    @GetMapping("/s/ass")
    public String test4(){

        return "Ass/SAssInsert";
    }

    @GetMapping("/ajsi")
    public String test0(){

        return "StdList";
    }
}
