package springboot.study.springbootblog.blogTest;

// lombok를 통해 Getter는 @Getter, Setter는 Setter, 둘 다는 @Data를 통해 생성할 수 있다.
// lombok를 통해 매개변수 생성자는 @AllArgsConstructor를 통해 생성 할 수 있다. (final 붙은 생성자 : @RequiredArgsConstructor)
// lombok를 통해 기본 생성자는 @NoArgsConstructor를 통해 생성 할 수 있다.
public class HttpMemberTest {
    // private로 선언하는 이유 : 변수에 직접적인 접근을 막아 값의 변경과 같은 권한을 제한하고 메서드를 이용하여 접근하도록 하기 위해서 이다.
    private int id;
    private String username;
    private String password;
    private String email;

    // 매개변수 생성자
    // 생성자를 생성하지 않으면 자바에서 자동으로 매개변수가 없는 기본생성자를 생성한다. (매개변수 생성자가 있다면 생성X)
    // @Builder : 오버로딩과 같이 매개변수를 각각 다르게 사용하고 싶을 때 사용
    // Member m = Member.builder().username("lee").build();와 같이 사용가능
    public HttpMemberTest(int id, String username, String password, String email) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.email = email;
    }
    
    
    // getter, setter를 통해 접근
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }
    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }


}
