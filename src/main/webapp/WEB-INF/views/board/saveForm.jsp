<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file ="../layout/securityHeader.jsp"%>

<div class="container">
<form>
  <div class="form-group">
    <label for="title">Title</label>
    <input type="text" class="form-control" placeholder="Enter Title" id="title">
  </div>
  <div class="form-group">
    <label for="content">Content:</label>
    <textarea class="form-control summernote" rows="15" id="content"></textarea>
  </div>
</form>
<button id="btn-board-save" class="btn btn-primary">저장</button>
</div>

<!--"#"은 id, "."은 class로 해당부분을 찾는다.-->
<script>
      $('.summernote').summernote({
        placeholder: 'Enter Content',
        tabsize: 2,
        height: 300
      });
</script>
<script src="/js/board.js"></script>
<%@ include file ="../layout/footer.jsp"%>

