package springboot.study.springbootblog.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import springboot.study.springbootblog.dto.ReplySaveRequestDto;

import javax.jws.soap.SOAPBinding;
import javax.persistence.*;
import java.sql.Timestamp;

@Data //Getter,Setter
@NoArgsConstructor //기본생성자
@AllArgsConstructor //매개변수 생성자
@Builder //생성자 매개뱐스 자유롭게 사용
@Entity //DB 테이블 생성
public class Reply {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(nullable = false, length = 200)
    private String content;

    @ManyToOne //햔 개의 게시글에 여러 개의 답변이 가능하다.
    @JoinColumn(name = "boardId")
    private Board board;

    @ManyToOne //한 개의 유저는 여러 개의 답변이 가능하다.
    @JoinColumn(name = "userId")
    private User user;

    @CreationTimestamp
    private Timestamp createDate;

    public void update(User user, Board board, String content){
        setUser(user);
        setBoard(board);
        setContent(content);
    }
}
