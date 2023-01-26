let index ={
    // init 이라는 함수 생성
    init:function(){
        // btn-board-save ID를 가진 HTML이 클릭되면 저장
        // function() {}가 아닌 ()=>{}를 사용하는 이유는 this를 바인당하기 위해서 사용한다.
        $("#btn-board-save").on("click", ()=>{
            this.save();
        });
        $("#btn-delete").on("click", ()=>{
            this.deleteById();
        });
        $("#btn-update").on("click", ()=>{
            this.update();
        });
        $("#btn-reply-save").on("click", ()=>{
            this.replySave();
        });
    },


    save:function(){
        let data ={
            // HTML(saveForm.jsp) 해당 ID를 가진 태그에서 값을 가저와서 저장.
            title: $("#title").val(),
            content: $("#content").val()
        };

        $.ajax({
            // 글쓰기 내용 저장
            type:"POST",
            url:"/api/board",
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
            alert("글쓰기가 완료되었습니다.");
            location.href="/";
        }).fail(function(error){
            // false
            alert(JSON.stringify(error));
        });

    },


    deleteById:function(){
            let id = $("#id").text();

            $.ajax({
                // 글쓰기 내용 삭제
                type:"DELETE",
                url:"/api/board/"+id,
                dataType:"json"
            }).done(function(resp){
                // resp에 응답 데이터를 받는다.
                // true
                // 메시지를 보여준 후 인덱스 폼으로 이동
                alert("삭제가 완료되었습니다.");
                location.href="/";
            }).fail(function(error){
                // false
                alert(JSON.stringify(error));
            });

        },


    update:function(){
           let id = $("#id").val();
           let data ={
               // HTML(updateDorm.jsp) 해당 ID를 가진 태그에서 값을 가저와서 저장.
               title: $("#title").val(),
               content: $("#content").val()
           };

           $.ajax({
               // 글쓰기 내용 수정
               type:"PUT",
               url:"/api/board/"+id,
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
               alert("수정이 완료되었습니다.");
               location.href="/";
           }).fail(function(error){
               // false
               alert(JSON.stringify(error));
           });
    },


    replySave:function(){
            let data ={
                // HTML(saveForm.jsp) 해당 ID를 가진 태그에서 값을 가저와서 저장.
                userId: $("#userId").val(),
                boardId: $("#boardId").val(),
                content: $("#reply-content").val()
            };

            $.ajax({
                // 글쓰기 내용 저장
                type:"POST",
                url:`/api/board/${data.boardId}/reply`,
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
                alert("댓글작성이 완료되었습니다.");
                location.href= `/board/${data.boardId}`;
            }).fail(function(error){
                // false
                alert(JSON.stringify(error));
            });

        },


   replyDelete : function(boardId, replyId){
   			$.ajax({
   				type: "DELETE",
   				url: `/api/board/${boardId}/reply/${replyId}`,
   				dataType: "json"
   			}).done(function(resp){
   				alert("댓글삭제가 완료되었습니다.");
   				location.href = `/board/${boardId}`;
   			}).fail(function(error){
   				alert(JSON.stringify(error));
   			});
   		}

}

// index 안에 init 함수 호출
index.init();