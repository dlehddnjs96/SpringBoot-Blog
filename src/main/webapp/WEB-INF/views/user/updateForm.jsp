<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file ="../layout/securityHeader.jsp"%>

<div class="container">
<!--과거에는 회원정보를 INSERT 하기위헤 POST 메서드 설정했지만 현재는 JSON 방식을 사용-->
<!--form action="/user/join" method="POST"-->
<form>
  <!--수정할 유저를 찾기위한 정보를 삽입-->
  <input type="hidden" id="id" value="${principal.user.id}"/>
  <div class="form-group">
    <label for="username">Username</label>
    <input type="text" value="${principal.user.username}" class="form-control" placeholder="Enter Username" id="username" readonly>
  </div>
  <!--if문-->
    <c:choose>
      <c:when test="${not empty principal.user.oauth}">
      <!--oauth 카카오 일때 (카카오로그인)-->
       <div class="form-group">
           <label for="email">Email</label>
           <input type="email" value="${principal.user.email}" class="form-control" placeholder="Enter Email" id="email" readonly>
       </div>
       <div class="form-group">
          <label for="password">Password</label>
          <input type="password" class="form-control" placeholder="Password" id="password" readonly>
       </div>
      </c:when>
      <c:otherwise>
      <!--oauth null 일때 (일반로그인)-->
       <div class="form-group">
          <label for="email">Email</label>
          <input type="email" value="${principal.user.email}" class="form-control" placeholder="Enter Email" id="email" >
       </div>
       <div class="form-group">
         <label for="password">Password</label>
         <input type="password" class="form-control" placeholder="비밀번호를 변경시 입력해주세요." id="password" >
       </div>
      </c:otherwise>
    </c:choose>
</form>
<!--user.js 사용하여 기능사용-->
<button id="btn-update" class="btn btn-primary">수정완료</button>
</div>

<!--자바스크립트를 body 밑에 두는 이유는 인터프리터언어여서 한줄씩 읽으면서 실행되기 때문에 html이 로드되지 않은 상태에서
자바스크립트로 html의 태그(dom) 을 찾게 되면 오류가 나기 때문이다. -->
<script src="/js/user.js"></script>
<%@ include file ="../layout/footer.jsp"%>

