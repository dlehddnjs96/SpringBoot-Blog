package springboot.study.springbootblog.blogTest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;
import springboot.study.springbootblog.model.RoleType;
import springboot.study.springbootblog.model.User;
import springboot.study.springbootblog.repository.UserRepository;

import javax.transaction.Transactional;
import java.util.EmptyStackException;
import java.util.List;
import java.util.function.Supplier;

@RestController
public class DummyControllerTest {
    // CRUD 테스트

    // 생성하지 않은 객체는 NULL값을 가지고 있다.

    // 어떠한 요청을 받는다면 모든 요청은 모두 Controller에 모여서 응답을 받는다.

    // Json 데이터로 통신 : Get요청은 주소에 데이터를 담아서 보내고 (데이터 형태 : key=value)
    // Post, Put, Delete는 Body에 데이터를 담아 보낸다 (데이터 형태 : Json으로 통일 하는것이 좋다.)
    // Body를 통해 데이터를 보내는 방법으로는 Form 태그와 자바스크립트를 이용하여 보낼 수 있다.
    // Form 태그는 Get, Post 요청만 가능하기 때문에 나머지 요청은 자바스크립트를 이용해야 하기 때문에 비효울적이다.
    // 따라서 효율적인 요청을 위해 자바스크립트로 ajax 요청 + 데이터는 json 으로 통일한다.
    // 스프링부트에서 form:from 태크로 get, post, put, delete 요청을 모두 method로 선언가능하다.

    //스프링 컨토롤러의 파싱전략 : 스프링 컨트롤러는 key=value 데이터를 자동으로 파싱하여 변수에 준다.
    // get 요청과 post 요청에서 x-www-form-urlencoded는 key=value 데이터이기 때문에 파라미터로 받을 수 있다.
    // 또한, key=value 데이터를 오브젝트로 파싱해서 받아주는 역할도 한다. (setter가 없으면 오브젝트 파싱이 불가능 하다.)
    // json 데이터나 일반 text데이터는 스프링 컨트롤러에서 받기 위해서는 @RequestBody 어노테이션이 필요하다.
    // 이런 데이터는 스프링이 파싱해서 오브젝트로 받지 못한다. 그래서 @RequestBody 어노테이션을 붙이면
    // MessageConverter 클래스를 구현한 Jackson 라이브러리가 발동하면서 json 데이터를 자바 오브젝트로 파싱하여 받아준다.

    // @Autowired : 현재 클래스가 메모리에 적재될 때 함께 적재되도록 설정
    @Autowired //의존성 주입 (DI)
    private UserRepository userRepository;


    //---------------------INSERT---------------------
    // DB에 INSERT 하기위해 repository 패키지안에 UserRepository 인터페이스를 생성
    @PostMapping("/dummy/join")
    public String join(User user){
        System.out.println("username : " + user.getUsername());
        System.out.println("password : " + user.getPassword());
        System.out.println("email : " + user.getEmail());

        user.setRole(RoleType.USER); //User 클래스에서 role에 어노테이션이 없을 때 사용하며 RoleType 생성하여 역할을 정의
        userRepository.save(user);
        return "회원가입 완료";
    }

    //---------------------SELECT(READ)---------------------
    // @PathVariable : {id} 주소로 파라미터를 전달 받을 수 있다.
    // 특정 데이터 가져오기
    @GetMapping("/dummy/user/{id}")
    public User detail(@PathVariable int id) {
        // id를 DB에서 찾지못한디면 user이 null이 되므로 null이 리턴되지 않도록 Optional로 User 객체를 감싸 가져와서 null인지 아닌지 판단해서 리턴
        // findById() : 기본키를 기준으로 해당 정보들을 출력
        // orElseThrow() : 값이 DB에 존재하는 값이라면 그대로 실행되고 아니라면 orElseThrow 메서드가 실행된다. (Optional 메서드)
        // IllegalArgumentException : findById를 'Ctrl + 클릭'하면 확인가능
        // 람다식
        User user = userRepository.findById(id).orElseThrow(()->{
            return new IllegalArgumentException(id+" 해당 사용자는 없습니다.");
        });
        // 웹 브라우저는 HTML, CSS, JavaScript만 이해하기 때문에 자바 오브젝트인 user은 이해하지 못한다.
        // 따라서, 스프링부트에서 MessageConvert가 Jackson 라이브러리를 호출해서 json으로 변환하여 브라우저에 리턴한다.
        return user;
    }
        // 람다식 안쓸 때
        // User user = userRepository.findById(id).orElseThrow(()->{
        // return new IllegalArgumentException(id+" 해당 사용자는 없습니다.");
        // });
        // return user;

