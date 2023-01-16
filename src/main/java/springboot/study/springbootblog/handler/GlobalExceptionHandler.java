package springboot.study.springbootblog.handler;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;


// @ControllerAdvice : 예외가 발생하면 이 클래스에 들어온다.
@ControllerAdvice
@RestController
public class GlobalExceptionHandler {

    // 예외 발생시 복잡한 스택 트레이스가 포함된 에러 페이지를 보여주는 것 보다 간단한 메시지를 보여줄 때 사용

    @ExceptionHandler(value = IllegalArgumentException.class)
    public String handleArgumentException(IllegalArgumentException e){
        return "<h1>"+e.getMessage()+"</h1>";
    }
}
