<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file ="../layout/securityHeader.jsp"%>

<div class="container">
<!--
<form>
-->
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
</form>
</div>
<!--
<button id="btn-login" class="btn btn-primary">로그인</button>
</div>
-->

<script src="/js/user.js"></script>
<%@ include file ="../layout/footer.jsp"%>

