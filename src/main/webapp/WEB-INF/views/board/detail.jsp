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
   글 번호: <span id="id"><i>${board.id} &nbsp;</i></span>
   작성자 : <span><i>${board.user.username}</i></span>
  </div>
  <br/>
    <h3 class>${board.title}</h3>
  </div>
  <hr/>
  <div class="form-group">
  <h2 style="text-align:center;">Content</h2>
    <div> ${board.content} </div>
  </div>
  <hr/>
  <!-- 댓글 -->
  <div class="card">
    <form>
     <input type="hidden" id="userId" value="${principal.user.id}"/>
     <input type="hidden" id="boardId" value="${board.id}"/>
     <div class="card-body"><textarea id="reply-content" class="form-control" rows="1"></textarea></div>
     <div class="card-footer"><button type="button" id="btn-reply-save" class="btn btn-primary">등록</button></div>
    </form>
  </div>
  <br/>
  <div class="card">
   <div class="card-header">댓글</div>
   <ul id="reply-box" class="list-group">
     <c:forEach var="reply" items="${board.replys}">
      <li id="reply-${reply.id}" class="list-group-item d-flex justify-content-between">
         <div>${reply.content}</div>
         <div class="d-flex">
           <div class="font-italic">작성자 : ${reply.user.username} &nbsp;</div>
           <button onClick="index.replyDelete(${board.id},${reply.id})" class="badge">삭제</button>
         </div>
      </li>
     </c:forEach>
   </ul>
  </div>


</div>


<script src="/js/board.js"></script>
<%@ include file ="../layout/footer.jsp"%>

