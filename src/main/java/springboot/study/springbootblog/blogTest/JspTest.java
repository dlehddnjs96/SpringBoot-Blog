package springboot.study.springbootblog.blogTest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class JspTest {

    // @RestController는 문자 그 자체(데이터)를 return 하는 반면 ,
    // @Controller는 해당 경로 이하에 있는 파일을 return 하므로 슬러시(/)를 붙여야 한다.
    // JSP 파일은 정적 파일이 아니므로 웹서버인 아파치가 처리하지 못한다. 톰캣이 대신 컴파일해서 웹브라우저에게 전달한다.
    @GetMapping("/http/jsp")
    public String jspTest() {
        // 프로젝트 설정에서(application.yml) 경로와 확장자를 이미 지정하여 jsp 파일명만 적는다.
        return "test";
    }
}
