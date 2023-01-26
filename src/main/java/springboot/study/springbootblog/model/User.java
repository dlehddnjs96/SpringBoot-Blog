package springboot.study.springbootblog.model;

import com.sun.istack.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.DynamicInsert;

import javax.persistence.*;
import java.sql.Timestamp;

// ORM : Java(다른언어) -> 테이블로 매핑해주는 기술
// @Entity : 클래스가 자동으로 MySQL 테이블이 생성된다.
@Data //Getter,Setter
@NoArgsConstructor //기본생성자
@AllArgsConstructor //매개변수 생성자
@Builder //생성자 매개뱐스 자유롭게 사용
@Entity //DB 테이블 생성
public class User {

    @Id //Primary Key
    @GeneratedValue(strategy = GenerationType.IDENTITY) //프로젝트에 연결된 DB의 넘버링 전략을 따라간다.
    private int id; //Oracle은 시퀀스, MySQL은 auto_increment 전략을 따라간다.

    @Column(nullable = false, length = 200, unique = true)
    private String username;

    @Column(length = 100) //비밀번호를 해쉬로 암호화하기 때문에 충분한 공간이 필요
    private String password;

    @Column(nullable = false, length = 50)
    private String email;

    // @ColumnDefault은 값이 NULL이 아닌 삽입되지 않을 때 대입되는 값이므로 아래의 어노테이션을 통해 해당 필드를 제외시켜 줄 수 있다.
    // @DynamicInsert : INSERT 할 때 NULL 인 필드는 제외하고 들어간다. (현재 클래스 위에 사용)
    // @ColumnDefault("'user'") //회원가입 시 기본값 (''를 통해 문자임을 알려준다, 아래처럼 String 타입일 때 사용)
    // private String role; //Enum을 사용하는 것이 좋다. (권한에 따른 역할구분)
    @Enumerated(EnumType.STRING) //DB에 RolType 이라는 타입이 없기 때문에 String 라고 알려준다.
    private RoleType role; //실수를 줄이기 위해 Enum 사용
    
    // 일반계정과 카카오계정을 구분하기 위해 생성
    private String oauth;

    @CreationTimestamp //시간이 자동으로 입력
    private Timestamp createDate;

}
