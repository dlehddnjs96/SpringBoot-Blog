package springboot.study.springbootblog.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import springboot.study.springbootblog.model.User;

import java.util.Optional;

// 현재 인터페이스는 JpaRepository로 User 테이블을 사용하고 기본키의 타입은 Integer 이다.
// DAO 역할을 하고 자동으로 빈 등록되어 DI(의존성 주입)이 가능하다. (@Repository 생략가능)
public interface UserRepository extends JpaRepository<User, Integer> {
    // JPA Naming 전략
    // SELECT * FROM user WHERE username=?1 AND password=?2;
    // 스프링 시큐리티를 사용하기 전에 사용하던 방법
    User findByUsernameAndPassword(String username, String password);
    // @Query(value="SELECT * FROM user WHERE username=?1 AND password=?2;", nativeQuery=true)
    // User login(String username, String password);


    // SELECT * FROM user WHERE username=?1
    Optional<User> findByUsername(String username);

}
