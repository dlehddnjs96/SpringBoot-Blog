let index ={
    // init 이라는 함수 생성
    init:function(){
        // btn-save ID를 가진 HTML이 클릭되면 저장
        // function() {}가 아닌 ()=>{}를 사용하는 이유는 this를 바인당하기 위해서 사용한다.
        $("#btn-save").on("click", ()=>{
            this.save();
        });
        $("#btn-login").on("click", ()=>{
            this.login();
        });
    },

    save:function(){
        let data ={
            // HTML(joinForm.jsp) 해당 ID를 가진 태그에서 값을 가저와서 저장.
            username: $("#username").val(),
            password: $("#password").val(),
            email: $("#email").val()
        };
        // 확인
        // console.log(data)

        // 회원가입시 Ajax를 사용하는 이유 2가지
        // 1. 요청에 대한 응답을 HTML이 아닌 DATA(Json)로 하기 위해서이다. (웹, 앱 2개 모두 응답이 가능한 서버를 만들 수 있다.)
        // 클라이언트가 브라우저를 이용하여 서버에게 요청하면 서버는 HTML 파일로 응답을 보여주는 방식으로 소통한다.
        // 서버에서 요청에 대한 로직을 수행하고 응답을 어떠한 페이지나 변화를 보여줌으로써 클라이언트에게 로직을 완료했음을 알려준다.
        // 만약 클라이언트가 브라우저 환경이 아닌 다른 환경(앱)이라면 HTML 파일이 아닌 DATA를 응답해줘야 하기 되기때문에 비효율적이다.
        // 따라서, 브라우저와 앱 모두 DATA를 응답해주는 방식으로 가고 브라우저의 경우 로직이 정상으로 수행되었다는 DATA를 받으면
        // 결과에 따른 변화를 서버에 요청을 다시하여 HTML 파일을 응답받고 앱의 경우는 DATA만 받으면 된다. (앱은 DATA에 따른 반응을 앱 내부에서 처리)
        // 2. 비동기 통신을 하기 위해서이다.
        // 프로그램이 절차적으로 이뤄지는 동안 중간에 긴 시간이 걸리는 로직을 수행해야 한다면 클라이언트는 오랜 시간 기다려야 한다.
        // 하지만, 연산과 같은 작업(CPU)과 다운로드 같은 작업(기억장치+저장장치)을 분리하여 수행한다면 클라이언트는 다운로드가 이뤄지는 동안 연산이 가능하다.
        // 그러다 다운로드가 완료되면 연산하고 있는 CPU를 CallBack 하여 절차에 맞는 작업을 수행한다. (비동기적 실행)
        // 따라서, 회원가입과 같은 통신이 일어나는 순간에도 연산과 같은 작업이 가능하게 해준다.

        // ajax 통신을 이용해서 3개의 데이터를 Json으로 변경하여 insert 요청!!
        // ajax 통신을 성공하고 서버가 String을 리턴해도 자동으로 Json으로 변환해주고 자바스크립트 오브젝트로 변환해준다.
        // ajax 호출시 default 비동기 호출이다.
        $.ajax({
            // 회원가입 수행 요청
            type:"POST",
            url:"/auth/joinProc",
            // 자바스크립트 오브젝트를 자바가 이해할 수 있게 JSON으로 변환
            data:JSON.stringify(data),
            // Post는 http body 데이터이므로 타입을 알려줘야한다. (요청 데이터타입, MINE)
            contentType:"application/json; charset=utf-8",
            // 서버에서 응답으로 들어오는 데이터 타입은 모두 String이지만 형태가 Json이라면 자바스크립트 오브젝트로 변환해준다. (응답 데이터타입)
            dataType:"json"
        }).done(function(resp){
            // resp에 응답 데이터를 받는다.
            // true
            // 메시지를 보여준 후 인덱스 폼으로 이동
            alert("사용자의 회원가입이 완료되었습니다.");
            location.href="/";
        }).fail(function(error){
            // false
            alert(JSON.stringify(error));
        });

    },

    login:function(){
            let data ={
                // HTML(loginForm.jsp) 해당 ID를 가진 태그에서 값을 가저와서 저장.
                username: $("#username").val(),
                password: $("#password").val(),
            };

            $.ajax({
                // 로그인 수행 요청
                type:"POST",
                url:"/api/user/login",
                // 자바스크립트 오브젝트를 자바가 이해할 수 있게 JSON으로 변환
                data:JSON.stringify(data),
                // Post는 http body 데이터이므로 타입을 알려줘야한다. (요청 데이터타입, MINE)
                contentType:"application/json; charset=utf-8",
                // 서버에서 응답으로 들어오는 데이터 타입은 모두 String이지만 형태가 Json이라면 자바스크립트 오브젝트로 변환해준다. (응답 데이터타입)
                dataType:"json"
            }).done(function(resp){
                // resp에 응답 데이터를 받는다.
                // true
                // UserApiController에서 HttpStatus.OK.value() = 200
                if (resp.status == "200"){
                    alert("로그인이 완료되었습니다");
                    location.href="/";
                }else{
                    alert("계정 또는 비밀번호가 맞지 않습니다.");
                    location.href="/user/loginForm";
                }


            }).fail(function(error){
                // false
                alert(JSON.stringify(error));
            });

        }
}
// index 안에 init 함수 호출
index.init();