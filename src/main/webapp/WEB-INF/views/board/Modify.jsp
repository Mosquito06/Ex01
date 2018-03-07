<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="../include/header.jsp" %>
<section class="content">
	<div class="row">
		<div class="col-md-12">
			<div class="box">
				<div class="box-header with-board">
					<h3 class="box-title">REGISTER</h3>
				</div>
				<div class="box-body">
					<form method="post" action="Modify">
						<div class="form-group">
							<label>Title</label>
							<input type="hidden" name="bno" value="${board.bno }">
							<input type="text" name="title" class="form-control" value="${board.title }" placeholder="title">
						</div>
						<div class="form-group">
							<label>Content</label>
							<textarea rows="5" name="content" class="form-control" placeholder="content">${board.content }</textarea>
						</div>
						<div class="form-group">
							<label>Writer</label>
							<input type="text" name="writer" class="form-control" value="${board.writer }" placeholder="writer">
						</div>
						<div class="form-group">
							<input type="submit" value="Modify" class="btn btn-primary">
						</div>
					</form>
				</div>
			</div>
		</div>	
	</div>
</section>
<%@ include file="../include/footer.jsp" %>