<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file ="../layout/securityHeader.jsp"%>

<div class="container">
<!--과거에는 회원정보를 INSERT 하기위헤 POST 메서드 설정했지만 현재는 JSON 방식을 사용-->
<!--form action="/user/join" method="POST"-->
<form>
  <div class="form-group">
    <label for="username">Username</label>
    <input type="text" class="form-control" placeholder="Enter username" id="username">
  </div>
  <div class="form-group">
     <label for="email">Email</label>
     <input type="email" class="form-control" placeholder="Enter email" id="email">
   </div>
  <div class="form-group">
    <label for="password">Password</label>
    <input type="password" class="form-control" placeholder="Enter password" id="password">
  </div>
</form>
<!--user.js 사용하여 기능사용-->
<button id="btn-save" class="btn btn-primary">회원가입</button>
</div>

<!--자바스크립트를 body 밑에 두는 이유는 인터프리터언어여서 한줄씩 읽으면서 실행되기 때문에 html이 로드되지 않은 상태에서
자바스크립트로 html의 태그(dom) 을 찾게 되면 오류가 나기 때문이다. -->
<script src="/js/user.js"></script>
<%@ include file ="../layout/footer.jsp"%>

