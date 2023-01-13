package springboot.study.springbootblog.blogTest;

import org.springframework.web.bind.annotation.*;

// 클라이언트가 요청 -> 응답 (DATA)
// 클라이언트의 요청에 HTML파일로 응답 : @Controller
@RestController
public class HttpControllerTest {
    // 모든 요청에 대한 객체사용은 스프링부트의 MessageConverter이 해준다.

    // 인터넷 브라우저 요청은 무조건 get요청만 가능하다.
    // Query String에 대한 응답 : @RequestParam, 객체 모두 가능
    // 클라이언트 요청을 http::/~~?id=1&username=lee 형식(Query String)으로 받는다
    @GetMapping("/http/get")
    // @RequestParam : 해당 파라미터에 정확한 변수명과 값이 모두 포함되어 있으면 나머지는 잘못적어도 오류가 발생하지 않지만 1개라도 없다면 오류가 발생한다.
    public String getTest(@RequestParam int id, @RequestParam String username) {
        return "GET 응답(SELECT) : " + id + "," + username;
    }
    // Member 객체에 있는 모든 변수받기
    @GetMapping("/http/get2")
    // 객체 : 해당 파라미터가 기본형으로 선언된 변수만 정확한 변수명과 값이 포함되어 있다면 나미저는 잘못 적어도 오류가 발생하지 않는다. (변수는 기본형 int.., 참조형 String...으로 나뉜다.)
    public String getTest2(HttpMemberTest httpMemberTest){
        return "GET 응답2(SELECT) : " + httpMemberTest.getId() + "," + httpMemberTest.getUsername();
    }
    //----------------------------------------------------------------------------------------------------------------

    // POST 요청은 Query String이 아닌 HTTP BODY에 데이터를 보낸다.
    // 클라이언트가 HTTP BODY로 데이터 보내는 법 : x-www-form-urlencoded (HTML 형태로 요청), raw (Text, JSON 등 다양한 형태로 요청)
    // HTTP BODY에 대한 응답 : @RequestParam, @RequestBody ,객체 모두 가능
    @PostMapping("/http/post")
    // @RequestParam : x-www-form-urlencoded 형식은 받을 수 있으나 raw 형식은 받지 못하고 오류가 발생한다.
    public String postTest(@RequestParam String text) {
        return "POST 응답(INSERT) : " + text;
    }
    // @RequestBody : x-www-form-urlencoded, raw 형식 모두 받을 수 있다.
    @PostMapping("/http/post2")
    public String postTest2(@RequestBody String text) {
        return "POST 응답2(INSERT) : " + text;
    }
    // 객체 : x-www-form-urlencoded 형식은 받을 수 있으나 raw 형식은 받지 못하고 오류가 발생한다.
    @PostMapping("/http/post3")
    public String postTest3(HttpMemberTest httpMemberTest) {
        return "POST 응답3(INSERT) : " + httpMemberTest.getId() + "," + httpMemberTest.getUsername();
    }
    // JSON 응답 (@RequestBody + 객체로 받아야 정확한 변수명에 대입되는 값을 받을 수 있다.)
    @PostMapping("/http/post4")
    public String postTest4(@RequestBody HttpMemberTest httpMemberTest) {
        return "POST 응답3(INSERT) : " + httpMemberTest.getId() + "," + httpMemberTest.getUsername();
    }
    //----------------------------------------------------------------------------------------------------------------

    // Put 요청은 Query String이 아닌 HTTP BODY에 데이터를 보낸다.
    // 클라이언트가 HTTP BODY로 데이터 보내는 법 : x-www-form-urlencoded (HTML 형태로 요청), raw (Text, JSON 등 다양한 형태로 요청)
    // HTTP BODY에 대한 응답 : @RequestParam, @RequestBody ,객체 모두 가능
    // Post 요청과 동일한 과정을 가지고 있다.
    @PutMapping("/http/put")
    public String putTest() {
        return "PUT 응답(UPDATE)";
    }
    @PutMapping("/http/put2")
    public String putTest2(@RequestBody HttpMemberTest httpMemberTest) {
        return "PUT 응답2(UPDATE) " + httpMemberTest.getId() + "," + httpMemberTest.getUsername();
    }
    //----------------------------------------------------------------------------------------------------------------

    // Query String에 대한 응답 : @RequestParam, 객체 모두 가능
    // 클라이언트 요청을 http::/~~?id=1&username=lee 형식(Query String)으로 받는다
    // Get 요청과 동잏한 과정을 가지고 있다.
    @DeleteMapping("/http/delete")
    public String deleteTest() {
        return "DELETE 응답(DELETE)";
    }


}
