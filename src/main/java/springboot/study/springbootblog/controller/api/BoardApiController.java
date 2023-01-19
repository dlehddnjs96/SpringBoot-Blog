package springboot.study.springbootblog.controller.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import springboot.study.springbootblog.config.auth.PrincipalDetail;
import springboot.study.springbootblog.dto.ResponseDto;
import springboot.study.springbootblog.model.Board;
import springboot.study.springbootblog.model.User;
import springboot.study.springbootblog.service.BoardService;

@RestController
public class BoardApiController {

    @Autowired
    private BoardService boardService;

    // 요청에서 들어온 데이터는 ApiController, 여기서 사용할 기능은 Service
    @PostMapping("/api/board")
    // 글쓰기 저장
    public ResponseDto<Integer> save(@RequestBody Board board, @AuthenticationPrincipal PrincipalDetail principalDetail){
        System.out.println("Board 내용이 DB에 저장합니다.");
        // board의 제목, 내용과 작성자의 id를 DB에 저장
        boardService.writerSave(board, principalDetail.getUser());
        // 자바오브잭트를 Json으로 변환해서 리턴 (Jackson)
        return new ResponseDto<Integer>(HttpStatus.OK.value(), 1);
    }

    @DeleteMapping("/api/board/{id}")
    //게시글 삭제
    public ResponseDto<Integer> deleteById(@PathVariable int id){
        boardService.delete(id);
        return new ResponseDto<Integer>(HttpStatus.OK.value(), 1);
    }

    @PutMapping("/api/board/{id}")
    //게시글 수정
    public ResponseDto<Integer> updateById(@PathVariable int id, @RequestBody Board board){
        boardService.update(id, board);
        return new ResponseDto<Integer>(HttpStatus.OK.value(), 1);
    }



}
