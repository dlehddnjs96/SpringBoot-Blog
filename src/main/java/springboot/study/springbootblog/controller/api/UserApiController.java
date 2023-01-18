package springboot.study.springbootblog.controller.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import springboot.study.springbootblog.dto.ResponseDto;
import springboot.study.springbootblog.model.RoleType;
import springboot.study.springbootblog.model.User;
import springboot.study.springbootblog.service.UserService;

import javax.servlet.http.HttpSession;

@RestController
public class UserApiController {
    @Autowired
    private UserService userService;


    // user.js 에서 들어오는 요청에 대합 응답 로직
    @PostMapping("/auth/joinProc")
    // 회원가입
    public ResponseDto<Integer> save(@RequestBody User user){
        System.out.println("User 회원가입이 완료되어 DB에 저장합니다.");
        userService.join(user);
        // 자바오브잭트를 Json으로 변환해서 리턴 (Jackson)
        return new ResponseDto<Integer>(HttpStatus.OK.value(), 1);
    }

    // 스프링 시큐리티를 사용하기 전에 사용하던 방법
    // 스프링 시큐리티를 사용하면 시큐리티 설정(SecurityConfig)에서 로그인 기능을 구현
    // 스프링 시큐리티에서는 /logout 기능을 자동으로 생성해준다. (securityHeader 에서 로그아웃에 /logout만 연결)
    @PostMapping("/api/user/login")
    public ResponseDto<Integer> login(@RequestBody User user, HttpSession session){
        System.out.println(user.getUsername() + " 님이 로그인 했습니다.");
        // userService에서 DB 데이터와 요청으러 들어오는 데이터를 비교하여 같으면 데이터를 리턴 다르다면 null값이 리턴된다.
        User principal = userService.login(user); // principal : 접근주체

        if (principal != null){
            // 세션 생성
            session.setAttribute("principal",principal);
            return new ResponseDto<Integer>(HttpStatus.OK.value(),1);
        }
        return new ResponseDto<Integer>(HttpStatus.INTERNAL_SERVER_ERROR.value(),-1);
    }



}
