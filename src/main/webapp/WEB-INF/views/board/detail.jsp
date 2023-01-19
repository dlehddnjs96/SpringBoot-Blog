<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file ="../layout/securityHeader.jsp"%>

<h2 style="text-align:center;">Title</h2>
<div class="container">
  <div class="form-group">
  <button class="btn btn-secondary" onclick="history.back()" style="float: right;">돌아가기</button>
  <!--삭제, 수정 버튼은 사용자만 볼 수 있도록 설정 (Board - User - Principal을 통해 가져온다.-->
  <c:if test="${board.user.id == principal.user.id}">
   <button onclick="location.href='/board/${board.id}/updateForm'" class="btn btn-warning" style="float: right; margin-right:4px;">수정</button>
   <button id="btn-delete" class="btn btn-danger" style="float: right; margin-right:4px;">삭제</button>
  </c:if>
  <br/><br/>
  <!--BoardController 중 findById 에서 오는 데이터 사용-->
  <div>
   글 번호: <span id="id"><i>${board.id}</i></span>
   작성자 : <span><i>${board.user.username}</i></span>
  </div>
  <br/>
    <h3>${board.title}</h3>
  </div>
  <hr/>
  <div class="form-group">
  <h2 style="text-align:center;">Content</h2>
    <div> ${board.content} </div>
  </div>
  <hr/>

</div>


<script src="/js/board.js"></script>
<%@ include file ="../layout/footer.jsp"%>

