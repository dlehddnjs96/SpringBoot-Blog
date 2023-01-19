package springboot.study.springbootblog.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class UserController {
    // 인증이 안된 사용자들이 출입 할 수 있는 경로룰 지정 :  /auth/*, /, static 이하에 있는 경로
    // 스프링 시큐리티에서는 /logout 기능을 자동으로 생성해준다. (securityHeader 에서 로그아웃에 /logout만 연결)
    // 로그아웃하여 인증이 없는 사용자는 시큐리티 설정에서 설정한 로그인 폼으로 이동하도록 설정

    @GetMapping("/auth/joinForm")
    public String joinForm(){
        return "user/joinForm";
    }

    @GetMapping("/auth/loginForm")
    public String loginForm(){
        return "user/loginForm";
    }
}
