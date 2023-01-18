package springboot.study.springbootblog.controller;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import springboot.study.springbootblog.config.auth.PrincipalDetail;


@Controller
public class BoardController {

    // 스프링 시큐어리 설정을 통해 로그인된 사용자는 인덱스 폼으로 이동하게 된다.
    // 컨토롤러에서 세션을 받는 방는 방법
    @GetMapping({"","/"})
    public String index(@AuthenticationPrincipal PrincipalDetail principalDetail){
        // application.yml JSP 경로지정
        System.out.println(principalDetail.getUsername() + "님이 로그인하였습니다.");
        return "index";
    }
}
