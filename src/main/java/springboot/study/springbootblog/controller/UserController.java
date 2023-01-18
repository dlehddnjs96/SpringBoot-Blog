package springboot.study.springbootblog.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class UserController {
    // 인증이 안된 사용자들이 출입 할 수 있는 경로룰 지정 :  /auth/*, /, static 이하에 있는 경로

    @GetMapping("/auth/joinForm")
    public String joinForm(){
        return "user/joinForm";
    }

    @GetMapping("/auth/loginForm")
    public String loginForm(){
        return "user/loginForm";
    }
}
