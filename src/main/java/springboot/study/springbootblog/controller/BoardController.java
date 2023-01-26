package springboot.study.springbootblog.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import springboot.study.springbootblog.config.auth.PrincipalDetail;
import springboot.study.springbootblog.service.BoardService;


@Controller
public class BoardController {

    @Autowired
    private BoardService boardService;

    // 스프링 시큐어리 설정을 통해 로그인된 사용자는 인덱스 폼으로 이동하게 된다.
    // 컨토롤러에서 세션을 받는 방는 방법 : index(@AuthenticationPrincipal PrincipalDetail principalDetail)
    // 확인 : System.out.println(principalDetail.getUsername() + "님이 로그인하였습니다.");
    @GetMapping({"","/"})
    public String index(Model model, @PageableDefault(size = 5, sort = "id", direction = Sort.Direction.DESC) Pageable pageable){
        // Board에 대한 데이터를 index에 전달
        // Controller은 리턴할 때, viewResolver가 작동하여 모델의 데이터를 가지고 이동한다.
        // 페이징하여 게시글 목록을 출력
        model.addAttribute("boards", boardService.boards(pageable));
        // application.yml mvc에서 JSP 파일 기본경로지정
        return "index";
    }

    //USER 권한필요
    @GetMapping("/board/saveForm")
    public String saveForm(){
        return "board/saveForm";
    }

    @GetMapping("/board/{id}")
    public String findById(@PathVariable int id, Model model){
        // Board 클래스에 Reply가 EAGER 전략으로 연관관계가 이어져 있기 때문에 Reply 데이터도 함께 넘어간다.
        model.addAttribute("board", boardService.boardDetail(id));

        return "board/detail";
    }

    @GetMapping("/board/{id}/updateForm")
    public String update(@PathVariable int id, Model model){
        model.addAttribute("board",boardService.boardDetail(id));
        return "board/updateForm";
    }
}
