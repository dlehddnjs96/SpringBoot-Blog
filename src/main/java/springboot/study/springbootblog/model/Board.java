package springboot.study.springbootblog.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.Generated;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.List;


@Data //Getter,Setter
@NoArgsConstructor //기본생성자
@AllArgsConstructor //매개변수 생성자
@Builder //생성자 매개뱐스 자유롭게 사용
@Entity //DB 테이블 생성
public class Board {
    // 객체를 선언하여 사용한다면 프로젝트 -> JPA -> DB 과정에서 내가 Board에 대한 정보만 SELECT해도 JPA가 자동으로
    // 해당 객체와 관련된 테이블 정보를 JOIN하는 쿼리문을 DB에 요청하고 Board와 User 테이블이 JOIN된 결과값을 가져온다.
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(nullable = false, length = 100)
    private String title;

    @Lob //대용량 데이터
    private String content; //섬머노트 라이브러리를 사용 (<html>태그가 섞여서 디자인 된다.)

    @ColumnDefault("0")
    private  int count; //조회수

    @ManyToOne //Many(Board), One(User) 연관관계로 한명의 유저가 여러개의 게시글을 쓸 수 있음을 명시
    @JoinColumn(name="userId") //DB에서는 FK로 userId로 생성
    private User user; //DB는 객체를 저장할 수 없지만 자바는 오브젝트를 저장할 수 있다. (FK 사용X)

    // mappedBy : 연관관계의 주인이 아니라 FK를 DB 컬럼에 생성하지 않도록 설정 (Join 기능만 사용)
    @OneToMany(mappedBy = "board", fetch = FetchType.EAGER) //한 개의 게시글에 여려개의 답변이 가능하다.
    //  @JoinColumn을 사용하지 않는 이유 : 여러 개의 답변이 가능하므로 원자성이 깨질 수 있어 FK로 생성하지 않는다.
    private List<Reply> reply;

    @CreationTimestamp
    private Timestamp createDate;

    // fetch = FetchType.EAGER : 반드시 해당 객체가 필요한 경우에 사용하는 전략 (기본값으로 들어간다.)
    // fetch = FetchType.LAZY : 해당 객체가 조건부(펼치기 버튼.. 등등)에서 사용될 때 사용하는 전략 (mappedBy의 기본값으로 들어간다.)

}