    // 모든 데이터 가져오기 (List 사용)
    @GetMapping("/dummy/user")
    public List<User> list(){
        return userRepository.findAll();
    }

    // 모든 데이터 페이지로 나눠서 가져오기 (Page 사용)
    @GetMapping("/dummy/user/page")
    // 한 페이지에 데이터를 2건씩 가져오고 id를 기준으로 내림차순 정렬한다.
    // 다음 페이지 : /dummy/user/page?page=0 (0,1,2,3,....)
    public Page<User> pageList(@PageableDefault(size = 2, sort = "id", direction = Sort.Direction.DESC) Pageable pageable){
        Page<User> users = userRepository.findAll(pageable);
        // Page 정보와 데이터가 합쳐진것이 아닌 데이터만 받기 (리턴값이 List이므로 메서드를 List로 변경)
        // List<user> users = user.getContent();
        // return users;
        return users;
    }

    //---------------------UPDATE--------------------
    // 위에 @GetMapping과 주소가 같아도 요청에 따라 구분하기 때문에 충돌이 발생하지 않는다.
    @Transactional //함수 종료시에 자동 Commit
    @PutMapping("/dummy/user/{id}")
    // @RequestBody : 스프링부트에서 MessageConvert가 Jackson 라이브러리를 호출해서 Java Object로 변환하여 받는다.
    public String updateUser(@PathVariable int id, @RequestBody User requestUser){
        System.out.println("id : " + id);
        System.out.println("password : " + requestUser.getPassword());
        System.out.println("email : " + requestUser.getEmail() );

        // save함수는 id를 전달하지 않거나 전달 헀으나 없으면 inset, 해당 id가 있으면 update를 진행한다.
        // 만약 UPDATE에 save를 사용한다면 requestUser를 바로 대입하게 되면 나머지 값들은 null이 되기 떄문에
        // 아래와 같이 DB에서 데이터가 채워진 객체를 가져온다음에 필요한 부분만 수정해줘야 한다.
        User user = userRepository.findById(id).orElseThrow(()->{
            return new IllegalArgumentException("수정에 실패하였습니다.");
        });
        user.setPassword(requestUser.getPassword());
        user.setEmail(requestUser.getEmail());
        // @Transactional : save 함수를 사용하지 않고 INSERT를 할 수 있다.
        // JPA는 영속성 컨텍스트라는 거대한 공간이 있는데 save 함수를 사용한다면 1차 캐시안에 데이터가 쌓인다.
        // 1차 캐시에서 DB로 들어가게 되는데 1차 캐시에 해당 데이터가 생긴것을 영속화되었다고 한다.
        // 영속화된 데이터를 DB에 삽입하여 1차 캐시를 비우는 것을 Flush라고 한다. (Buffer를 비운다고도 표현)
        // JPA는 Flush 라고해도 1차 캐시르 비우지 않고 조회 요청이 들어온다면 DB에 접근하는 것이아닌 영속성 컨텍스트에 접근하여 부하를 줄여준다.
        // 또한, UPDATE 요청이 들어온다면 프로그램은 영속성 컨텍스트에 새로운 데이터를 만드는 것이 아닌 데이터를 수정한 다음에 Flush한다.
        // @Transactional을 사용 했을 때 save 함수를 사용하지 않을 수 있는 이유는 해당 어노테인션을 사용하면
        // 함수 종료시에 자동 Commit이 되는데 JPA는 트랜잭션이 끝나는 시점에 변화가 있는 모든 엔티티 객체를 DB에 자동 반영해주기 떄문에
        // save 함수 없이 변경상태가 DB에 저장되는 것이다. (더티 채킹은 상태 변경 검사한다는 뜻이다.)
        // userRepository.save(user);
        return "수정에 성공하였습니다.";
    }

    //---------------------DELETE--------------------
    @DeleteMapping("/dummy/user/{id}")
    public String delete(@PathVariable int id){
        try {
            userRepository.deleteById(id);
        }catch (EmptyResultDataAccessException e){
            return "삭제에 실패하였습니다. 해당 " + id + "는 DB에 없습니다";
        }

        return "삭제가 완료되었습니다.";
    }
}
