<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file ="../layout/securityHeader.jsp"%>

<div class="container">
<!--
<form>
-->
<!--로그인은 시큐리티가 요청을 가로채서 처리한다.-->
<form action="/auth/loginProc" method="post">
  <div class="form-group">
    <!--security 사용 전에는 id만 사용, security 사용 후에는 name 추가사용-->
    <label for="username">Username</label>
    <input type="text" name="username" class="form-control" placeholder="Enter Username" id="username">
  </div>
  <div class="form-group">
    <label for="password">Password</label>
    <input type="password" name="password" class="form-control" placeholder="Enter Password" id="password">
  </div>
  <button id="btn-securityLogin" class="btn btn-primary">로그인</button>
  <!--카카오 개발 페이지에서 등록 후 내 앱에서 키와 문서 - REST API - 인가코드 받기에서 요청주소 받아오기-->
  <a href="https://kauth.kakao.com/oauth/authorize?client_id=8624aab618e29f5d6c8e225b3d09766e&redirect_uri=http://localhost:8000/auth/kakao/callback&response_type=code"><img height="38px" src="/image/kakao_login_medium.png"/></a>
</form>
</div>
<!--
<button id="btn-login" class="btn btn-primary">로그인</button>
</div>
-->

<script src="/js/user.js"></script>
<%@ include file ="../layout/footer.jsp"%>

