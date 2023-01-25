package springboot.study.springbootblog.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import springboot.study.springbootblog.model.RoleType;
import springboot.study.springbootblog.model.User;
import springboot.study.springbootblog.repository.UserRepository;


// 스피링이 컴포넌트 스캔을 통해 Bean에 등록 (IoC를 해준다.)
@Service
public class UserService {
    // 서비스가 필요한 이유
    // 트랜잭션 관리 : DB에 데이터를 저장하고 관리 (트랜잭션 : 일이 처리되기 위한 가장 작은 단위)
    // 오라클 DB 격리수준 : 'Read Commit'을 기반으로 만약 다른 쪽에서 Update가 이루어진 데이터가 있어도 다른 쪽이 Commit이 이루어지기 전까지는 그 전에 데이터를 SELECT 하고 Commit 후에는 그 데이터를 SELECT 한다. (부정합 발생)
    // MySQL DB 격리수준 : 'Repeatable Read'을 기반으로 만약 다른 쪽에서 Update가 이루어진 데이터가 있어도 내가 commit이 이루어지기 전까지는 그전에 데이터를 SELECT 한다. (부정합 발생X, 자기 트랜잭션 번호보다 낮은 Undo 로그를 보고 SELECT)
    // CRUD를 할 때 SELECT에서는 트랜잭션이 굳이 필요하지 않지만 DB의 정합성을 위해 트랜잭션을 하는게 좋다.
    // 스프링의 전통적인 트랜잭션
    // 스프링이 시작하며 톰캣이 시작하고 web.xml, context.xml 을 읽고 시작한다.
    // 요청이 들어오면 web.xml에서 DB 연결 세션이 생성되고 트랜잭션, 영속성 컨택스트가 시작된다..
    // 만약 update 요청에 대한 모든 일이 끝나고 Controller에서 트랜잭션, DB 연결을 종료시키고 자동 Commit되어 영속성 컨텍스트에서 JPA가 변경을 감지하여 DB flush하여 DB에 실제 변경이 이뤄지면 영속성 컨텍스트를 종료한다.
    // 마지막으로, 응답을 해주면 DB세션을 종료한다.
    // 스프링의 트랜잭션 개선
    // 스프링이 시작하며 톰캣이 시작하고 web.xml, context.xml 을 읽고 시작한다.
    // 요청이 들어오면 web.xml에서 영속성 컨택스트가 시작된다..
    // 만약 update 요청에 대한 모든 일이 끝나고 Service 들어가기 직전에  DB 연결 세션이 생성되고 트랜잭션을 시작한다.
    // Service에서 모든 요청에 대한 로직이 마무리되면 바로 DB 연결, 트랜잭션, 영속성 컨택스트를 종료하여 DB 부하를 많이 줄여준다.
    // 하지만 Service가 끝나고 모든 연결을 끊어주면 만약 Controller에서 데이터가 더 필요할 경우 사용할 수가 없게 된다.
    // 그래서, fetch 전략 중 LAZY 전략을 이용하면 해당 데이터와 관련된 데이터를 프록시 객체 (빈 객체)로 가져오게 되는데
    // 영속성 컨택스트 종료만 Controller이 끝난 뒤로 미뤄주면 관련 데이터가 필요할 때 영속성 컨택스트에 있는 프록시 객체에 대한 데이터를 빼오기 위해 잠시만 DB 커넥션을 하여 가져온다.
    // 이것을 가능하게 해주는 것이 yml 파일에 jap (open-in-view) 설정이다. (프록시 객체에 대한 Lazy Loding을 가능하게 된다.)
    // 스프링부트에서는 open-in-view를 기본적으로 true로 설정된다.

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private BCryptPasswordEncoder encoder;

    @Transactional
    // User 객체로 들어오는 정보는 username, email, password 3개
    // id, createTime DB에서 자동으로 삽입, role은 직접 삽입
    // 회원가입
    public void join(User user) {
        String rawPassword = user.getPassword(); // 원래 패스워드
        String encPassword = encoder.encode(rawPassword); // 해시 암호화 패스워드
        user.setPassword(encPassword); // 해시 암호화된 패스워드를 DB에 저장 
        user.setRole(RoleType.USER);
        userRepository.save(user);
    }
    
    // SELECT 할때 트랜잭션이 시작하여 정합성을 유지 (스프링 시큐리티를 사용하기 전에 사용하던 방법)
    // 로그인
    // 스프링 시큐리티가 있으면 로그인 요청시 시큐리티가 username, password를 가로채서 시큐리티 세션으로 가로채서 유저 정보를 등록한다.
    // IoC에 등록된 유저 정보가 필요할때마다 DI를 이용하여 시큐리티 세션에서 가져와서 사용한다.
    // 세션에 있는 유저 정보의 타입은 프로잭트에서 생성한 User 타입이 아닌 UserDetails 타입으로 저장된다. (extends를 이용하여 저장가능)
    @Transactional(readOnly = true)
    public User login(User user) {
        return userRepository.findByUsernameAndPassword(user.getUsername(), user.getPassword());
    }

    @Transactional
    public void update(User user) {
        // 수정시에는 JPA 영속성 컨텍스트 User 오브젝트를 영속화를 시키고, 영속화된 User 오브젝트를 수정
        // SELECT를 해서 User 오브젝트를 DB로 부터 가져와서 영속화 (영속화된 오브젝트를 변경하면 DB에서 자동으로 UPDATE)
        User persistence = userRepository.findById(user.getId()).orElseThrow(()->{
           return  new IllegalArgumentException("회원정보를 찾을 수 없습니다.");
        });
        String rawPassword = user.getPassword();
        String encPassword = encoder.encode(rawPassword);
        persistence.setPassword(encPassword);
        persistence.setEmail(user.getEmail());
        // 회원수정 함수 종료 = 서비스 종료 = 트랜젝션 종료 = commit이 자동으로 이루어짐 (영속화된 persistence 객체의 변화가 감지되면 더티체킹이 되어 변화된 것들을 Flush한다.)
    }



}
