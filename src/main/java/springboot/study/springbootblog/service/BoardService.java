package springboot.study.springbootblog.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import springboot.study.springbootblog.dto.ReplySaveRequestDto;
import springboot.study.springbootblog.model.Board;
import springboot.study.springbootblog.model.Reply;
import springboot.study.springbootblog.model.RoleType;
import springboot.study.springbootblog.model.User;
import springboot.study.springbootblog.repository.BoardRepository;
import springboot.study.springbootblog.repository.ReplyRepository;
import springboot.study.springbootblog.repository.UserRepository;

import java.util.List;


// 스피링이 컴포넌트 스캔을 통해 Bean에 등록 (IoC를 해준다.)
@Service
public class BoardService {

    // @Autowired 원리 : 스프링이 컴포넌트 스캔할 때 어노테이션을 보고 해당 클래스를 스프링 컨택스트에 저장하려고 할 때
    // 기본생성자를 사용하여 new 명령어를 통해 호출하는데 파라미터가 존재하는 생성자를 호출하기 위해서 해당 어노테이션을 통해
    // 파라미터 생성자를 만들어주는 역할을 한다.
    // @Autowired 어노테이션과 같은 역할을 하는 것은 @RequiredArgsConstructor 어노테이션을 클래스위에 붙혀주고
    // @Autowired 붙힐 클래스에 final 접근자를 붙혀주면 된다.

    @Autowired
    private BoardRepository boardRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    ReplyRepository replyRepository;


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

    // 댓글저장
    @Transactional
    public void writerReply(ReplySaveRequestDto replySaveRequestDto){
        // 영속화
        User user = userRepository.findById(replySaveRequestDto.getUserId())
                .orElseThrow(()->{
                    return new IllegalArgumentException("댓글 저장 실패 : 유저 ID를 찾을 수 없습니다.");
                });

        Board board = boardRepository.findById(replySaveRequestDto.getBoardId())
                .orElseThrow(()->{
                    return new IllegalArgumentException("댓글 저장 실패 : 게시글 ID를 찾을 수 없습니다.");
                });

        Reply reply = Reply.builder()
                .user(user)
                .board(board)
                .content(replySaveRequestDto.getContent())
                .build();

        // 영속화하는 다른 방법 (네이티브 쿼리, ReplyRepository에 생성)
        // replyRepository.mSave(replySaveRequestDto.getUserId(), replySaveRequestDto.getBoardId(), replySaveRequestDto.getContent());

        // 메서드를 생성하여 집어 넣어도 된다.
        // Reply reply = new Reply();
        // reply.update(user, board, replySaveRequestDto.getContent());

        replyRepository.save(reply);
    }

    // 댓글삭제
    @Transactional
    public void deleteReply(int replyId){
        replyRepository.deleteById(replyId);
    }


}
