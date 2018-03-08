<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="../include/header.jsp" %>
<script>
	$(function(){
		$("input[type='button']").click(function(){
			var action = $(this).val();
						
			if(action == "Go List"){
				action = "listPage";
			}else{
				action = action + "Page";
			}
			$("#f1").attr("action", action);
			$("#f1").submit();
		})
	})

</script>
<section class="content">
	<div class="row">
		<div class="col-md-12">
			<div class="box">
				<div class="box-header with-board">
					<h3 class="box-title">REGISTER</h3>
				</div>
				<div class="box-body">
					<form method="get" action="" id="f1">
						<input type="hidden" name="bno" value="${board.bno }">
						<input type="hidden" name="page" value="${cri.page }">
						<input type="hidden" name="perPageNum" value="${cri.perPageNum }">
					</form>
						<div class="form-group">
							<label>Title</label>
							<input type="text" name="title" readonly="readonly" class="form-control" value="${board.title }" placeholder="title">
						</div>
						<div class="form-group">
							<label>Content</label>
							<textarea rows="5" name="content" readonly="readonly" class="form-control" placeholder="content">${board.content }</textarea>
						</div>
						<div class="form-group">
							<label>Writer</label>
							<input type="text" name="writer" readonly="readonly" class="form-control" value="${board.writer }" placeholder="writer">
						</div>
						<div class="form-group">
							<input type="button" value="Modify" class="btn btn-warning">
							<input type="button" value="Remove" class="btn btn-danger">
							<input type="button" value="Go List" class="btn btn-primary">
						</div>
				</div>
			</div>
		</div>	
	</div>
</section>
<%@ include file="../include/footer.jsp" %>