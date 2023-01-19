package springboot.study.springbootblog.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import springboot.study.springbootblog.model.Board;
import springboot.study.springbootblog.model.RoleType;
import springboot.study.springbootblog.model.User;
import springboot.study.springbootblog.repository.BoardRepository;
import springboot.study.springbootblog.repository.UserRepository;

import java.util.List;


// 스피링이 컴포넌트 스캔을 통해 Bean에 등록 (IoC를 해준다.)
@Service
public class BoardService {

    @Autowired
    private BoardRepository boardRepository;


    @Transactional
    // Board 객체로 들어오는 정보는 title, content 2개
    // user, count 직접 삽입
    // id, createTime DB에서 자동으로 삽입,
    // 글쓰기 저장
    public void writerSave(Board board, User user) {
        board.setCount(0);
        board.setUser(user);
        boardRepository.save(board);
    }

    // 게시글 목록 (페이징해서 출력, 페이징의 모든 속성을 사용하기 위해 Page 타입사용)
    @Transactional(readOnly = true)
    public Page<Board> boards(Pageable pageable){
        return boardRepository.findAll(pageable);
    }

    // 게시글 상세보기
    @Transactional(readOnly = true)
    public Board boardDetail(int id){
        return boardRepository.findById(id)
                .orElseThrow(()->{
                    return new IllegalArgumentException("글 상세보기 실패 : ID를 찾을 수 없습니다.");
                });
    }

    //게시글 삭제
    @Transactional
    public void delete(int id){
        boardRepository.deleteById(id);
    }

    //게시글 수정
    @Transactional
    public void update(int id, Board requestBoard){
        // 영속화
         Board board = boardRepository.findById(id)
                 .orElseThrow(()->{
                     return new IllegalArgumentException("글 수정 실패 : ID를 찾을 수 없습니다.");
                 });
         // Service 종료될 때, 트랜잭션이 종료 (더티 체킹으로 인해 자동으로 Flush)
         board.setTitle(requestBoard.getTitle());
         board.setContent(requestBoard.getContent());
    }


}
